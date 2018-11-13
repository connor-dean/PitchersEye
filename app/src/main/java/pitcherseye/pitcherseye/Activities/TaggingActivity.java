/*
 This Activity handles the majority of calculations and adjustments in the tracking of statistics in the
 application. We have a 3x3 "grid" of buttons and 4 buttons that will indicate a "ball" that are red before
 setting the opacity of the buttons completely. When a grid button is selected, we adjust the counts accordingly.

 This also hosts several fragments that improve the workflow of the application.

 We host:
    EventInfoFragment - Shown on startup and finish of the Activity. Prompts the user for input of the Event.
    ResultsFragment - Displays to prompt the user for the pitch type
    ChangePitcherFragment - Shows a spinner of loaded pitchers from Firebase

 We also have an undo workflow that will take the latest pitch type and region and adjust the counts.

 The system of how we are handling the events for the pressing of those buttons will be refactored in the
 future after a better alternative is found.
 */

package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import pitcherseye.pitcherseye.Fragments.ChangePitcherFragment;
import pitcherseye.pitcherseye.Fragments.EventInfoFragment;
import pitcherseye.pitcherseye.Fragments.ResultsFragment;
import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.Objects.PitcherStats;
import pitcherseye.pitcherseye.R;
import pitcherseye.pitcherseye.Utilities;

