package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pitcherseye.pitcherseye.Fragments.EventInfoFragment;
import pitcherseye.pitcherseye.Fragments.ResultsFragment;
import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.Objects.PitcherStats;
import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Utilities;

import static java.lang.Math.round;

// TODO master list
/* - Change pitcher workflow XXX
        - Spinner validation is giving me issues  XXX
   - Fix undo workflow XXX
        - Adjust for Result type XXX
        - Do something similar that we did for updating result count, save class level booleans in updatePitcherResultsCounts XXX
        - Refactor mUndo, can keep it similar, but need to break down. XXX
        - Fix undo button's disabled state XXX
   - Add "Ball" selection
   - Task to check for Firebase send success
   - Styling
        - Move colors and strings to res files
   - Disable back button on dialog
   - Heatmap (Tentative)
   - Full testing regression
*/

public class TaggingActivity extends Activity implements EventInfoFragment.OnInputListener, ResultsFragment.OnInputListener {

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
    Button mBallLow;
    Button mBallHigh;
    Button mBallRight;
    Button mBallLeft;
    Button mFinishGame;
    ProgressBar mProgressFinishGame;
    Button mUndo;
    DatabaseReference mDatabase;
    Button mEditEventInfo;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // Event information
    String eventName;
    String pitcherFirstName;
    String pitcherLastName;
    String pitcherName;
    Spinner mSpinnerPitchers;
    int pitcherSpinnerIndex = 0;
    Boolean pitcherSet = false;
    Boolean eventSet = false;

    Boolean isGame = false;
    Boolean isHome = false;
    Boolean locationSelected = false;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String eventDate = df.format(Calendar.getInstance().getTime());

    TextView mEventName;
    TextView mPitcherName;

    TextView mEventFastballCount;
    TextView mEventStrikes;
    TextView mEventBalls;
    TextView mEventPitchCount;
    TextView mEventChangeupCount;
    TextView mEventCurveballCount;
    TextView mEventSliderCount;
    TextView mEventOtherCount;

    TextView mPitcherPitchCount;
    TextView mPitcherStrikes;
    TextView mPitcherBalls;
    TextView mPitcherFastballCount;
    TextView mPitcherChangeupCount;
    TextView mPitcherCurveballCount;
    TextView mPitcherSliderCount;
    TextView mPitcherOtherCount;

    // Events count
    int eventPitchCount = 0;
    int eventStrikesCount = 0;
    int eventBallsCount = 0;

    int eventFastballCount = 0;
    int eventChangeupCount = 0;
    int eventCurveballCount = 0;
    int eventSliderCount = 0;
    int eventOtherCount = 0;

    // Individual Pitchers count
    int pitcherPitchCount = 0;
    int pitcherStrikesCount = 0;
    int pitcherBallsCount = 0;

    int pitcherFastballCount = 0;
    int pitcherChangeupCount = 0;
    int pitcherCurveballCount = 0;
    int pitcherSliderCount = 0;
    int pitcherOtherCount = 0;

    // Location counts
    int eventCount_R1C1 = 0;
    int eventCount_R1C2 = 0;
    int eventCount_R1C3 = 0;
    int eventCount_R2C1 = 0;
    int eventCount_R2C2 = 0;
    int eventCount_R2C3 = 0;
    int eventCount_R3C1 = 0;
    int eventCount_R3C2 = 0;
    int eventCount_R3C3 = 0;

    int pitcherCount_R1C1 = 0;
    int pitcherCount_R1C2 = 0;
    int pitcherCount_R1C3 = 0;
    int pitcherCount_R2C1 = 0;
    int pitcherCount_R2C2 = 0;
    int pitcherCount_R2C3 = 0;
    int pitcherCount_R3C1 = 0;
    int pitcherCount_R3C2 = 0;
    int pitcherCount_R3C3 = 0;

    // Undo
    int undoPitchRegion = 0;
    int undoPitchType = 0;

