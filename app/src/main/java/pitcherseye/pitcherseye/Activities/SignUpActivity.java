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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pitcherseye.pitcherseye.Objects.User;
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
    DatabaseReference mDatabase;
    ProgressBar mSignUpProgress;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // EditText strings to be used later
    String fname;
    String lname;
    String email;
    String password;
    String confirmPassword;
    String teamID;
    String registrationID;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, SignUpActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Instantiate Firebase object
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                loadRegistrationValues();
                registerUser();
            }
        });
    }

    // Receives input from text fields and verifies credentials
    private void registerUser() {
        // Iterate through inputs and validate
        if (validateInput(fname, mSignUpFirstName, "First name")) return;
        if (validateInput(lname, mSignUpLastName, "Last name")) return;
        if (validateInput(email, mSignUpEmail, "Email")) return;
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mSignUpEmail.setError("You've entered an invalid email.");
            return;
        }
        if (validateInput(password, mSignUpPassword, "Password")) return;
        if (password.length() < 6) {
            mSignUpPassword.setError("Password requires at least 6 characters.");
            return;
        }
        if (validateInput(confirmPassword, mSignUpConfirmPassword, "Password")) return;
        if (confirmPassword.compareTo(password) != 0) {
            mSignUpConfirmPassword.setError("Passwords do no match.");
            return;
        }

        // Keep this commented out until we can scale
        /*if (validateInput(teamID, mSignUpTeamID, "Team ID")) return;
        if (validateInput(registrationID, mSignUpRegistrationID, "Registration ID")) return;*/

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

                            String userID = Utilities.createRandomHex(6);

                            // TeamID will default to 0 until we can scale
                            sendUserInfo(userID, fname, lname, email, password, Integer.toString(0));
                            LoginActivity.loginActivity.finish(); // Kill LoginActivity from the backstack
                            finish(); // Don't add to the backstack
                        } else {
                            // Check if the user already exists
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    // Initialize/instantiate inputs from EditText fields
    private void loadRegistrationValues() {
        mSignUpFirstName = (EditText) findViewById(R.id.edt_first_name);
        mSignUpLastName = (EditText) findViewById(R.id.edt_last_name);
        mSignUpEmail = (EditText) findViewById(R.id.edt_signup_email);
        mSignUpPassword = (EditText) findViewById(R.id.edt_signup_password);
        mSignUpConfirmPassword = (EditText) findViewById(R.id.edt_confirm_signup_password);
        // Keep this commented out until we can scale
        /*mSignUpTeamID = (EditText) findViewById(R.id.edt_team_id);
        mSignUpRegistrationID = (EditText) findViewById(R.id.edt_registration_id);*/

        fname = mSignUpFirstName.getText().toString().trim();
        lname = mSignUpLastName.getText().toString().trim();
        email = mSignUpEmail.getText().toString().trim();
        password = mSignUpPassword.getText().toString().trim();
        confirmPassword = mSignUpConfirmPassword.getText().toString().trim();

        // Keep this commented out until we can scale
        /*teamID = mSignUpTeamID.getText().toString().trim();
        registrationID = mSignUpRegistrationID.getText().toString().trim();*/
    }

    // Made sure that fields aren't empty
    private boolean validateInput(String checkedString, EditText editText, String errorMessage) {
        if (checkedString.isEmpty() || checkedString == null) {
            editText.setError(errorMessage + " is required.");
            editText.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    private void sendUserInfo(String userID, String userFName, String userLName, String userEmail, String userPassword, String userTeamID) {
        // Instantiate User object
        User user = new User(userFName, userLName, userEmail, userPassword, userTeamID);

        // Send user information to Firebase
        mDatabase.child("users").child(userID).setValue(user);
    }
}
