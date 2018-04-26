/*
 This Activity acts as our "home page". It hosts several buttons that redirect you to the LoginActivity,
 TaggingActivity or the StatisticsActivity.

 If the user has an authentication token assigned to them then this is the first Activity they'll see
 when opening the application.

 When selecting the "Logout" option, we also clear the user's authentication token.
 */

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

    // UI Components
    Button mLogoutButton;
    Button mNewGameButton;
    Button mStatsButton;
    ImageButton mStatsImageButton;
    ImageButton mNewGameImageButton;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // We'll call this in other Activities to access this Activity
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MainActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Redirect to the StatisticsActivity
        mStatsImageButton = (ImageButton) findViewById(R.id.img_button_statistics);
        mStatsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = StatisticsActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });

        // Redirect to the StatisticsActivity
        mStatsButton = (Button) findViewById(R.id.button_statistics);
        mStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = StatisticsActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });

        // Redirect to the TaggingActivity
        mNewGameImageButton = (ImageButton) findViewById(R.id.img_button_new_game);
        mNewGameImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = TaggingActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });

        // Redirect to the TaggingActivity
        mNewGameButton = (Button) findViewById(R.id.button_new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = TaggingActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });

        // Redirect to the LoginActivity
        mLogoutButton = (Button) findViewById(R.id.button_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut(); // Clear authentication token
                Intent i = LoginActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish(); // Don't add to the backstack
            }
        });
    }
}
