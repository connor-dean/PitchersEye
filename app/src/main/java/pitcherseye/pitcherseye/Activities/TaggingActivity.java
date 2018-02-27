package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pitcherseye.pitcherseye.Objects.PitchingStats;
import pitcherseye.pitcherseye.R;

public class TaggingActivity extends Activity {

    // Buttons
    Button mR1C1;
    Button mR1C2;
    Button mR1C3;
    Button mR2C1;
    Button mR2C2;
    Button mR2C3;
    Button mR3C1;
    Button mR3C2;
    Button mR3C3;
    Button mFinishGame;
    DatabaseReference mDatabase;
    TextView mPitchCount;
    TextView mTextStrikes;
    TextView mTextBalls;
    TextView mTextR1C3;
    TextView mTextR2C1;
    TextView mTextR2C2;
    TextView mTextR2C3;
    TextView mTextR3C1;
    TextView mTextR3C2;
    TextView mTextR3C3;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    int pitchCounter = 0;
    int[] totalPitchCount = new int[pitchCounter];
    int strikes = 0;
    int balls = 0;

    int count_R1C1 = 0;
    int count_R1C2 = 0;
    int count_R1C3 = 0;
    int count_R2C1 = 0;
    int count_R2C2 = 0;
    int count_R2C3 = 0;
    int count_R3C1 = 0;
    int count_R3C2 = 0;
    int count_R3C3 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        // Instantiate Firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Instantiate Buttons
        mR1C1 = (Button) findViewById(R.id.btnR1C1);
        mR1C2 = (Button) findViewById(R.id.btnR1C2);
        mR1C3 = (Button) findViewById(R.id.btnR1C3);
        mR2C1 = (Button) findViewById(R.id.btnR2C1);
        mR2C2 = (Button) findViewById(R.id.btnR2C2);
        mR2C3 = (Button) findViewById(R.id.btnR2C3);
        mR3C1 = (Button) findViewById(R.id.btnR3C1);
        mR3C2 = (Button) findViewById(R.id.btnR3C2);
        mR3C3 = (Button) findViewById(R.id.btnR3C3);
        mFinishGame = (Button) findViewById(R.id.btn_finish_game);

        // Instantiate TextViews
        mPitchCount = (TextView) findViewById(R.id.txt_pitch_count_counter);
        mTextStrikes = (TextView) findViewById(R.id.txt_strikes_counter);
        mTextBalls = (TextView) findViewById(R.id.txt_balls_counter);
        mTextR1C3 = (TextView) findViewById(R.id.txt_R1C3);
        mTextR2C1 = (TextView) findViewById(R.id.txt_R2C1);
        mTextR2C2 = (TextView) findViewById(R.id.txt_R2C2);
        mTextR2C3 = (TextView) findViewById(R.id.txt_R2C3);
        mTextR3C1 = (TextView) findViewById(R.id.txt_R3C1);
        mTextR3C2 = (TextView) findViewById(R.id.txt_R3C2);
        mTextR3C3 = (TextView) findViewById(R.id.txt_R3C3);

        // Needs refactoring eventually
        mR1C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase pitch count
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R1C1;
            }
        });

        mR1C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase pitch count
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R1C2;
            }
        });

        mR1C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR1C3.setText(Integer.toString(count_R1C3));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R1C3;
            }
        });

        mR2C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR2C1.setText(Integer.toString(count_R2C1));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C1;
            }
        });

        mR2C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR2C2.setText(Integer.toString(count_R2C2));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C2;
            }
        });

        mR2C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR2C3.setText(Integer.toString(count_R2C3));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C3;
            }
        });

        mR3C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR3C1.setText(Integer.toString(count_R3C1));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C1;
            }
        });

        mR3C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR3C2.setText(Integer.toString(count_R3C2));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C2;
            }
        });

        mR3C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR3C3.setText(Integer.toString(count_R3C3));
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C3;
            }
        });

        mFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send stats
                sendGameStats(0, 0, 0, pitchCounter, strikes, balls,
                        count_R1C1, count_R1C2, count_R1C3, count_R2C1, count_R2C2, count_R2C3,
                        count_R3C1, count_R3C2, count_R3C3);
            }
        });

    }

    private void sendGameStats(int gameID, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                               int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                               int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count) {
        // Defaulting to 0 until we establish further IDs
        PitchingStats pitchingStats = new PitchingStats(gameID, playerID, teamID, pitchCount, strikeCount, ballCount,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count);

        mDatabase.child("pitchingStats").child(Integer.toString(gameID)).setValue(pitchingStats);
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }

}
