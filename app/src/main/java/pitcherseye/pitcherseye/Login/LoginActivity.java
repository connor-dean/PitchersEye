/*
 This Activity manages the process of logging in for the user and is the first thing the user
 will see when opening the application unless they have an authentication token. The user has the option to enter
 their credentials, if it's unsuccessful than we'll display a message stating so.

 The user also has an option to register for an account by selecting the link below the login button.
 */

package pitcherseye.pitcherseye.Login;

import android.app.Activity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import pitcherseye.pitcherseye.Home.HomeActivity;
import pitcherseye.pitcherseye.R;

public class LoginActivity extends AppCompatActivity {
    static Activity loginActivity;
    private FirebaseAuth mAuth;

    @BindView(R.id.button_login) Button mLoginButton;
    @BindView(R.id.edt_email) EditText mLoginEmail;
    @BindView(R.id.edt_password) EditText mLoginPassword;
    @BindView(R.id.progress_login) ProgressBar mLogInProgress;
    @BindView(R.id.txt_new_user) TextView mSignUp;

    private View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkCredentials();
        }
    };

    private View.OnClickListener signUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intentSignUp = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intentSignUp);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // For backstack management
        loginActivity = this;

        mAuth = FirebaseAuth.getInstance();

        mLogInProgress.setVisibility(View.GONE);

        mLoginButton.setOnClickListener(loginClickListener);
        mSignUp.setOnClickListener(signUpClickListener);

        loginPersistance();
    }

    public void checkCredentials() {
        final String email = mLoginEmail.getText().toString().trim();
        final String password = mLoginPassword.getText().toString().trim();

        if (email.isEmpty() || email == null) {
            mLoginEmail.setError("Email is required.");
            mLoginEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mLoginEmail.setError("You've entered an invalid email.");
            mLoginEmail.requestFocus();
            return;
        }

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

        mLogInProgress.setVisibility(View.VISIBLE);

        signIn(email, password);
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mLogInProgress.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Intent intentLogin = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intentLogin);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginPersistance() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}
