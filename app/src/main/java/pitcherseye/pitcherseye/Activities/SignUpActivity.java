package pitcherseye.pitcherseye.Activities;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import pitcherseye.pitcherseye.Objects.User;
import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Utilities;

public class SignUpActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.button_signup_register) Button mRegisterButton;
    @BindView(R.id.edt_confirm_signup_password) EditText mSignUpConfirmPassword;
    @BindView(R.id.edt_signup_email) EditText mSignUpEmail;
    @BindView(R.id.edt_first_name) EditText mSignUpFirstName;
    @BindView(R.id.edt_last_name) EditText mSignUpLastName;
    @BindView(R.id.edt_signup_password) EditText mSignUpPassword;
    @BindView(R.id.progress_signup) ProgressBar mSignUpProgress;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //EditText mSignUpRegistrationID; // Use this field later once we can scale
    //EditText mSignUpTeamID; // Use this field later once we can scale

    String fname;
    String lname;
    String email;
    String password;
    String confirmPassword;

    // String teamID; // Use this field later once we can scale
    // String registrationID; // Use this field later once we can scale

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        // Instantiate Firebase objects
        // We'll have one for our Authentication database and one for our Realtime Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Instantiate and hide progress bar
        mSignUpProgress.setVisibility(View.GONE);

        // Register event
        registerUserHelper();
    }

    // Used as a helper method to wrap the loadRegistrationValues and registerUser methods
    private void registerUserHelper() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRegistrationValues();
                registerUser();
            }
        });
    }

    // Receives input from text fields and verifies credentials
    // TODO see if we can refactor this at all to have a better pattern
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

                        // If the registration was successful, direct user to the MainActivity
                        if (task.isSuccessful()) {
                            Intent intentTagging = new Intent(SignUpActivity. this, MainActivity.class);

                            Log.i("SignUpActivity", "Opening MainActivity");
                            Log.i("SignUpActivity", "Adding user");
                            startActivity(intentTagging);

                            // Assign the user a randomly generated userID, this is up for change
                            // and we'll see if we can make an incrementign value like to do for
                            // PitcherStats and EventStats.
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
