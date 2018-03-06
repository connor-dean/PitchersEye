package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Utilities;

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
    Button mConfirmEvent;
    Button mConfirmPitcher;
    CheckBox mEventType;
    CheckBox mEventLocation;
    DatabaseReference mDatabase;
    EditText mEventName;
    EditText mPitcherFirst;
    EditText mPitcherLast;
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

    // Event information
    String eventName;
    String pitcherFirstName;
    String pitcherLastName;
    Boolean pitcherSet = false;
    Boolean eventSet = false;
    Boolean isGame;
    Boolean isHome;
    Boolean locationSelected = false;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String eventDate = df.format(Calendar.getInstance().getTime());

    // Results
    Button mFastball;
    Button mChangeup;
    Button mCurveball;
    Button mSlider;
    Button mOther;
    TextView mFastballCount;
    TextView mChangeupCount;
    TextView mCurveballCount;
    TextView mSliderCount;
    TextView mOtherCount;

    // Statistic counts
    int pitchCounter = 0;
    int strikes = 0;
    int balls = 0;

    // Events count
    int fastballCount = 0;
    int changeupCount = 0;
    int curveballCount = 0;
    int sliderCount = 0;
    int otherCount = 0;

    // Location counts
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
        mFastball = (Button) findViewById(R.id.btn_result_fastball);
        mChangeup = (Button) findViewById(R.id.btn_result_changeup);
        mCurveball = (Button) findViewById(R.id.btn_result_curve);
        mSlider = (Button) findViewById(R.id.btn_result_slider);
        mOther = (Button) findViewById(R.id.btn_result_other);
        mFinishGame = (Button) findViewById(R.id.btn_finish_game);
        mConfirmEvent = (Button) findViewById(R.id.btn_event_confirm);
        mConfirmPitcher = (Button) findViewById(R.id.btn_event_pitcher);

        // Instantiate CheckBoxes
        mEventType = (CheckBox) findViewById(R.id.chck_bx_event_type);
        mEventLocation = (CheckBox) findViewById(R.id.chck_bx_event_location);

        // Instantiate EditTexts
        mEventName = (EditText) findViewById(R.id.edt_txt_event_name_entry);
        mPitcherFirst = (EditText) findViewById(R.id.edt_txt_event_pitcher_first_name);
        mPitcherLast = (EditText) findViewById(R.id.edt_txt_event_pitcher_last_name);

        // Instantiate TextViews
        mPitchCount = (TextView) findViewById(R.id.txt_pitch_count_counter);
        mTextStrikes = (TextView) findViewById(R.id.txt_strikes_counter);
        mTextBalls = (TextView) findViewById(R.id.txt_balls_counter);
        mFastballCount = (TextView) findViewById(R.id.txt_fastball_counter);
        mChangeupCount = (TextView) findViewById(R.id.txt_changeup_counter);
        mCurveballCount = (TextView) findViewById(R.id.txt_curveball_counter);
        mSliderCount = (TextView) findViewById(R.id.txt_slider_counter);
        mOtherCount = (TextView) findViewById(R.id.txt_other_counter);


        // Check to see if there is input for the event and the pitcher
        // If there isn't, don't allow the user to tag the games
        // This should disable buttons on start

        // Also ensure that the workflow is set correctly on startup
        enableTagging(eventSet, pitcherSet);
        disableResults();

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

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
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

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR1C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R1C3;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR2C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C1;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR2C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C2;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR2C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R2C3;

                // Notify that we've selected the location for the workflow
                //locationSelected = false;
                enableTagging(locationSelected  = true);
            }
        });

        mR3C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C1;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR3C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C2;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        mR3C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPitchCount.setText(Integer.toString(++pitchCounter));

                // Increase strikes
                mTextStrikes.setText(Integer.toString(++strikes));

                // Increase region count
                ++count_R3C3;

                // Notify that we've selected the location for the workflow
                enableTagging(locationSelected = true);
            }
        });

        // Result events
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mFastballCount.setText(Integer.toString(++fastballCount));

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mChangeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mChangeupCount.setText(Integer.toString(++changeupCount));

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mCurveball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mCurveballCount.setText(Integer.toString(++curveballCount));

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mSliderCount.setText(Integer.toString(++sliderCount));

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mOtherCount.setText(Integer.toString(++otherCount));

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        // Enter event name
        mConfirmEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!eventSet) {
                    // Grab event information
                    saveEventInfo();

                    // Disable event components
                    mEventName.setEnabled(false);
                    mEventType.setEnabled(false);
                    mEventLocation.setEnabled(false);

                    // Change button text
                    mConfirmEvent.setText("Edit");
                } else {
                    // Enable components
                    mEventName.setEnabled(true);
                    mEventType.setEnabled(true);
                    mEventLocation.setEnabled(true);

                    mConfirmEvent.setText("Confirm");

                    // Say that there has been a previous entry
                    eventSet = false;
                }
                enableTagging(eventSet, pitcherSet);

                // TODO create a Game object and send info to Firebase on game completion
            }
        });

        mConfirmPitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If there isn't a pitcher set yet, save the input
                if (!pitcherSet) {
                    savePitcherInfo();

                    // Disable EditTexts
                    mPitcherFirst.setEnabled(false);
                    mPitcherLast.setEnabled(false);

                    // Change button text
                    mConfirmPitcher.setText("Change Pitcher");
                } else {
                    // If there IS a pitcher set and the "Change Pitcher" button is selected,
                    // update the fields to allow the user to enter a new pitcher
                    // Also sends the pitcher's stats after changing pitchers
                    mPitcherFirst.setEnabled(true);
                    mPitcherLast.setEnabled(true);

                    mConfirmPitcher.setText("Confirm Pitcher");

                    pitcherSet = false;

                    // TODO: Need to send statistics to Firebase for the previous pitcher
                    // Send the pitcher's stats on change
                    // Need to create variables for individual pitching statistics and not game stats


                }
                enableTagging(eventSet, pitcherSet);
            }
        });

        mFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the eventID and save with this event
                String eventID = Utilities.createRandomHex(6);

                // Send stats
                sendGameStats(eventID, eventName, eventDate, 0, 0, pitchCounter, strikes, balls,
                        count_R1C1, count_R1C2, count_R1C3, count_R2C1, count_R2C2, count_R2C3,
                        count_R3C1, count_R3C2, count_R3C3, fastballCount, changeupCount,
                        curveballCount, sliderCount, otherCount);
            }
        });

    }

    // We'll call this when checking to see if the event name and pitchers are set
    private void enableTagging(Boolean eventSet, Boolean pitcherSet) {
        if (eventSet && pitcherSet) {
            mR1C1.setEnabled(true);
            mR1C2.setEnabled(true);
            mR1C3.setEnabled(true);
            mR2C1.setEnabled(true);
            mR2C2.setEnabled(true);
            mR2C3.setEnabled(true);
            mR3C1.setEnabled(true);
            mR3C2.setEnabled(true);
            mR3C3.setEnabled(true);
        } else {
            mR1C1.setEnabled(false);
            mR1C2.setEnabled(false);
            mR1C3.setEnabled(false);
            mR2C1.setEnabled(false);
            mR2C2.setEnabled(false);
            mR2C3.setEnabled(false);
            mR3C1.setEnabled(false);
            mR3C2.setEnabled(false);
            mR3C3.setEnabled(false);
        }
    }

    // Wrapper method to enable tagging during the result workflow
    private void enableTagging(Boolean locationSelected) {
        if (locationSelected) {
            mR1C1.setEnabled(false);
            mR1C2.setEnabled(false);
            mR1C3.setEnabled(false);
            mR2C1.setEnabled(false);
            mR2C2.setEnabled(false);
            mR2C3.setEnabled(false);
            mR3C1.setEnabled(false);
            mR3C2.setEnabled(false);
            mR3C3.setEnabled(false);

            mFastball.setEnabled(true);
            mChangeup.setEnabled(true);
            mCurveball.setEnabled(true);
            mSlider.setEnabled(true);
            mOther.setEnabled(true);

            mConfirmEvent.setEnabled(false);
            mConfirmPitcher.setEnabled(false);
        } else {
            mR1C1.setEnabled(true);
            mR1C2.setEnabled(true);
            mR1C3.setEnabled(true);
            mR2C1.setEnabled(true);
            mR2C2.setEnabled(true);
            mR2C3.setEnabled(true);
            mR3C1.setEnabled(true);
            mR3C2.setEnabled(true);
            mR3C3.setEnabled(true);

            mFastball.setEnabled(false);
            mChangeup.setEnabled(false);
            mCurveball.setEnabled(false);
            mSlider.setEnabled(false);
            mOther.setEnabled(false);

            mConfirmEvent.setEnabled(true);
            mConfirmPitcher.setEnabled(true);
        }
    }

    private void disableResults() {
        mFastball.setEnabled(false);
        mChangeup.setEnabled(false);
        mCurveball.setEnabled(false);
        mSlider.setEnabled(false);
        mOther.setEnabled(false);
    }

    private void saveEventInfo() {
        eventName = mEventName.getText().toString().trim();
        if (!mEventType.isChecked()) {
            isGame = false;
        }
        if (!mEventLocation.isChecked()) {
            isHome = false;
        }
        eventSet = true;
    }

    private void savePitcherInfo() {
        pitcherFirstName = mPitcherFirst.getText().toString().trim();
        pitcherLastName = mPitcherLast.getText().toString().trim();
        pitcherSet = true;
    }

    private void sendGameStats(String eventID, String eventName, String eventDate, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                               int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                               int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                               int changeupCount, int curveballCount, int sliderCount, int otherCount) {
        // Defaulting some statistics to 0 until we establish further IDs
        EventStats gameStats = new EventStats(eventID, eventName, eventDate, playerID, teamID, pitchCount, strikeCount, ballCount,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, fastballCount,
                changeupCount, curveballCount, sliderCount, otherCount);

        mDatabase.child("eventStats").child(eventID).setValue(gameStats);
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }

}
