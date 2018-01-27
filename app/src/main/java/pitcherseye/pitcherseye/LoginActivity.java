package pitcherseye.pitcherseye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    // Buttons
    Button mLoginButton;
    TextView mSignUp;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Log in event
        logIn();

        // Sign up event
        signUp();
    }

    public void logIn() {
        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = MainActivity.newIntent(LoginActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });
    }

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
