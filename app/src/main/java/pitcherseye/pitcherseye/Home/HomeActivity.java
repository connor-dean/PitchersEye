/*
 This Activity acts as our "home page". It hosts several buttons that redirect you to the LoginActivity,
 TaggingActivity or the StatisticsActivity.

 If the user has an authentication token assigned to them then this is the first Activity they'll see
 when opening the application.

 When selecting the "Logout" option, we also clear the user's authentication token.
 */

package pitcherseye.pitcherseye.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pitcherseye.pitcherseye.Login.LoginActivity;
import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Reports.StatisticsActivity;
import pitcherseye.pitcherseye.Tagging.TaggingActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.img_button_statistics, R.id.button_statistics })
    void openStatisticsActivity() {
        Intent intentStatistics = new Intent(this, StatisticsActivity.class);

        Log.i("HomeActivity", "Opening StatisticsActivity");
        startActivity(intentStatistics);
    }

    // Redirect to the TaggingActivity
    @OnClick({ R.id.img_button_new_game, R.id.button_new_game })
    void openTaggingActivity() {
        Intent intentTagging = new Intent(this, TaggingActivity.class);

        Log.i("HomeActivity", "Opening TaggingActivity");
        startActivity(intentTagging);
    }

    // Log the user out and navigate to LoginActivity
    @OnClick(R.id.button_logout)
    void logout() {
        FirebaseAuth.getInstance().signOut(); // Clear authentication token

        Intent intentLogout = new Intent(this, LoginActivity.class);

        Log.i("HomeActivity", "Logging out");
        Log.i("HomeActivity", "Opening LoginActivity");
        startActivity(intentLogout);
        finish(); // Don't add to the backstack
    }
}
