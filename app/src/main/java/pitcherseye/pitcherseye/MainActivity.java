package pitcherseye.pitcherseye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button mScheduleButton;
    Button mStatsButton;
    Button mNewGameButton;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScheduleButton = (Button) findViewById(R.id.button_schedule);
        mScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = ScheduleActivity.newIntent(MainActivity.this);
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


        mNewGameButton = (Button) findViewById(R.id.button_new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = TaggingActivity.newIntent(MainActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
            }
        });
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, MainActivity.class);
        return i;
    }
}
