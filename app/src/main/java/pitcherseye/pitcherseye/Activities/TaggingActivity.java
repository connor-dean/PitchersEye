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

import pitcherseye.pitcherseye.Fragments.ChangePitcherFragment;
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
   - Add "Ball" selection XXX
        - Hook up to "Undo" XXX
        - New Pitcher XXX
        - Adjust Objects to take balls regions XXX
   - Styling XXX
        - Add header to tagging XXX
        - Move colors and strings to res files XXX
   - Disable back button on dialog <---
   - Rework workflow for pitcher/event fragments XXX
   - Add speed workflow dialog
   - Pick backstack on main menu after finishing game XXX
   - Refactor code
   - Full testing regression
   - Heatmap (Tentative)
   - Task to check for Firebase send success
*/

public class TaggingActivity extends Activity implements EventInfoFragment.OnInputListener, ResultsFragment.OnInputListener,
                                                            ChangePitcherFragment.OnInputListener {

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
    Button mChangePitcher;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // Event information
    String eventName;
    String pitcherName;
    int pitcherSpinnerIndex = 0;

    Boolean isGame = true;
    Boolean isHome = true;
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

    // Balls
    int eventBallsCountLow = 0;
    int eventBallsCountHigh = 0;
    int eventBallsCountLeft = 0;
    int eventBallsCountRight = 0;

    int pitcherBallsCountLow = 0;
    int pitcherBallsCountHigh = 0;
    int pitcherBallsCountLeft = 0;
    int pitcherBallsCountRight = 0;

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
    Boolean isBallLow;
    Boolean isBallHigh;
    Boolean isBallLeft;
    Boolean isBallRight;

    Boolean eventInfoSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);
        setEventInfoSet(false); // Default that the event isn't set

        // Display DialogFragment for initial data entry
        displayEventInfoFragment();

        // Instantiate Firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Instantiate Buttons
        mChangePitcher = (Button) findViewById(R.id.button_event_info);
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

        // Disabble undo button at startup
        mUndo.setEnabled(false);

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
        mChangePitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fm = getFragmentManager();
                EventInfoFragment infoFragment = new EventInfoFragment();
                infoFragment.show(fm, "Open EventInfoFragment");*/
                FragmentManager fm = getFragmentManager();
                ChangePitcherFragment pitcherFragment = new ChangePitcherFragment();
                pitcherFragment.show(fm, "Open ChangePitcherFragment");
            }
        });

        // TODO Needs refactoring eventually
        mR1C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R1C1; // Increase region count
                ++pitcherCount_R1C1; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(true, false, false,
                        false, false, false,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR1C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R1C2; // Increase region count
                ++pitcherCount_R1C2; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, true, false,
                        false, false, false,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR1C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R1C3; // Increase region count
                ++pitcherCount_R1C3; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, true,
                        false, false, false,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R2C1; // Increase region count
                ++pitcherCount_R2C1; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        true, false, false,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R2C2; // Increase region count
                ++pitcherCount_R2C2; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, true, false,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR2C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R2C3; // Increase region count
                ++pitcherCount_R2C3; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, true,
                        false, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R3C1; // Increase region count
                ++pitcherCount_R3C1; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        true, false, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R3C2; // Increase region count
                ++pitcherCount_R3C2; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, true, false,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mR3C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mEventStrikes.setText(Integer.toString(++eventStrikesCount)); // Increase strikes
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount)); // Increase pitcher count
                mPitcherStrikes.setText(Integer.toString(++pitcherStrikesCount)); // Increase pitcher strikes
                ++eventCount_R3C3; // Increase region count
                ++pitcherCount_R3C3; // Increase pitcher region count

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, true,
                        false, false, false,
                        false);

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        mBallLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));
                mEventBalls.setText(Integer.toString(++eventBallsCount));
                mPitcherBalls.setText(Integer.toString(++pitcherBallsCount));
                ++eventBallsCountLow;
                ++pitcherBallsCountLow;
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        true, false, false,
                        false);
                mUndo.setEnabled(true);
                displayPitchResultsFragment();
            }
        });

        mBallHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));
                mEventBalls.setText(Integer.toString(++eventBallsCount));
                mPitcherBalls.setText(Integer.toString(++pitcherBallsCount));
                ++eventBallsCountHigh;
                ++pitcherBallsCountHigh;
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, true, false,
                        false);
                mUndo.setEnabled(true);
                displayPitchResultsFragment();
            }
        });

        mBallLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));
                mEventBalls.setText(Integer.toString(++eventBallsCount));
                mPitcherBalls.setText(Integer.toString(++pitcherBallsCount));
                ++eventBallsCountLeft;
                ++pitcherBallsCountLeft;
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, false, true,
                        false);
                mUndo.setEnabled(true);
                displayPitchResultsFragment();
            }
        });

        mBallRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEventPitchCount.setText(Integer.toString(++eventPitchCount));
                mPitcherPitchCount.setText(Integer.toString(++pitcherPitchCount));
                mEventBalls.setText(Integer.toString(++eventBallsCount));
                mPitcherBalls.setText(Integer.toString(++pitcherBallsCount));
                ++eventBallsCountRight;
                ++pitcherBallsCountRight;
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, false, false,
                        true);
                mUndo.setEnabled(true);
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
                        isR3C1, isR3C2, isR3C3,
                        isBallLow, isBallHigh,
                        isBallLeft, isBallRight);
                checkLastPitchResult();
            }
        });

        mFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display EventInfoFragment to confirm results
                displayEventInfoFragment();
            }
        });
    }

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

    // Once a game has been finished, grab the event stats and send them to Firebase
    private void sendEventStats(String eventID, String eventName, String eventDate, int playerID, int teamID, int pitchCount, int strikeCount,
                                int eventBallCount, int eventBallCountLow, int eventBallCountHigh, int eventBallCountLeft, int eventBallCountRight,
                               int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                               int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int eventFastballCount,
                               int eventChangeupCount, int eventCurveballCount, int eventSliderCount, int eventOtherCount) {
        // Defaulting some statistics to 0 until we establish further IDs
        EventStats eventStats = new EventStats(eventID, eventName, eventDate, playerID, teamID, pitchCount, strikeCount,
                eventBallCount, eventBallCountLow, eventBallCountHigh, eventBallCountLeft, eventBallCountRight,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, eventFastballCount,
                eventChangeupCount, eventCurveballCount, eventSliderCount, eventOtherCount);

        mDatabase.child("eventStats").child(eventID).setValue(eventStats);
    }

    // Once a pitcher has been changed, grab the pitcher's information and send that to Firebase
    private void sendPitcherStats(String eventID, String eventName, String eventDate, int playerID, String pitcherName, int teamID, int pitchCount, int strikeCount, int pitcherBallCount,
                                  int pitcherBallCountLow, int pitcherBallCountHigh, int pitcherBallCountLeft, int pitcherBallCountRight,
                                  int R1C1Count, int R1C2Count,  int R1C3Count, int R2C1Count, int R2C2Count,
                                  int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                                  int changeupCount, int curveballCount, int sliderCount, int otherCount) {

        PitcherStats pitcherStats = new PitcherStats(eventID, eventName, eventDate, playerID,
                pitcherName, teamID, pitchCount, strikeCount, pitcherBallCount, pitcherBallCountLow,
                pitcherBallCountHigh, pitcherBallCountLeft, pitcherBallCountRight,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, fastballCount,
                changeupCount, curveballCount, sliderCount, otherCount);

        mDatabase.child("pitcherStats").child(eventID).setValue(pitcherStats);
    }

    private void setLastRegionResult(Boolean isR1C1, Boolean isR1C2, Boolean isR1C3,
                                     Boolean isR2C1, Boolean isR2C2, Boolean isR2C3,
                                     Boolean isR3C1, Boolean isR3C2, Boolean isR3C3,
                                     Boolean isBallLow, Boolean isBallHigh,
                                     Boolean isBallLeft, Boolean isBallRight) {
        this.isR1C1 = isR1C1;
        this.isR1C2 = isR1C2;
        this.isR1C3 = isR1C3;
        this.isR2C1 = isR2C1;
        this.isR2C2 = isR2C2;
        this.isR2C3 = isR2C3;
        this.isR3C1 = isR3C1;
        this.isR3C2 = isR3C2;
        this.isR3C3 = isR3C3;
        this.isBallLow = isBallLow;
        this.isBallHigh = isBallHigh;
        this.isBallLeft = isBallLeft;
        this.isBallRight = isBallRight;
    }

    private void getLastRegionResults(Boolean isR1C1, Boolean isR1C2, Boolean isR1C3,
                                      Boolean isR2C1, Boolean isR2C2, Boolean isR2C3,
                                      Boolean isR3C1, Boolean isR3C2, Boolean isR3C3,
                                      Boolean isBallLow, Boolean isBallHigh,
                                      Boolean isBallLeft, Boolean isBallRight) {
        if (isR1C1) {
            --eventCount_R1C1;
            --pitcherCount_R1C1;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR1C2) {
            --eventCount_R1C2;
            --pitcherCount_R1C2;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR1C3) {
            --eventCount_R1C3;
            --pitcherCount_R1C3;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR2C1) {
            --eventCount_R2C1;
            --pitcherCount_R2C1;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR2C2) {
            --eventCount_R2C2;
            --pitcherCount_R2C2;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR2C3) {
            --eventCount_R2C3;
            --pitcherCount_R2C3;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR3C1) {
            --eventCount_R3C1;
            --pitcherCount_R3C1;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR3C2) {
            --eventCount_R3C2;
            --pitcherCount_R3C2;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isR3C3) {
            --eventCount_R3C3;
            --pitcherCount_R3C3;
            mEventStrikes.setText(Integer.toString(--eventStrikesCount));
            mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
        }
        if (isBallLow) {
            --eventBallsCountLow;
            --pitcherBallsCountLow;
            mEventBalls.setText(Integer.toString(--eventBallsCount));
            mPitcherBalls.setText(Integer.toString(--pitcherBallsCount));
        }
        if (isBallHigh) {
            --eventBallsCountHigh;
            --pitcherBallsCountHigh;
            mEventBalls.setText(Integer.toString(--eventBallsCount));
            mPitcherBalls.setText(Integer.toString(--pitcherBallsCount));
        }
        if (isBallLeft) {
            --eventBallsCountLeft;
            --pitcherBallsCountLeft;
            mEventBalls.setText(Integer.toString(--eventBallsCount));
            mPitcherBalls.setText(Integer.toString(--pitcherBallsCount));
        }
        if (isBallRight) {
            --eventBallsCountRight;
            --pitcherBallsCountRight;
            mEventBalls.setText(Integer.toString(--eventBallsCount));
            mPitcherBalls.setText(Integer.toString(--pitcherBallsCount));
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
        isBallLow = false;
        isBallHigh = false;
        isBallLeft = false;
        isBallRight = false;
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

    // Use this as a helper so we can call sendPitcherStats from EventInfoFragment
    public void sendPitcherStatsHelper() {
        String eventID = Utilities.createRandomHex(6);
        sendPitcherStats(eventID, eventName, eventDate, 0,
                pitcherName,0, pitcherPitchCount, pitcherStrikesCount,
                pitcherBallsCount, pitcherBallsCountLow, pitcherBallsCountHigh, pitcherBallsCountLeft, pitcherBallsCountRight,
                pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3, pitcherCount_R2C1,
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
        pitcherBallsCountLow = 0;
        pitcherBallsCountHigh = 0;
        pitcherBallsCountLeft = 0;
        pitcherBallsCountRight = 0;

        mPitcherPitchCount.setText(Integer.toString(pitcherPitchCount = 0));
        mPitcherStrikes.setText(Integer.toString(pitcherStrikesCount = 0));
        mPitcherBalls.setText(Integer.toString(pitcherBallsCount = 0));
        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount = 0));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeupCount = 0));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveballCount = 0));
        mPitcherSliderCount.setText(Integer.toString(pitcherSliderCount = 0));
        mPitcherOtherCount.setText(Integer.toString(pitcherOtherCount = 0));
    }

    public void finishGameHelper() {
        // Display ProgressBar
        mProgressFinishGame.setVisibility(View.VISIBLE);

        // Create the eventID and save with this event
        String eventID = Utilities.createRandomHex(6);

        // Send event stats
        sendEventStats(eventID, eventName, eventDate, 0, 0, eventPitchCount, eventStrikesCount, eventBallsCount,
                eventBallsCountLow, eventBallsCountHigh, eventBallsCountLeft, eventBallsCountRight,
                eventCount_R1C1, eventCount_R1C2, eventCount_R1C3, eventCount_R2C1, eventCount_R2C2, eventCount_R2C3,
                eventCount_R3C1, eventCount_R3C2, eventCount_R3C3, eventFastballCount, eventChangeupCount,
                eventCurveballCount, eventSliderCount, eventOtherCount);

        // Send individual stats as well
        sendPitcherStats(eventID, eventName, eventDate, 0,
                pitcherName,0, pitcherPitchCount, pitcherStrikesCount,
                pitcherBallsCount, pitcherBallsCountLow, pitcherBallsCountHigh, pitcherBallsCountLeft, pitcherBallsCountRight,
                pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3, pitcherCount_R2C1,
                pitcherCount_R2C2, pitcherCount_R2C3, pitcherCount_R3C1, pitcherCount_R3C2, pitcherCount_R3C3,
                pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount, pitcherSliderCount, pitcherOtherCount);

        // Send back to MainActivity
        Intent i = MainActivity.newIntent(TaggingActivity.this);
        startActivityForResult(i, REQUEST_CODE_CALCULATE);
        finish();
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
        this.setPitcherName(dialogPitcherName);
    }

    // Use this to retrieve pitcher information from ChangePitcherFragment
    @Override
    public void sendInput(String changePitcherDialogName, int changePitcherDialogSpinnerIndex) {
        mPitcherName.setText(changePitcherDialogName);

        pitcherName = changePitcherDialogName;
        pitcherSpinnerIndex = changePitcherDialogSpinnerIndex;
        this.setPitcherName(changePitcherDialogName);
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

    public int getPitcherSpinnerIndex() {
        return pitcherSpinnerIndex;
    }

    public Boolean getGame() {
        return isGame;
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

    public Boolean getEventInfoSet() { return eventInfoSet; }

    public void setEventInfoSet(Boolean eventInfoSet) { this.eventInfoSet = eventInfoSet; }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please finish the game to exit the event", Toast.LENGTH_SHORT).show();
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }
}
