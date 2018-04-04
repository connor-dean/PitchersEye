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
        - Spinner validation is giving me issues  <--------------
   - Fix undo workflow XXX
   - Add "Ball" selection
   - Task to check for Firebase send success
   - Styling
        - Move colors and strings to res files
   - Heatmap
*/

public class TaggingActivity extends Activity implements EventInfoFragment.OnInputListener {

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
    ProgressBar mProgressFinishGame;
    Button mUndo;
    //Button mConfirmEvent;
    //Button mConfirmPitcher;
    /*CheckBox mEventType;
    CheckBox mEventLocation;*/
    DatabaseReference mDatabase;
    //EditText mEventName;
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

    // Results
    /*Button mFastball;
    Button mChangeup;
    Button mCurveball;
    Button mSlider;
    Button mOther;*/

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

        // Display DialogFragment
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

                // TODO move to fragment
                /*mSpinnerPitchers = (Spinner) findViewById(R.id.spin_pitcher_names);
                ArrayAdapter<String> pitchersAdapter = new ArrayAdapter<String>(TaggingActivity.this, android.R.layout.simple_spinner_item, pitchers);
                pitchersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerPitchers.setAdapter(pitchersAdapter);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // TODO cleanup
        // Check to see if there is input for the event and the pitcher
        // If there isn't, don't allow the user to tag the games
        // This should disable buttons on start
        // Also ensure that the workflow is set correctly on startup
        //enableTagging(eventSet, pitcherSet);
        //mUndo.setEnabled(false);
        //mFinishGame.setEnabled(false);
        //disableResults();

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

                mUndo.setEnabled(true);

                // Open ResultsFragment
                displayPitchResultsFragment();
            }
        });

        // TODO Clean up
        /*// Result events
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mEventFastballCount.setText(Integer.toString(++eventFastballCount));

                // Increase pitcher count
                mPitcherFastballCount.setText(Integer.toString(++pitcherFastballCount));

                // Keep track of previous pitch
                undoPitchType = 10;

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mChangeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mEventChangeupCount.setText(Integer.toString(++eventChangeupCount));

                // Increase pitcher count
                mPitcherChangeupCount.setText(Integer.toString(++pitcherChangeupCount));

                // Keep track of previous pitch
                undoPitchType = 11;

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mCurveball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mEventCurveballCount.setText(Integer.toString(++eventCurveballCount));

                // Increase pitcher count
                mPitcherCurveballCount.setText(Integer.toString(++pitcherCurveballCount));

                // Keep track of previous pitch
                undoPitchType = 12;

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mEventSliderCount.setText(Integer.toString(++eventSliderCount));

                // Increase pitcher count
                mPitcherSliderCount.setText(Integer.toString(++pitcherSliderCount));

                // Keep track of previous pitch
                undoPitchType = 13;

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                mEventOtherCount.setText(Integer.toString(++eventOtherCount));

                // Increase pitcher count
                mPitcherOtherCount.setText(Integer.toString(++pitcherOtherCount));

                // Keep track of previous pitch
                undoPitchType = 14;

