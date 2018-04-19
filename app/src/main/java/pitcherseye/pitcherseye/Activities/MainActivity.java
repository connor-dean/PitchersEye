package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import pitcherseye.pitcherseye.R;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button mLogoutButton;
    Button mNewGameButton;
    Button mStatsButton;
    ImageButton mStatsImageButton;
    ImageButton mNewGameImageButton;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MainActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatsImageButton = (ImageButton) findViewById(R.id.img_button_statistics);
        mStatsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = StatisticsActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });

        mStatsButton = (Button) findViewById(R.id.button_statistics);
        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = StatisticsActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });

        mNewGameImageButton = (ImageButton) findViewById(R.id.img_button_new_game);
        mNewGameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = TaggingActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });

        mNewGameButton = (Button) findViewById(R.id.button_new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = TaggingActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });

        mLogoutButton = (Button) findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = LoginActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });
    }
}
