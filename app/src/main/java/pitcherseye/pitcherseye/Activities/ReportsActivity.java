package pitcherseye.pitcherseye.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import pitcherseye.pitcherseye.R;

public class ReportsActivity extends AppCompatActivity {

    TextView mReportsHeader;
    TextView mReportsPitchCount;
    TextView mReportsStrikeCount;
    TextView mReportsBallCount;
    TextView mReportsFastballCount;
    TextView mReportsChangeupCount;
    TextView mReportsCurveballCount;
    TextView mReportsSliderCount;
    TextView mReportsOtherCount;
    TextView mReportsR1C1;
    TextView mReportsR1C2;
    TextView mReportsR1C3;
    TextView mReportsR2C1;
    TextView mReportsR2C2;
    TextView mReportsR2C3;
    TextView mReportsR3C1;
    TextView mReportsR3C2;
    TextView mReportsR3C3;
    TextView mReportsBallLow;
    TextView mReportsBallHigh;
    TextView mReportsBallLeft;
    TextView mReportsBallRight;

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

    Boolean tabSelection;

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

        // Load values into variables
        index = getIntent().getIntExtra("index", 99);
        tabSelection = getIntent().getBooleanExtra("tabSelection", true);

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

        // Instantiate widgets
        mReportsHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportsPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);
        mReportsStrikeCount = (TextView) findViewById(R.id.txt_reports_event_strikes_count);
        mReportsBallCount = (TextView) findViewById(R.id.txt_reports_event_balls_count);
        mReportsFastballCount = (TextView) findViewById(R.id.txt_reports_event_fastball_count);
        mReportsChangeupCount = (TextView) findViewById(R.id.txt_reports_event_changeup_count);
        mReportsCurveballCount = (TextView) findViewById(R.id.txt_reports_event_curveball_count);
        mReportsSliderCount = (TextView) findViewById(R.id.txt_reports_event_slider_count);
        mReportsOtherCount = (TextView) findViewById(R.id.txt_reports_event_other_count);

        mReportsR1C1 = (TextView) findViewById(R.id.txt_reports_event_r1c1_count);
        mReportsR1C2 = (TextView) findViewById(R.id.txt_reports_event_r1c2_count);
        mReportsR1C3 = (TextView) findViewById(R.id.txt_reports_event_r1c3_count);
        mReportsR2C1 = (TextView) findViewById(R.id.txt_reports_event_r2c1_count);
        mReportsR2C2 = (TextView) findViewById(R.id.txt_reports_event_r2c2_count);
        mReportsR2C3 = (TextView) findViewById(R.id.txt_reports_event_r2c3_count);
        mReportsR3C1 = (TextView) findViewById(R.id.txt_reports_event_r3c1_count);
        mReportsR3C2 = (TextView) findViewById(R.id.txt_reports_event_r3c2_count);
        mReportsR3C3 = (TextView) findViewById(R.id.txt_reports_event_r3c3_count);
        mReportsBallLow = (TextView) findViewById(R.id.txt_reports_event_ball_low_count);
        mReportsBallHigh = (TextView) findViewById(R.id.txt_reports_event_ball_high_count);
        mReportsBallLeft = (TextView) findViewById(R.id.txt_reports_event_ball_left_count);
        mReportsBallRight = (TextView) findViewById(R.id.txt_reports_event_ball_right_count);

        // TODO rename these and fix xml
        mR1C1 = (Button) findViewById(R.id.btnR1C1);
        mR1C2 = (Button) findViewById(R.id.btnR1C2);
        mR1C3 = (Button) findViewById(R.id.btnR1C3);
        mR2C1 = (Button) findViewById(R.id.btnR2C1);
        mR2C2 = (Button) findViewById(R.id.btnR2C2);
        mR2C3 = (Button) findViewById(R.id.btnR2C3);
        mR3C1 = (Button) findViewById(R.id.btnR3C1);
        mR3C2 = (Button) findViewById(R.id.btnR3C2);
        mR3C3 = (Button) findViewById(R.id.btnR3C3);
        mBallLow = (Button) findViewById(R.id.btn_ball_low);
        mBallHigh = (Button) findViewById(R.id.btn_ball_high);
        mBallRight = (Button) findViewById(R.id.btn_ball_right);
        mBallLeft = (Button) findViewById(R.id.btn_ball_left);


        loadComponents(tabSelection);
        adjustHeatMap(tabSelection);
    }

    // TODO we'll want to see if we can add this to Utilities eventually since we're duplicating
    // methods from TaggingActivity. UI components usually don't like to play well when coming
    // from other Activities.
    private void adjustHeatMap(Boolean tabSelection) {
        // TODO refactor this. Will throw error when dividing by 0.
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


    public void loadComponents(Boolean tabSelection) {
        if (tabSelection) {
            mReportsHeader.setText(eventName);
        } else {
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
}