                // Reenable grid
                enableTagging(locationSelected = false);
            }
        });*/

        // Enter event name
        // TODO moving to fragment
        /*mConfirmEvent.setOnClickListener(new View.OnClickListener() {
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
                if (!pitcherSet && mSpinnerPitchers.getSelectedItem().toString() != " ") {
                    savePitcherInfo();

                    // Disable EditTexts
                    //mPitcherFirst.setEnabled(false);
                    //mPitcherLast.setEnabled(false);
                    mSpinnerPitchers.setEnabled(false);

                    // Change button text
                    mConfirmPitcher.setText("Change Pitcher");
                } else if (mSpinnerPitchers.getSelectedItem().toString() == " ") {
                    Toast.makeText(getApplicationContext(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                } else {
                    // If there IS a pitcher set and the "Change Pitcher" button is selected,
                    // update the fields to allow the user to enter a new pitcher
                    // Also sends the pitcher's stats after changing pitchers
                    mSpinnerPitchers.setEnabled(true);

                    String eventID = Utilities.createRandomHex(6);

                    // Send individual statistics to Firebase
                    sendPitcherStats(eventID, eventName, eventDate, 0,
                            pitcherName,0, pitcherPitchCount, pitcherStrikesCount,
                            pitcherBallsCount, pitcherCount_R1C1, pitcherCount_R1C2, pitcherCount_R1C3, pitcherCount_R2C1,
                            pitcherCount_R2C2, pitcherCount_R2C3, pitcherCount_R3C1, pitcherCount_R3C2, pitcherCount_R3C3,
                            pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount, pitcherSliderCount, pitcherOtherCount);

                    mConfirmPitcher.setText("Confirm Pitcher");

                    pitcherSet = false;
                }
                enableTagging(eventSet, pitcherSet);
                mUndo.setEnabled(false);
                mFinishGame.setEnabled(false);
            }
        }); */

        // TODO needs refactoring
        mUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUndo.setEnabled(false);
                if (1 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R1C1;
                    --pitcherCount_R1C1;
                }
                if (2 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R1C2;
                    --pitcherCount_R1C2;
                }
                if (3 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R1C3;
                    --pitcherCount_R1C3;
                }
                if (4 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R2C1;
                    --pitcherCount_R2C1;
                }
                if (5 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R2C2;
                    --pitcherCount_R2C2;
                }
                if (6 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R2C3;
                    --pitcherCount_R2C3;
                }
                if (7 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R3C1;
                    --pitcherCount_R3C1;
                }
                if (8 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R3C2;
                    --pitcherCount_R3C2;
                }
                if (9 == undoPitchRegion) {
                    mEventPitchCount.setText(Integer.toString(--eventPitchCount));
                    mEventStrikes.setText(Integer.toString(--eventStrikesCount));
                    mPitcherPitchCount.setText(Integer.toString(--pitcherPitchCount));
                    mPitcherStrikes.setText(Integer.toString(--pitcherStrikesCount));
                    --eventCount_R3C2;
                    --pitcherCount_R1C1;
                }
                if (10 == undoPitchType) {
                    mEventFastballCount.setText(Integer.toString(--eventFastballCount));
                    mPitcherFastballCount.setText(Integer.toString(--pitcherFastballCount));
                }
                if (11 == undoPitchType) {
                    mEventChangeupCount.setText(Integer.toString(--eventChangeupCount));
                    mPitcherChangeupCount.setText(Integer.toString(--pitcherChangeupCount));
                }
                if (12 == undoPitchType) {
                    mEventCurveballCount.setText(Integer.toString(--eventCurveballCount));
                    mPitcherCurveballCount.setText(Integer.toString(--pitcherCurveballCount));
                }
                if (13 == undoPitchType) {
                    mEventSliderCount.setText(Integer.toString(--eventSliderCount));
                    mPitcherSliderCount.setText(Integer.toString(--pitcherSliderCount));
                }
                if (14 == undoPitchType) {
                    mEventOtherCount.setText(Integer.toString(--eventOtherCount));
                    mPitcherOtherCount.setText(Integer.toString(--pitcherOtherCount));
                }
                undoPitchRegion = 0;
                undoPitchType = 0;
                //mFinishGame.setEnabled(false);
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

            // TODO cleanup
            /*mFastball.setEnabled(true);
            mChangeup.setEnabled(true);
            mCurveball.setEnabled(true);
            mSlider.setEnabled(true);
            mOther.setEnabled(true);*/

            //mConfirmEvent.setEnabled(false);
            //mConfirmPitcher.setEnabled(false);

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

            // TODO cleanup
            /*mFastball.setEnabled(false);
            mChangeup.setEnabled(false);
            mCurveball.setEnabled(false);
            mSlider.setEnabled(false);
            mOther.setEnabled(false);*/

            //mConfirmEvent.setEnabled(true);
            //mConfirmPitcher.setEnabled(true);

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

    // TODO cleanup
    private void disableResults() {
        /*mFastball.setEnabled(false);
        mChangeup.setEnabled(false);
        mCurveball.setEnabled(false);
        mSlider.setEnabled(false);
        mOther.setEnabled(false);*/
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
        /*if (!mEventType.isChecked()) {
            isGame = false;
        }
        if (!mEventLocation.isChecked()) {
            isHome = false;
        }*/
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


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }
}