    Boolean isFastball;
    Boolean isChangeup;
    Boolean isCurveball;
    Boolean isSlider;
    Boolean isOther;
    Boolean isR1C1;
    Boolean isR1C2;
    Boolean isR1C3;
    Boolean isR2C1;
    Boolean isR2C2;
    Boolean isR2C3;
    Boolean isR3C1;
    Boolean isR3C2;
    Boolean isR3C3;

    // TODO WOW THIS SUCKKKKS
    double r1 = 0;
    double r2 = 0;
    double r3 = 0;
    double r4 = 0;
    double r5 = 0;
    double r6 = 0;
    double r7 = 0;
    double r8 = 0;
    double r9 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        // Display DialogFragment for initial data entry
        displayEventInfoFragment();

        // Instantiate Firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Instantiate Buttons
        mEditEventInfo = (Button) findViewById(R.id.button_event_info);
        mR1C1 = (Button) findViewById(R.id.btnR1C1); //mR1C1.getBackground().setAlpha(0);
        mR1C2 = (Button) findViewById(R.id.btnR1C2); //mR1C2.getBackground().setAlpha(0);
        mR1C3 = (Button) findViewById(R.id.btnR1C3); //mR1C3.getBackground().setAlpha(0);
        mR2C1 = (Button) findViewById(R.id.btnR2C1); //mR2C1.getBackground().setAlpha(0);
        mR2C2 = (Button) findViewById(R.id.btnR2C2); //mR2C2.getBackground().setAlpha(0);
        mR2C3 = (Button) findViewById(R.id.btnR2C3); //mR2C3.getBackground().setAlpha(0);
        mR3C1 = (Button) findViewById(R.id.btnR3C1); //mR3C1.getBackground().setAlpha(0);
        mR3C2 = (Button) findViewById(R.id.btnR3C2); //mR3C2.getBackground().setAlpha(0);
        mR3C3 = (Button) findViewById(R.id.btnR3C3); //mR3C3.getBackground().setAlpha(0);
        mBallLow = (Button) findViewById(R.id.btn_ball_low);
        mBallHigh = (Button) findViewById(R.id.btn_ball_high);
        mBallRight = (Button) findViewById(R.id.btn_ball_right);
        mBallLeft = (Button) findViewById(R.id.btn_ball_left);
        mFinishGame = (Button) findViewById(R.id.btn_finish_game);
        mUndo = (Button) findViewById(R.id.btn_undo);

        // Instantiate ProgressBar
        mProgressFinishGame = (ProgressBar) findViewById(R.id.progress_finish_game);
        mProgressFinishGame.setVisibility(View.GONE);

        // Instantiate TextViews
        mEventName = (TextView) findViewById(R.id.txt_event_name);
        mPitcherName = (TextView) findViewById(R.id.txt_pitcher_name);

        mEventPitchCount = (TextView) findViewById(R.id.txt_event_pitch_count);
        mEventStrikes = (TextView) findViewById(R.id.txt_event_strikes_count);
        mEventBalls = (TextView) findViewById(R.id.txt_event_balls_count);
        mEventFastballCount = (TextView) findViewById(R.id.txt_event_fastball_count);
        mEventChangeupCount = (TextView) findViewById(R.id.txt_event_changeup_count);
        mEventCurveballCount = (TextView) findViewById(R.id.txt_event_curveball_count);
        mEventSliderCount = (TextView) findViewById(R.id.txt_event_slider_count);
        mEventOtherCount = (TextView) findViewById(R.id.txt_event_other_count);

        mPitcherPitchCount = (TextView) findViewById(R.id.txt_pitcher_pitch_count);
        mPitcherStrikes = (TextView) findViewById(R.id.txt_pitcher_strikes_count);
        mPitcherBalls = (TextView) findViewById(R.id.txt_pitcher_balls_count);
        mPitcherFastballCount = (TextView) findViewById(R.id.txt_pitcher_fastball_count);
        mPitcherChangeupCount = (TextView) findViewById(R.id.txt_pitcher_changeup_count);
        mPitcherCurveballCount = (TextView) findViewById(R.id.txt_pitcher_curveball_count);
        mPitcherSliderCount = (TextView) findViewById(R.id.txt_pitcher_slider_count);
        mPitcherOtherCount = (TextView) findViewById(R.id.txt_pitcher_other_count);

