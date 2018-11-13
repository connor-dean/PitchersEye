/*
 This Activity handles all of the reports that are created in the StatisticsActivity. We don't
 handle any calculations yet, since StatisticsActivity handles most of the heavy lifting. All we do
 is grab the intents from the StatisticsActivity and save them as variables and assign them to UI
 components based on what type of statistics the user selects from the RecyclerView in
 StatisticsActivity (ex. Events vs. Pitcher).
 */

package pitcherseye.pitcherseye.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import pitcherseye.pitcherseye.R;

public class ReportsActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.txt_reports_event_header) TextView mReportsHeader;
    @BindView(R.id.txt_reports_event_pitch_count) TextView mReportsPitchCount;
    @BindView(R.id.txt_reports_event_strikes_count) TextView mReportsStrikeCount;
    @BindView(R.id.txt_reports_event_balls_count) TextView mReportsBallCount;
    @BindView(R.id.txt_reports_event_fastball_count) TextView mReportsFastballCount;
    @BindView(R.id.txt_reports_event_changeup_count) TextView mReportsChangeupCount;
    @BindView(R.id.txt_reports_event_curveball_count) TextView mReportsCurveballCount;
    @BindView(R.id.txt_reports_event_slider_count) TextView mReportsSliderCount;
    @BindView(R.id.txt_reports_event_other_count) TextView mReportsOtherCount;
    @BindView(R.id.txt_reports_event_r1c1_count) TextView mReportsR1C1;
    @BindView(R.id.txt_reports_event_r1c2_count) TextView mReportsR1C2;
    @BindView(R.id.txt_reports_event_r1c3_count) TextView mReportsR1C3;
    @BindView(R.id.txt_reports_event_r2c1_count) TextView mReportsR2C1;
    @BindView(R.id.txt_reports_event_r2c2_count) TextView mReportsR2C2;
    @BindView(R.id.txt_reports_event_r2c3_count) TextView mReportsR2C3;
    @BindView(R.id.txt_reports_event_r3c1_count) TextView mReportsR3C1;
    @BindView(R.id.txt_reports_event_r3c2_count) TextView mReportsR3C2;
    @BindView(R.id.txt_reports_event_r3c3_count) TextView mReportsR3C3;
    @BindView(R.id.txt_reports_event_ball_low_count) TextView mReportsBallLow;
    @BindView(R.id.txt_reports_event_ball_high_count) TextView mReportsBallHigh;
    @BindView(R.id.txt_reports_event_ball_left_count) TextView mReportsBallLeft;
    @BindView(R.id.txt_reports_event_ball_right_count) TextView mReportsBallRight;

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

    // Used to determine if the user selected a Event or Pitchers RecyclerView
    // item in StatisticsActivity. Events = true, Pitchers = false.
    Boolean tabSelection;

    // Variables that we'll use to assign values coming from intents in StatisticsActivity
    String eventName;
    String pitcherName;
    int index;
    int eventPitchCount;
    int eventStrikeCount;
    int eventFastballCount;
    int eventChangeupCount;
    int eventCurveballCount;
    int eventSliderCount;
    int eventOtherCount;
    int eventR1C1;
    int eventR1C2;
    int eventR1C3;
    int eventR2C1;
    int eventR2C2;
    int eventR2C3;
    int eventR3C1;
    int eventR3C2;
    int eventR3C3;
    int eventBallCount;
    int eventBallLow;
    int eventBallHigh;
    int eventBallLeft;
    int eventBallRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        ButterKnife.bind(this);

        // Load values into variables
        index = getIntent().getIntExtra("index", 99); // Doesn't get used for now but can be used for debugging in the future
        tabSelection = getIntent().getBooleanExtra("tabSelection", true); // Determines if an Event or Pitcher RecyclerView item was selected
        pitcherName = getIntent().getStringExtra("pitcherName");
        eventName = getIntent().getStringExtra("eventName");
        eventPitchCount = getIntent().getIntExtra("totalPitchCount", 99);
        eventStrikeCount = getIntent().getIntExtra("eventStrikeCount", 99);
        eventBallCount = getIntent().getIntExtra("eventBallCount", 99);
        eventFastballCount = getIntent().getIntExtra("eventFastballCount", 99);
        eventChangeupCount = getIntent().getIntExtra("eventChangeupCount", 99);
        eventCurveballCount = getIntent().getIntExtra("eventCurveballCount", 99);
        eventSliderCount = getIntent().getIntExtra("eventSliderCount", 99);
        eventOtherCount = getIntent().getIntExtra("eventOtherCount", 99);
        eventR1C1 = getIntent().getIntExtra("eventR1C1", 99);
        eventR1C2 = getIntent().getIntExtra("eventR1C2", 99);
        eventR1C3 = getIntent().getIntExtra("eventR1C3", 99);
        eventR2C1 = getIntent().getIntExtra("eventR2C1", 99);
        eventR2C2 = getIntent().getIntExtra("eventR2C2", 99);
        eventR2C3 = getIntent().getIntExtra("eventR2C3", 99);
        eventR3C1 = getIntent().getIntExtra("eventR3C1", 99);
        eventR3C2 = getIntent().getIntExtra("eventR3C2", 99);
        eventR3C3 = getIntent().getIntExtra("eventR3C3", 99);
        eventBallLow = getIntent().getIntExtra("eventBallLow", 99);
        eventBallHigh = getIntent().getIntExtra("eventBallHigh", 99);
        eventBallLeft = getIntent().getIntExtra("eventBallLeft", 99);
        eventBallRight = getIntent().getIntExtra("eventBallRight", 99);

        // Use this to load information from intents to the UI components based off the tab selection in
        // StatisticsActivity
        loadUIComponents(tabSelection);

        // Use this to adjust the heat map accordingly based on the values loaded from StatisticsActivity
        adjustHeatMap(tabSelection);
    }

    // Use this to load information from intents to the UI components based off the tab selection in
    // StatisticsActivity
    public void loadUIComponents(Boolean tabSelection) {
        if (tabSelection) {
            mReportsHeader.setText(eventName);
        } else {
            // It will be beneficial to still see what event was associated with the pitcher's statistics
            mReportsHeader.setText(pitcherName + " - " + eventName);
        }
        mReportsPitchCount.setText(Integer.toString(eventPitchCount));
        mReportsStrikeCount.setText(Integer.toString(eventStrikeCount));
        mReportsBallCount.setText(Integer.toString(eventBallCount));
        mReportsFastballCount.setText(Integer.toString(eventFastballCount));
        mReportsChangeupCount.setText(Integer.toString(eventChangeupCount));
        mReportsCurveballCount.setText(Integer.toString(eventCurveballCount));
        mReportsSliderCount.setText(Integer.toString(eventSliderCount));
        mReportsOtherCount.setText(Integer.toString(eventOtherCount));

        mReportsR1C1.setText(Integer.toString(eventR1C1));
        mReportsR1C2.setText(Integer.toString(eventR1C2));
        mReportsR1C3.setText(Integer.toString(eventR1C3));
        mReportsR2C1.setText(Integer.toString(eventR2C1));
        mReportsR2C2.setText(Integer.toString(eventR2C2));
        mReportsR2C3.setText(Integer.toString(eventR2C3));
        mReportsR3C1.setText(Integer.toString(eventR3C1));
        mReportsR3C2.setText(Integer.toString(eventR3C2));
        mReportsR3C3.setText(Integer.toString(eventR3C3));
        mReportsBallLow.setText(Integer.toString(eventBallLow));
        mReportsBallHigh.setText(Integer.toString(eventBallHigh));
        mReportsBallLeft.setText(Integer.toString(eventBallLeft));
        mReportsBallRight.setText(Integer.toString(eventBallRight));
    }

    // TODO we'll want to see if we can add this to Utilities eventually since we're duplicating
    // methods from TaggingActivity. UI components usually don't like to play well when coming
    // from other Activities or else you'll get a lot of NullPointerExceptions.
    private void adjustHeatMap(Boolean tabSelection) {
        // TODO refactor this. Will throw error when dividing by 0.
        // Need to investigate a better way of processing this.
        if (eventPitchCount > 0) {
            if (eventR1C1 > 0)
                mR1C1.getBackground().setAlpha(eventR1C1 * 255 / eventPitchCount);
            else mR1C1.getBackground().setAlpha(0);
            if (eventR1C2 > 0)
                mR1C2.getBackground().setAlpha(eventR1C2 * 255 / eventPitchCount);
            else mR1C2.getBackground().setAlpha(0);
            if (eventR1C3 > 0)
                mR1C3.getBackground().setAlpha(eventR1C3 * 255 / eventPitchCount);
            else mR1C3.getBackground().setAlpha(0);
            if (eventR2C1 > 0)
                mR2C1.getBackground().setAlpha(eventR2C1 * 255 / eventPitchCount);
            else mR2C1.getBackground().setAlpha(0);
            if (eventR2C2 > 0)
                mR2C2.getBackground().setAlpha(eventR2C2 * 255 / eventPitchCount);
            else mR2C2.getBackground().setAlpha(0);
            if (eventR2C3 > 0)
                mR2C3.getBackground().setAlpha(eventR2C3 * 255 / eventPitchCount);
            else mR2C3.getBackground().setAlpha(0);
            if (eventR3C1 > 0)
                mR3C1.getBackground().setAlpha(eventR3C1 * 255 / eventPitchCount);
            else mR3C1.getBackground().setAlpha(0);
            if (eventR3C2 > 0)
                mR3C2.getBackground().setAlpha(eventR3C2 * 255 / eventPitchCount);
            else mR3C2.getBackground().setAlpha(0);
            if (eventR3C3 > 0)
                mR3C3.getBackground().setAlpha(eventR3C3 * 255 / eventPitchCount);
            else mR3C3.getBackground().setAlpha(0);
            if (eventBallLow > 0)
                mBallLow.getBackground().setAlpha(eventBallLow * 255 / eventPitchCount);
            else mBallLow.getBackground().setAlpha(0);
            if (eventBallHigh > 0)
                mBallHigh.getBackground().setAlpha(eventBallHigh * 255 / eventPitchCount);
            else mBallHigh.getBackground().setAlpha(0);
            if (eventBallLeft > 0)
                mBallLeft.getBackground().setAlpha(eventBallLeft * 255 / eventPitchCount);
            else mBallLeft.getBackground().setAlpha(0);
            if (eventBallRight > 0)
                mBallRight.getBackground().setAlpha(eventBallRight * 255 / eventPitchCount);
            else mBallRight.getBackground().setAlpha(0);
        } else {
            // If the pitch count is zero, just display a blank heat map
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
        }
    }
}
