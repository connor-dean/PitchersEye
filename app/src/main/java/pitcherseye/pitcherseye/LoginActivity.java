package pitcherseye.pitcherseye;

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

public class LoginActivity extends AppCompatActivity {

    // UI Components
    Button mLoginButton;
    EditText mLoginEmail;
    EditText mLoginPassword;
    FirebaseAuth mAuth;
    ProgressBar mLogInProgress;
    TextView mSignUp;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    public void loginPersistance() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            //Log.d(TAG, "onAuthStateChanged:signed_out");
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
        if (password.isEmpty()  || password == null) {
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

                if(task.isSuccessful()) {
                    // If the login was successful, direct user to the MainActivity
                    Intent i = MainActivity.newIntent(LoginActivity.this);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the backstack
                    startActivityForResult(i, REQUEST_CODE_CALCULATE);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, LoginActivity.class);
        return i;
    }

}