        // Instantiate and load pitchers into spinner
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> pitchers = new ArrayList<String>();
                pitchers.add(" ");
                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String pitcherFName = areaSnapshot.child("fname").getValue(String.class);
                    String pitcherLName = areaSnapshot.child("lname").getValue(String.class);
                    String pitcherFullName = pitcherFName + " " + pitcherLName;
                    // Add empty space for the start
                    pitchers.add(pitcherFullName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Open DialogFragment
        mEditEventInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EventInfoFragment infoFragment = new EventInfoFragment();
                infoFragment.show(fm, "Hello");
            }
        });

        // TODO Needs refactoring eventually
        mR1C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase pitch count
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R1C1;

                // Increase pitcher region count
                ++pitcherCount_R1C1;

                // Keep track of previous pitch
                undoPitchRegion = 1;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                //adjustHeatMap(.5f, 0 , 0, 0 , 0 , 0 , 0 , 0, 0);
                //mR1C1.getBackground().setAlpha(50);

                r1++;

                // Undo workflow
                setLastRegionResult(true, false, false,
                        false, false, false,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR1C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase pitch count
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase eventStrikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R1C2;

                // Increase pitcher region count
                ++pitcherCount_R1C2;

                // Keep track of previous pitch
                undoPitchRegion = 2;

                // Notify that we've selected the location for the workflow
                //(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                //mR1C2.getBackground().setAlpha(50);
                r2++;

                // Undo workflow
                setLastRegionResult(false, true, false,
                        false, false, false,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR1C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R1C3;

                // Increase pitcher region count
                ++pitcherCount_R1C3;

                // Keep track of previous pitch
                undoPitchRegion = 3;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r3++;

                // Undo workflow
                setLastRegionResult(false, false, true,
                        false, false, false,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R2C1;

                // Increase pitcher region count
                ++pitcherCount_R2C1;

                // Keep track of previous pitch
                undoPitchRegion = 4;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r4++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        true, false, false,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R2C2;

                // Increase pitcher region count
                ++pitcherCount_R2C2;

                // Keep track of previous pitch
                undoPitchRegion = 5;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r5++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, true, false,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R2C3;

                // Increase pitcher region count
                ++pitcherCount_R2C3;

                // Keep track of previous pitch
                undoPitchRegion = 6;

                // Notify that we've selected the location for the workflow
                //locationSelected = false;
                //enableTagging(locationSelected  = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r6++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, true,
                        false, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R3C1;

                // Increase pitcher region count
                ++pitcherCount_R3C1;

                // Keep track of previous pitch
                undoPitchRegion = 7;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r7++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        true, false, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R3C2;

                // Increase pitcher region count
                ++pitcherCount_R3C2;

                // Keep track of previous pitch
                undoPitchRegion = 8;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();

                r8++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, true, false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));

                // Increase strikes
                mEventStrikes.setText(Integer.toString(++eventStrikesCount));

                // Increase pitcher count
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));

                // Increase pitcher strikes
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount));

                // Increase region count
                ++eventCount_R3C3;

                // Increase pitcher region count
                ++pitcherCount_R3C3;

                // Keep track of previous pitch
                undoPitchRegion = 9;

                // Notify that we've selected the location for the workflow
                //enableTagging(locationSelected = true);

                // Testing opacity
                //adjustHeatMapHelper();
                adjustHeatMap();
                r9++;

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, true);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        // TODO needs refactoring
        mUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUndo.setEnabled(false);

                decreaseTotalPitchCount();
                getLastRegionResults(isR1C1, isR1C2, isR1C3,
                        isR2C1, isR2C2, isR2C3,
                        isR3C1, isR3C2, isR3C3);
                checkLastPitchResult();
            }
        });

        mFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display ProgressBar
                mProgressFinishGame.setVisibility(View.VISIBLE);

                // Create the eventID and save with this event
                String eventID = Utilities.createRandomHex(6);

                // Send event stats
                sendEventStats(eventID, eventName, eventDate, 0, 0, eventPitchCount, eventStrikesCount, eventBallsCount,
                        eventCount_R1C1, eventCount_R1C2, eventCount_R1C3, eventCount_R2C1, eventCount_R2C2, eventCount_R2C3,
                        eventCount_R3C1, eventCount_R3C2, eventCount_R3C3, eventFastballCount, eventChangeupCount,
                        eventCurveballCount, eventSliderCount, eventOtherCount);

                // Send individual stats as well
                sendPitcherStats(eventID, eventName, eventDate, 0,
                        pitcherName,0, pitcherPitchCount, pitcherStrikesCount,
                        pitcherBallsCount, pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3, pitcherCount_R2C1,
                        pitcherCount_R2C2, pitcherCount_R2C3, pitcherCount_R3C1, pitcherCount_R3C2, pitcherCount_R3C3,
                        pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount, pitcherSliderCount, pitcherOtherCount);

                // Send back to MainActivity
                Intent i = MainActivity.newIntent(TaggingActivity.this);
                startActivityForResult(i, REQUEST_CODE_CALCULATE);
                finish();
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
            mUndo.setEnabled(true);
            mFinishGame.setEnabled(true);
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
            mUndo.setEnabled(false);
            mFinishGame.setEnabled(false);
        }
        // TODO
        //adjustHeatMapHelper();
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

            mUndo.setEnabled(false);
            mFinishGame.setEnabled(false);
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

            mUndo.setEnabled(true);
            mFinishGame.setEnabled(true);
        }
        // TODO
        //adjustHeatMapHelper();
    }

    // We'll use this method in the EventInfoFragment after info has been entered
    public void enableTagging() {
        mR1C1.setEnabled(true);
        mR1C2.setEnabled(true);
        mR1C3.setEnabled(true);
        mR2C1.setEnabled(true);
        mR2C2.setEnabled(true);
        mR2C3.setEnabled(true);
        mR3C1.setEnabled(true);
        mR3C2.setEnabled(true);
        mR3C3.setEnabled(true);
    }

    // Adjust the heatmap
