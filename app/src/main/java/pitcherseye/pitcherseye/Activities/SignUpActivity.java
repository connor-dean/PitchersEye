package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Utilities;

public class SignUpActivity extends AppCompatActivity {

    // UI Components
    Button mRegisterButton;
    EditText mSignUpConfirmPassword;
    EditText mSignUpEmail;
    EditText mSignUpFirstName;
    EditText mSignUpLastName;
    EditText mSignUpPassword;
    EditText mSignUpRegistrationID;
    EditText mSignUpTeamID;
    FirebaseAuth mAuth;
    ProgressBar mSignUpProgress;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Instantiate Firebase object
        mAuth = FirebaseAuth.getInstance();

        // Instantiate and hide progress bar
        mSignUpProgress = (ProgressBar) findViewById(R.id.progress_signup);
        mSignUpProgress.setVisibility(View.GONE);

        // Register event
        registerEvent();
    }

    private void registerEvent() {
        mRegisterButton = (Button) findViewById(R.id.button_signup_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide keyboard
                Utilities.hideSoftKeyboard(SignUpActivity.this);
                registerUser();
            }
        });
    }

    // Receives input from text fields and verifies credentials
    private void registerUser() {
        mSignUpFirstName = (EditText) findViewById(R.id.edt_first_name);
        mSignUpLastName = (EditText) findViewById(R.id.edt_last_name);
        mSignUpEmail = (EditText) findViewById(R.id.edt_signup_email);
        mSignUpPassword = (EditText) findViewById(R.id.edt_signup_password);
        mSignUpConfirmPassword = (EditText) findViewById(R.id.edt_confirm_signup_password);
        mSignUpTeamID = (EditText) findViewById(R.id.edt_team_id);
        mSignUpRegistrationID = (EditText) findViewById(R.id.edt_registration_id);

        final String fname = mSignUpFirstName.getText().toString().trim();
        final String lname = mSignUpLastName.getText().toString().trim();
        final String email = mSignUpEmail.getText().toString().trim();
        final String password = mSignUpPassword.getText().toString().trim();
        final String confirmPassword = mSignUpConfirmPassword.getText().toString().trim();
        final String teamID = mSignUpTeamID.getText().toString().trim();
        final String registrationID = mSignUpRegistrationID.getText().toString().trim();

        // Wow you sure are repeating yourself...
        stopRepeatingYourself(registrationID, mSignUpRegistrationID, "Registration ID");
        stopRepeatingYourself(teamID, mSignUpTeamID, "Team ID");
        stopRepeatingYourself(confirmPassword, mSignUpConfirmPassword, "Password");
        stopRepeatingYourself(password, mSignUpPassword, "Password");
        stopRepeatingYourself(email, mSignUpEmail, "Email");
        stopRepeatingYourself(lname, mSignUpLastName, "Last name");
        stopRepeatingYourself(fname, mSignUpFirstName, "First name");

        if (password.length() < 6) {
            mSignUpPassword.setError("Password requires at least 6 characters");
            mSignUpPassword.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mSignUpEmail.setError("You've entered an invalid email.");
            mSignUpEmail.requestFocus();
            return;
        }

        // Display the progress bar while loading
        mSignUpProgress.setVisibility(View.VISIBLE);

        // Return if the login was successful or invalid
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // Hide the progress bar
                        mSignUpProgress.setVisibility(View.GONE);

                        if (task.isSuccessful()) {
                            // If the registration was successful, direct user to the MainActivity
                            Intent i = MainActivity.newIntent(SignUpActivity.this);
                            startActivityForResult(i, REQUEST_CODE_CALCULATE);
                            LoginActivity.loginActivity.finish(); // Kill LoginActivity from the backstack
                            finish(); // Don't add to the backstack
                        } else {
                            // Check if the user already exists
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User already regisered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    public void stopRepeatingYourself(String checkedString, EditText editText, String errorMessage) {
        if(checkedString.isEmpty() || checkedString == null) {
            editText.setError(errorMessage + " is required.");
            editText.requestFocus();
        }
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, SignUpActivity.class);
        return i;
    }
}
