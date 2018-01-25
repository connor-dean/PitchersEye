package pitcherseye.pitcherseye;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    // Buttons
    Button mLoginButton;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogIn();
    }

    public void LogIn() {
        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = MainActivity.newIntent(LoginActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });
    }
}