/*    private void adjustHeatMap(int pitchCount_R1C1, int pitchCount_R1C2,int pitchCount_R1C3,
                               int pitchCount_R2C1, int pitchCount_R2C2,int pitchCount_R2C3,
                               int pitchCount_R3C1, int pitchCount_R3C2,int pitchCount_R3C3) {*/
      private void adjustHeatMap() {
        // Calculation
        // eventCount_R1C1 / totalCount * 255
/*        mR1C1.getBackground().setAlpha(round(eventCount_R1C1 / pitcherPitchCount * 255));
        mR1C2.getBackground().setAlpha(round(eventCount_R1C2 / pitcherPitchCount * 255));
        mR1C3.getBackground().setAlpha(round(eventCount_R1C3 / pitcherPitchCount * 255));
        mR2C1.getBackground().setAlpha(round(eventCount_R2C1 / pitcherPitchCount * 255));
        mR2C2.getBackground().setAlpha(round(eventCount_R2C2 / pitcherPitchCount * 255));
        mR2C3.getBackground().setAlpha(round(eventCount_R2C3 / pitcherPitchCount * 255));
        mR3C1.getBackground().setAlpha(round(eventCount_R3C1 / pitcherPitchCount * 255));
        mR3C2.getBackground().setAlpha(round(eventCount_R3C2 / pitcherPitchCount * 255));
        mR3C3.getBackground().setAlpha(round(eventCount_R3C3 / pitcherPitchCount * 255));

        mR1C1.setText(Integer.toString(round(eventCount_R1C1 / pitcherPitchCount * 255)));
        mR1C2.setText(Integer.toString(round(eventCount_R1C2 / pitcherPitchCount * 255)));
        mR1C3.setText(Integer.toString(round(eventCount_R1C3 / pitcherPitchCount * 255)));
        mR2C1.setText(Integer.toString(round(eventCount_R2C1 / pitcherPitchCount * 255)));
        mR2C2.setText(Integer.toString(round(eventCount_R2C2 / pitcherPitchCount * 255)));
        mR2C3.setText(Integer.toString(round(eventCount_R2C3 / pitcherPitchCount * 255)));
        mR3C1.setText(Integer.toString(round(eventCount_R3C1 / pitcherPitchCount * 255)));
        mR3C2.setText(Integer.toString(round(eventCount_R3C2 / pitcherPitchCount * 255)));
        mR3C3.setText(Integer.toString(round(eventCount_R3C3 / pitcherPitchCount * 255)));*/

//          int stuff1 = (int) r1 / pitcherPitchCount;
//          int stuff2 = (int) r2 / pitcherPitchCount;
//          int stuff3 = (int) r3 / pitcherPitchCount;
//          int stuff4 = (int) r4 / pitcherPitchCount;
//          int stuff5 = (int) r5 / pitcherPitchCount;
//          int stuff6 = (int) r6 / pitcherPitchCount;
//          int stuff7 = (int) r7 / pitcherPitchCount;
//          int stuff8 = (int) r8 / pitcherPitchCount;
//          int stuff9 = (int) r9 / pitcherPitchCount;




          /*mR1C1.getBackground().setAlpha(stuff1);
          mR1C2.getBackground().setAlpha(stuff2);
          mR1C3.getBackground().setAlpha(stuff3);
          mR2C1.getBackground().setAlpha(stuff4);
          mR2C2.getBackground().setAlpha(stuff5);
          mR2C3.getBackground().setAlpha(stuff6);
          mR3C1.getBackground().setAlpha(stuff7);
          mR3C2.getBackground().setAlpha(stuff8);
          mR3C3.getBackground().setAlpha(stuff9);

          mR1C1.setText(Integer.toString(stuff1));
          mR1C2.setText(Double.toString((r2 / pitcherPitchCount * 255)));
          mR1C3.setText(Double.toString((r3 / pitcherPitchCount * 255)));
          mR2C1.setText(Double.toString((r4 / pitcherPitchCount * 255)));
          mR2C2.setText(Double.toString((r5 / pitcherPitchCount * 255)));
          mR2C3.setText(Double.toString((r6 / pitcherPitchCount * 255)));
          mR3C1.setText(Double.toString(r7 / pitcherPitchCount * 255));
          mR3C2.setText(Double.toString((r8 / pitcherPitchCount * 255)));
          mR3C3.setText(Double.toString((r9 / pitcherPitchCount * 255)));*/

    }

