/*
 This Activity manages the process of logging in for the user and is the first thing the user
 will see when opening the application unless they have an authentication token. The user has the option to enter
 their credentials, if it's unsuccessful than we'll display a message stating so.

 The user also has an option to register for an account by selecting the link below the login button.
 */

package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pitcherseye.pitcherseye.R;

public class LoginActivity extends AppCompatActivity {

    // Instantiate LoginActivity object to manage backstack
    public static Activity loginActivity;

    // UI Components
    Button mLoginButton;
    EditText mLoginEmail;
    EditText mLoginPassword;
    FirebaseAuth mAuth;
    ProgressBar mLogInProgress;
    TextView mSignUp;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // We'll call this in other Activities to access this Activity
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, LoginActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // For backstack management
        loginActivity = this;

        // Instantiate Firebase instance
        mAuth = FirebaseAuth.getInstance();

        // Instantiate and hide progress bar
        mLogInProgress = (ProgressBar) findViewById(R.id.progress_login);
        mLogInProgress.setVisibility(View.GONE);

        // Check if a user is already logged in
        loginPersistance();

        // Log in event
        logInEvent();

        // Sign up event
        signUp();
    }

    // This is the magic behind our token persistence. When we open the app, we'll check to see
    // if the user still has an authentication token. If they do, just direct them to the MainActivity.
    public void loginPersistance() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // User is signed in successfully
        if (user != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    // Helper method for logInUser()
    public void logInEvent() {
        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInUser();
            }
        });
    }

    // Receives input from text fields and verifies credentials
    public void logInUser() {
        mLoginEmail = (EditText) findViewById(R.id.edt_email);
        mLoginPassword = (EditText) findViewById(R.id.edt_password);

        final String email = mLoginEmail.getText().toString().trim();
        final String password = mLoginPassword.getText().toString().trim();

        // Validate email, doesn't check valid emails, just the form for now
        if (email.isEmpty() || email == null) {
            mLoginEmail.setError("Email is required.");
            mLoginEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mLoginEmail.setError("You've entered an invalid email.");
            mLoginEmail.requestFocus();
            return;
        }

        // Validate password, we'll probably want to require special characters/casing in the future
        if (password.isEmpty() || password == null) {
            mLoginPassword.setError("Password is required.");
            mLoginPassword.requestFocus();
            Log.d("Password", "Password failed" + password);
            return;
        } else if (password.length() < 6) {
            mLoginPassword.setError("Password requires at least 6 characters");
            mLoginPassword.requestFocus();
            Log.d("Password", "Password succeeded" + password);
            return;
        }

        // Display the progress bar while loading
        mLogInProgress.setVisibility(View.VISIBLE);

        // Return if the login was successful or invalid
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // Hide the progress bar
                mLogInProgress.setVisibility(View.GONE);

                // If the login was successful, direct user to the MainActivity
                if (task.isSuccessful()) {
                    Intent i = MainActivity.newIntent(LoginActivity.this);
                    startActivityForResult(i, REQUEST_CODE_CALCULATE);
                    finish(); // Don't add to the backstack
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show(); // Display error message
                }
            }
        });
    }

    // Redirect to the SignUpActivity
    public void signUp() {
        mSignUp = (TextView) findViewById(R.id.txt_new_user);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = SignUpActivity.newIntent(LoginActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });
    }

}