public class TaggingActivity extends Activity implements EventInfoFragment.OnInputListener, ResultsFragment.OnInputListener,
        ChangePitcherFragment.OnInputListener {

    // UI Components
    @BindView(R.id.btnR1C1) Button mR1C1;
    @BindView(R.id.btnR1C2) Button mR1C2;
    @BindView(R.id.btnR1C3) Button mR1C3;
    @BindView(R.id.btnR2C1) Button mR2C1;
    @BindView(R.id.btnR2C2) Button mR2C2;
    @BindView(R.id.btnR2C3) Button mR2C3;
    @BindView(R.id.btnR3C1) Button mR3C1;
    @BindView(R.id.btnR3C2) Button mR3C2;
    @BindView(R.id.btnR3C3) Button mR3C3;
    @BindView(R.id.btn_ball_low) Button mBallLow;
    @BindView(R.id.btn_ball_high) Button mBallHigh;
    @BindView(R.id.btn_ball_right) Button mBallRight;
    @BindView(R.id.btn_ball_left) Button mBallLeft;
    @BindView(R.id.btn_finish_game) Button mFinishGame;
    @BindView(R.id.progress_finish_game) ProgressBar mProgressFinishGame;
    @BindView(R.id.btn_undo) Button mUndo;
    @BindView(R.id.button_event_info) Button mChangePitcher;
    @BindView(R.id.txt_event_name) TextView mEventName;
    @BindView(R.id.txt_pitcher_name) TextView mPitcherName;
    @BindView(R.id.txt_event_fastball_count) TextView mEventFastballCount;
    @BindView(R.id.txt_event_strikes_count) TextView mEventStrikes;
    @BindView(R.id.txt_event_balls_count) TextView mEventBalls;
    @BindView(R.id.txt_event_pitch_count) TextView mEventPitchCount;
    @BindView(R.id.txt_event_changeup_count) TextView mEventChangeupCount;
    @BindView(R.id.txt_event_curveball_count) TextView mEventCurveballCount;
    @BindView(R.id.txt_event_slider_count) TextView mEventSliderCount;
    @BindView(R.id.txt_event_other_count) TextView mEventOtherCount;
    @BindView(R.id.txt_pitcher_pitch_count) TextView mPitcherPitchCount;
    @BindView(R.id.txt_pitcher_strikes_count) TextView mPitcherStrikes;
    @BindView(R.id.txt_pitcher_balls_count) TextView mPitcherBalls;
    @BindView(R.id.txt_pitcher_fastball_count) TextView mPitcherFastballCount;
    @BindView(R.id.txt_pitcher_changeup_count) TextView mPitcherChangeupCount;
    @BindView(R.id.txt_pitcher_curveball_count) TextView mPitcherCurveballCount;
    @BindView(R.id.txt_pitcher_slider_count) TextView mPitcherSliderCount;
    @BindView(R.id.txt_pitcher_other_count) TextView mPitcherOtherCount;

    DatabaseReference mDatabase;

    // Event information
    String eventName;
    String pitcherName;
    int pitcherSpinnerIndex = 0;

    Boolean isGame = true;
    Boolean isHome = true;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String eventDate = df.format(Calendar.getInstance().getTime());

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

    long pitcherStatsIDCount;
    long eventStatsIDCount;

    String eventID = Utilities.createRandomHex(6);

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    // We'll call this in other Activities to access this Activity
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);
        ButterKnife.bind(this);

        setEventInfoSet(false); // Default that the event isn't set
        setGame(true); // Set to true on startup
        setHome(true); // Set to true on startup

        // Instantiate Firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Display DialogFragment for initial data entry
        displayEventInfoFragment();

        mProgressFinishGame.setVisibility(View.GONE);
        
        // Set opacity on start
        resetHeatMap();

        // Instantiate and load pitchers into spinner
        // TODO see if we can get this into the Utility class
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> pitchers = new ArrayList<String>();
                pitchers.add(" ");
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String pitcherFName = areaSnapshot.child("fname").getValue(String.class);
                    String pitcherLName = areaSnapshot.child("lname").getValue(String.class);
                    String pitcherFullName = pitcherFName + " " + pitcherLName;
                    pitchers.add(pitcherFullName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TaggingActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Firebase doesn't have a built in auto-increment method for identifiers,
        // so we're grabbing the count of children in the database and incrementing the ID
        // and assigning the value to our counter. This will help with sorting in the RecyclerViews
        // in StatisticsActivity.
        mDatabase.child("pitcherStats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = "0";

                // Grab the key of the children and increment by one
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    max = child.getKey();
                    Log.i("Max pitcher", max);
                }
                pitcherStatsIDCount = Long.parseLong(max) + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TaggingActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.child("eventStats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String max = "0";

                // Grab the key of the children and increment by one
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    max = child.getKey();
                    Log.i("Max event", max);
                }
                eventStatsIDCount = Long.parseLong(max) + 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TaggingActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Open DialogFragment
        mChangePitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                ChangePitcherFragment pitcherFragment = new ChangePitcherFragment();
                pitcherFragment.show(fm, "Open ChangePitcherFragment");
            }
        });

        // TODO Needs refactoring eventually
        // Event handlers for the grid buttons
        // We could have used a switch statement and had dedicated methods for each of these,
        // but would be more difficult to debug. Also believe that this is the "cleaner" route
        // for the time being until we can find a better convention.
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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

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

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        true, false, false,
                        false);

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

                // Open ResultsFragment
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

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, true, false,
                        false);

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

                // Open ResultsFragment
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

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, false, true,
                        false);

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

                // Open ResultsFragment
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

                // Undo workflow
                setLastRegionResult(false, false, false,
                        false, false, false,
                        false, false, false,
                        false, false, false,
                        true);

                mUndo.setEnabled(true); // Enable the undo button after entering selection
                adjustHeatMap(); // Recalculate the heat map accordingly

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        // Event listener for the Undo workflow. Decrease the total pitch counts and find the last
        // entered statistics and decrement the counts appropriately.
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

        // Display EventInfoFragment to confirm results
        mFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayEventInfoFragment();
            }
        });
    }

    // Helper method to display the EventInfoFragment
    private void displayEventInfoFragment() {
        FragmentManager fm = getFragmentManager();
        EventInfoFragment infoFragment = new EventInfoFragment();
        infoFragment.show(fm, "Open EventInfoFragment");
    }

    // Helper method to display the PitchResultsFragment
    private void displayPitchResultsFragment() {
        FragmentManager fm = getFragmentManager();
        ResultsFragment resultsFragment = new ResultsFragment();
        resultsFragment.show(fm, "Open ResultsFragment");
    }

    // Once a game has been finished, grab the event stats and send them to Firebase using the EventStats object
    private void sendEventStats(String eventID, String eventName, String eventDate, Boolean isGame, Boolean isHome,
                                int pitcherID, int teamID, int pitchCount, int strikeCount, int eventBallCount,
                                int eventBallCountLow, int eventBallCountHigh, int eventBallCountLeft, int eventBallCountRight,
                                int R1C1Count, int R1C2Count, int R1C3Count, int R2C1Count, int R2C2Count,
                                int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int eventFastballCount,
                                int eventChangeupCount, int eventCurveballCount, int eventSliderCount, int eventOtherCount) {

        // Defaulting some statistics to 0 until we establish further IDs
        EventStats eventStats = new EventStats(eventID, eventName, eventDate, isGame, isHome, pitcherID, teamID, pitchCount, strikeCount,
                eventBallCount, eventBallCountLow, eventBallCountHigh, eventBallCountLeft, eventBallCountRight,
                R1C1Count, R1C2Count, R1C3Count, R2C1Count, R2C2Count,
                R2C3Count, R3C1Count, R3C2Count, R3C3Count, eventFastballCount,
                eventChangeupCount, eventCurveballCount, eventSliderCount, eventOtherCount);

        // TODO decide if we still need eventID or if we want to implement this in User as well
        mDatabase.child("eventStats").child(Long.toString(eventStatsIDCount)).setValue(eventStats);
    }

    // Once a pitcher has been changed, grab the pitcher's information and send that to Firebase using the PitcherStats object
    private void sendPitcherStats(String eventID, String eventName, String eventDate, Boolean isGame, Boolean isHome,
                                  int pitcherID, String pitcherName, int teamID, int pitchCount, int strikeCount, int pitcherBallCount,
                                  int pitcherBallCountLow, int pitcherBallCountHigh, int pitcherBallCountLeft, int pitcherBallCountRight,
                                  int R1C1Count, int R1C2Count, int R1C3Count, int R2C1Count, int R2C2Count,
                                  int R2C3Count, int R3C1Count, int R3C2Count, int R3C3Count, int fastballCount,
                                  int changeupCount, int curveballCount, int sliderCount, int otherCount) {

        // TODO decide if we still need this or eventID
        // String pitcherStatsID = Utilities.createRandomHex(6);

        PitcherStats pitcherStats = new PitcherStats(eventID, eventName, eventDate, isGame, isHome,
                pitcherID, pitcherName, teamID, pitchCount, strikeCount, pitcherBallCount,
                pitcherBallCountLow, pitcherBallCountHigh, pitcherBallCountLeft,
                pitcherBallCountRight, R1C1Count, R1C2Count, R1C3Count, R2C1Count,
                R2C2Count, R2C3Count, R3C1Count, R3C2Count, R3C3Count, fastballCount,
                changeupCount, curveballCount, sliderCount, otherCount);

        mDatabase.child("pitcherStats").child(Long.toString(pitcherStatsIDCount)).setValue(pitcherStats);
    }

    // Reset the heat map on startup and when we select a new pitcher
    public void resetHeatMap() {
        mR1C1.getBackground().setAlpha(0);
        mR1C2.getBackground().setAlpha(0);
        mR1C3.getBackground().setAlpha(0);
        mR2C1.getBackground().setAlpha(0);
        mR2C2.getBackground().setAlpha(0);
        mR2C3.getBackground().setAlpha(0);
        mR3C1.getBackground().setAlpha(0);
        mR3C2.getBackground().setAlpha(0);
        mR3C3.getBackground().setAlpha(0);
        mBallLow.getBackground().setAlpha(0);
        mBallHigh.getBackground().setAlpha(0);
        mBallLeft.getBackground().setAlpha(0);
        mBallRight.getBackground().setAlpha(0);
        mUndo.setEnabled(false);
    }

    // Calculate the percentages of the regions thrown to. We actually have the buttons set as
    // completely red and set the opacity to 0 when we start the Activity. We use setAlpha to adjust
    // the opacity. This method ranges from 0 to 255, with 0 having no opacity, resulting in red buttons.
    // We increase the opacity of the button based on the percentage that that area was thrown to.
    private void adjustHeatMap() {
        mR1C1.getBackground().setAlpha(pitcherCount_R1C1 * 255 / pitcherPitchCount);
        mR1C2.getBackground().setAlpha(pitcherCount_R1C2 * 255 / pitcherPitchCount);
        mR1C3.getBackground().setAlpha(pitcherCount_R1C3 * 255 / pitcherPitchCount);
        mR2C1.getBackground().setAlpha(pitcherCount_R2C1 * 255 / pitcherPitchCount);
        mR2C2.getBackground().setAlpha(pitcherCount_R2C2 * 255 / pitcherPitchCount);
        mR2C3.getBackground().setAlpha(pitcherCount_R2C3 * 255 / pitcherPitchCount);
        mR3C1.getBackground().setAlpha(pitcherCount_R3C1 * 255 / pitcherPitchCount);
        mR3C2.getBackground().setAlpha(pitcherCount_R3C2 * 255 / pitcherPitchCount);
        mR3C3.getBackground().setAlpha(pitcherCount_R3C3 * 255 / pitcherPitchCount);
        mBallLow.getBackground().setAlpha(pitcherBallsCountLow * 255 / pitcherPitchCount);
        mBallHigh.getBackground().setAlpha(pitcherBallsCountHigh * 255 / pitcherPitchCount);
        mBallLeft.getBackground().setAlpha(pitcherBallsCountLeft * 255 / pitcherPitchCount);
        mBallRight.getBackground().setAlpha(pitcherBallsCountRight * 255 / pitcherPitchCount);
    }

    // This is used to find hte last region that was thrown to for the "undo" workflow
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

    // The actual handler for resetting the counts. It'll grab the last region thrown to and decrement
    // the counts.
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

    // Lower the total pitch counts on undo
    private void decreaseTotalPitchCount() {
        mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
        mEventPitchCount.setText(Integer.toString(--eventPitchCount));
    }

    // This handles the pitch "type" count decrement for the undo workflow
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

    // Used by ResultsFragment to adjust the pitch type counts in TaggingActivity
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
        sendPitcherStats(eventID, eventName, eventDate, isGame, isHome, 0,
                pitcherName, 0, pitcherPitchCount, pitcherStrikesCount,
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

    // Helper method for when we finish an event. We'll display a progress spinner while it's sending information
    // to Firebase, and we'll save the pitching and event sessions and save the statistics to EventStats and PitcherStats.
    // On completion, we'll redirect the user back to MainActivity
    public void finishGameHelper() {
        // Display ProgressBar
        mProgressFinishGame.setVisibility(View.VISIBLE);

        // Send event stats
        sendEventStats(eventID, eventName, eventDate, isGame, isHome, 0, 0, eventPitchCount, eventStrikesCount, eventBallsCount,
                eventBallsCountLow, eventBallsCountHigh, eventBallsCountLeft, eventBallsCountRight,
                eventCount_R1C1, eventCount_R1C2, eventCount_R1C3, eventCount_R2C1, eventCount_R2C2, eventCount_R2C3,
                eventCount_R3C1, eventCount_R3C2, eventCount_R3C3, eventFastballCount, eventChangeupCount,
                eventCurveballCount, eventSliderCount, eventOtherCount);

        // Send individual stats as well
        sendPitcherStats(eventID, eventName, eventDate, isGame, isHome, 0,
                pitcherName, 0, pitcherPitchCount, pitcherStrikesCount,
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

    // Use this to retrieve pitcher information from ResultsFragment
    @Override
    public void sendResultsInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                                 int pitcherSliderCount, int pitcherOtherCount) {
        mPitcherFastballCount.setText(Integer.toString(pitcherFastballCount));
        mPitcherChangeupCount.setText(Integer.toString(pitcherChangeupCount));
        mPitcherCurveballCount.setText(Integer.toString(pitcherCurveballCount));
        mPitcherSliderCount.setText(Integer.toString(pitcherSliderCount));
        mPitcherOtherCount.setText(Integer.toString(pitcherOtherCount));
    }

    // Force the user to finish the event in case of an accidental exit with the back button
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please finish the game to exit the event", Toast.LENGTH_SHORT).show();
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

    public Boolean getEventInfoSet() {
        return eventInfoSet;
    }

    public void setEventInfoSet(Boolean eventInfoSet) {
        this.eventInfoSet = eventInfoSet;
    }
}