/*    private void adjustHeatMapHelper() {
        adjustHeatMap(pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3,
                pitcherCount_R2C1, pitcherCount_R2C2, pitcherCount_R2C3,
                pitcherCount_R3C1, pitcherCount_R3C2, pitcherCount_R3C3);
    }*/

    // TODO improve logs
    private void displayEventInfoFragment() {
        FragmentManager fm = getFragmentManager();
        EventInfoFragment infoFragment = new EventInfoFragment();
        infoFragment.show(fm, "Open EventInfoFragment");
    }

    private void displayPitchResultsFragment() {
        FragmentManager fm = getFragmentManager();
        ResultsFragment resultsFragment = new ResultsFragment();
        resultsFragment.show(fm, "Open ResultsFragment");
    }

    // TODO don't think we need this
    private void saveEventInfo() {
        eventName = mEventName.getText().toString().trim();
        eventSet = true;
    }

    // TODO don't think we need this
    private void savePitcherInfo() {
        pitcherName = mSpinnerPitchers.getSelectedItem().toString();
        pitcherSet = true;
    }

    // Once a game has been finished, grab the event stats and send them to Firebase
    private void sendEventStats(String eventID, String eventName, String eventDate, int playerID, int teamID, int pitchCount, int strikeCount, int ballCount,
                               int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                               int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int eventFastballCount,
                               int eventChangeupCount, int eventCurveballCount, int eventSliderCount, int eventOtherCount) {
        // Defaulting some statistics to 0 until we establish further IDs
        EventStats eventStats = new EventStats(eventID, eventName, eventDate, playerID, teamID, pitchCount, strikeCount, ballCount,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, eventFastballCount,
                eventChangeupCount, eventCurveballCount, eventSliderCount, eventOtherCount);

        mDatabase.child("eventStats").child(eventID).setValue(eventStats);
    }

    // Once a pitcher has been changed, grab the pitcher's information and send that to Firebase
    private void sendPitcherStats(String eventID, String eventName, String eventDate, int playerID, String pitcherName, int teamID, int pitchCount, int strikeCount, int ballCount,
                                  int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                                  int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                                  int changeupCount, int curveballCount, int sliderCount, int otherCount) {

        PitcherStats pitcherStats = new PitcherStats(eventID, eventName, eventDate, playerID,
                pitcherName, teamID, pitchCount, strikeCount, ballCount,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, fastballCount,
                changeupCount, curveballCount, sliderCount, otherCount);

        mDatabase.child("pitcherStats").child(eventID).setValue(pitcherStats);

        // Reset pitcher statistics
        //resetPitcherStats();
    }

    private void setLastRegionResult(Boolean isR1C1, Boolean isR1C2, Boolean isR1C3,
                                     Boolean isR2C1, Boolean isR2C2, Boolean isR2C3,
                                     Boolean isR3C1, Boolean isR3C2, Boolean isR3C3) {
        this.isR1C1 = isR1C1;
        this.isR1C2 = isR1C2;
        this.isR1C3 = isR1C3;
        this.isR2C1 = isR2C1;
        this.isR2C2 = isR2C2;
        this.isR2C3 = isR2C3;
        this.isR3C1 = isR3C1;
        this.isR3C2 = isR3C2;
        this.isR3C3 = isR3C3;
    }

    private void getLastRegionResults(Boolean isR1C1, Boolean isR1C2, Boolean isR1C3,
                                      Boolean isR2C1, Boolean isR2C2, Boolean isR2C3,
                                      Boolean isR3C1, Boolean isR3C2, Boolean isR3C3) {
        if (isR1C1) {
            --eventCount_R1C1;
            --pitcherCount_R1C1;
        }
        if (isR1C2) {
            --eventCount_R1C2;
            --pitcherCount_R1C2;
        }
        if (isR1C3) {
            --eventCount_R1C3;
            --pitcherCount_R1C3;
        }
        if (isR2C1) {
            --eventCount_R2C1;
            --pitcherCount_R2C1;
        }
        if (isR2C2) {
            --eventCount_R2C2;
            --pitcherCount_R2C2;
        }
        if (isR2C3) {
            --eventCount_R2C3;
            --pitcherCount_R2C3;
        }
        if (isR3C1) {
            --eventCount_R3C1;
            --pitcherCount_R3C1;
        }
        if (isR3C2) {
            --eventCount_R3C2;
            --pitcherCount_R3C2;
        }
        if (isR3C3) {
            --eventCount_R3C3;
            --pitcherCount_R3C3;
        }
        isR1C1 = false;
        isR1C2 = false;
        isR1C3 = false;
        isR2C1 = false;
        isR2C2 = false;
        isR2C3 = false;
        isR3C1 = false;
        isR3C2 = false;
        isR3C3 = false;
    }

    private void decreaseTotalPitchCount() {
        mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
        mEventPitchCount.setText(Integer.toString(--eventPitchCount));
    }

    private void checkLastPitchResult() {
        if (isFastball) {
            mPitcherFastballCount.setText(Integer.toString(--pitcherFastballCount));
            mEventFastballCount.setText(Integer.toString(--eventFastballCount));
        }
        if (isChangeup) {
            mPitcherChangeupCount.setText(Integer.toString(--pitcherChangeupCount));
            mEventChangeupCount.setText(Integer.toString(--eventChangeupCount));
        }
        if (isCurveball) {
            mPitcherCurveballCount.setText(Integer.toString(--pitcherCurveballCount));
            mEventCurveballCount.setText(Integer.toString(--eventCurveballCount));
        }
        if (isSlider) {
            mPitcherSliderCount.setText(Integer.toString(--pitcherSliderCount));
            mEventSliderCount.setText(Integer.toString(--eventSliderCount));
        }
        if (isOther) {
            mPitcherOtherCount.setText(Integer.toString(--pitcherOtherCount));
            mEventOtherCount.setText(Integer.toString(--eventOtherCount));
        }
        isFastball = false;
        isChangeup = false;
        isCurveball = false;
        isSlider = false;
        isOther = false;
    }

    // Use this to update count on TextView components
    public void updatePitcherResultsCounts(int pitcherFastball, int pitcherChangeup, int pitcherCurveball,
                                            int pitcherSlider, int pitcherOther) {

        pitcherFastballCount = pitcherFastball;
        pitcherChangeupCount = pitcherChangeup;
        pitcherCurveballCount = pitcherCurveball;
        pitcherSliderCount = pitcherSlider;
        pitcherOtherCount = pitcherOther;

        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeup));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveball));
        mPitcherSliderCount.setText(Integer.toString(pitcherSlider));
        mPitcherOtherCount.setText(Integer.toString(pitcherOther));
        //updateComponents();
    }

    public void updatePitcherResultsCounts(Boolean isFastball, Boolean isChangeup, Boolean isCurveball,
                                           Boolean isSlider, Boolean isOther) {

        // Save the results so we can tell which pitch was thrown last in case of an "undo"
        this.isFastball = isFastball;
        this.isChangeup = isChangeup;
        this.isCurveball = isCurveball;
        this.isSlider = isSlider;
        this.isOther = isOther;

        if (isFastball) {
            mPitcherFastballCount.setText(Integer.toString(++pitcherFastballCount));
            mEventFastballCount.setText(Integer.toString(++eventFastballCount));
        }
        if (isChangeup) {
            mPitcherChangeupCount.setText(Integer.toString(++pitcherChangeupCount));
            mEventChangeupCount.setText(Integer.toString(++eventChangeupCount));
        }
        if (isCurveball) {
            mPitcherCurveballCount.setText(Integer.toString(++pitcherCurveballCount));
            mEventCurveballCount.setText(Integer.toString(++eventCurveballCount));
        }
        if (isSlider) {
            mPitcherSliderCount.setText(Integer.toString(++pitcherSliderCount));
            mEventSliderCount.setText(Integer.toString(++eventSliderCount));
        }
        if (isOther) {
            mPitcherOtherCount.setText(Integer.toString(++pitcherOtherCount));
            mEventOtherCount.setText(Integer.toString(++eventOtherCount));
        }
    }

    public void updateComponents() {
        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeupCount));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveballCount));
        mPitcherSliderCount.setText(Integer.toString(pitcherSliderCount));
        mPitcherOtherCount.setText(Integer.toString(pitcherOtherCount));
    }

    // Use this as a helper so we can call sendPitcherStats from EventInfoFragment
    public void sendPitcherStatsWrapper() {
        String eventID = Utilities.createRandomHex(6);
        sendPitcherStats(eventID, eventName, eventDate, 0,
                pitcherName,0, pitcherPitchCount, pitcherStrikesCount,
                pitcherBallsCount, pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3, pitcherCount_R2C1,
                pitcherCount_R2C2, pitcherCount_R2C3, pitcherCount_R3C1, pitcherCount_R3C2, pitcherCount_R3C3,
                pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount, pitcherSliderCount, pitcherOtherCount);
        resetPitcherStats();
    }

    // We'll call this once the user changes pitchers. We'll keep the event stats but reset the current pitcher's
    public void resetPitcherStats() {
        pitcherCount_R1C1 = 0;
        pitcherCount_R1C2 = 0;
        pitcherCount_R1C3 = 0;
        pitcherCount_R2C1 = 0;
        pitcherCount_R2C2 = 0;
        pitcherCount_R2C3 = 0;
        pitcherCount_R3C1 = 0;
        pitcherCount_R3C2 = 0;
        pitcherCount_R3C3 = 0;

        mPitcherPitchCount.setText(Integer.toString(pitcherPitchCount = 0));
        mPitcherStrikes.setText(Integer.toString(pitcherStrikesCount = 0));
        mPitcherBalls.setText(Integer.toString(pitcherBallsCount = 0));
        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount = 0));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeupCount = 0));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveballCount = 0));
        mPitcherSliderCount.setText(Integer.toString(pitcherSliderCount = 0));
        mPitcherOtherCount.setText(Integer.toString(pitcherOtherCount = 0));
    }

    // Use this to retrieve information from EventInfoFragment's entry
    @Override
    public void sendInput(String dialogEventName, Boolean dialogIsGame, Boolean dialogIsHome, String dialogPitcherName, int dialogPitcherSpinnerIndex) {
        mEventName.setText(dialogEventName);
        mPitcherName.setText(dialogPitcherName);

        eventName = dialogEventName;
        isGame = dialogIsGame;
        isHome = dialogIsHome;
        pitcherName = dialogPitcherName;
        pitcherSpinnerIndex = dialogPitcherSpinnerIndex;

        EventInfoFragment eventInfoFragment = new EventInfoFragment();
        //eventInfoFragment.setPitcherName(dialogPitcherName);
        this.setPitcherName(dialogPitcherName);
    }

    @Override
    public void sendResultsInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                          int pitcherSliderCount, int pitcherOtherCount) {
        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeupCount));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveballCount));
        mPitcherSliderCount.setText(Integer.toString(pitcherSliderCount));
        mPitcherOtherCount.setText(Integer.toString(pitcherOtherCount));
    }

    // Getters/Setters
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getPitcherSpinnerIndex() {
        return pitcherSpinnerIndex;
    }

    public void setPitcherSpinnerIndex(int pitcherSpinnerIndex) {
        this.pitcherSpinnerIndex = pitcherSpinnerIndex;
    }

    public Boolean getGame() {
        return isGame;
    }

    public void setGame(Boolean game) {
        isGame = game;
    }
    public Boolean getHome() {
        return isHome;
    }

    public void setHome(Boolean home) {
        isHome = home;
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public void setPitcherName(String pitcherName) {
        this.pitcherName = pitcherName;
    }

    public int getPitcherPitchCount() {
        return pitcherPitchCount;
    }



    public int getPitcherFastballCount() {
        return pitcherFastballCount;
    }

    public void setPitcherFastballCount(int pitcherFastballCount) {
        this.pitcherFastballCount = pitcherFastballCount;
    }


    public int getPitcherChangeupCount() {
        return pitcherChangeupCount;
    }

    public void setPitcherChangeupCount(int pitcherChangeupCount) {
        this.pitcherChangeupCount = pitcherChangeupCount;
    }

    public int getPitcherCurveballCount() {
        return pitcherCurveballCount;
    }

    public void setPitcherCurveballCount(int pitcherCurveballCount) {
        this.pitcherCurveballCount = pitcherCurveballCount;
    }

    public int getPitcherSliderCount() {
        return pitcherSliderCount;
    }

    public void setPitcherSliderCount(int pitcherSliderCount) {
        this.pitcherSliderCount = pitcherSliderCount;
    }

    public int getPitcherOtherCount() {
        return pitcherOtherCount;
    }

    public void setPitcherOtherCount(int pitcherOtherCount) {
        this.pitcherOtherCount = pitcherOtherCount;
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }
}
