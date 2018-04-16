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
    TextView mReportsR1C1;
    TextView mReportsR1C2;
    TextView mReportsR1C3;
    TextView mReportsR2C1;
    TextView mReportsR2C2;
    TextView mReportsR2C3;
    TextView mReportsR3C1;
    TextView mReportsR3C2;
    TextView mReportsR3C3;

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
    int index;
    int eventPitchCount;
    int eventStrikeCount;
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

    String pitcherName;
    String pitcherEventName;
    int pitcherPitchCount;
    int pitcherStrikeCount;
    int pitcherR1C1;
    int pitcherR1C2;
    int pitcherR1C3;
    int pitcherR2C1;
    int pitcherR2C2;
    int pitcherR2C3;
    int pitcherR3C1;
    int pitcherR3C2;
    int pitcherR3C3;
    int pitcherBallCount;
    int pitcherBallLow;
    int pitcherBallHigh;
    int pitcherBallLeft;
    int pitcherBallRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Load values into variables
        index = getIntent().getIntExtra("index", 99);
        tabSelection = getIntent().getBooleanExtra("tabSelection", true);


        eventName = getIntent().getStringExtra("eventName");
        eventPitchCount = getIntent().getIntExtra("totalPitchCount", 99);
        eventStrikeCount = getIntent().getIntExtra("eventStrikeCount", 99);
        eventBallCount = getIntent().getIntExtra("eventBallCount", 99);
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

        pitcherName = getIntent().getStringExtra("pitcherName");
        pitcherEventName = getIntent().getStringExtra("pitcherEventName");
        pitcherPitchCount = getIntent().getIntExtra("pitcherTotalPitchCount", 99);
        pitcherStrikeCount = getIntent().getIntExtra("pitcherStrikeCount", 99);
        pitcherBallCount = getIntent().getIntExtra("pitcherBallCount", 99);
        pitcherR1C1 = getIntent().getIntExtra("pitcherR1C1", 99);
        pitcherR1C2 = getIntent().getIntExtra("pitcherR1C2", 99);
        pitcherR1C3 = getIntent().getIntExtra("pitcherR1C3", 99);
        pitcherR2C1 = getIntent().getIntExtra("pitcherR2C1", 99);
        pitcherR2C2 = getIntent().getIntExtra("pitcherR2C2", 99);
        pitcherR2C3 = getIntent().getIntExtra("pitcherR2C3", 99);
        pitcherR3C1 = getIntent().getIntExtra("pitcherR3C1", 99);
        pitcherR3C2 = getIntent().getIntExtra("pitcherR3C2", 99);
        pitcherR3C3 = getIntent().getIntExtra("pitcherR3C3", 99);
        pitcherBallLow = getIntent().getIntExtra("pitcherBallLow", 99);
        pitcherBallHigh = getIntent().getIntExtra("pitcherBallHigh", 99);
        pitcherBallLeft = getIntent().getIntExtra("pitcherBallLeft", 99);
        pitcherBallRight = getIntent().getIntExtra("pitcherBallRight", 99);

        // Instantiate widgets
        mReportsHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportsPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);
        mReportsStrikeCount = (TextView) findViewById(R.id.txt_reports_event_strikes_count);
        mReportsBallCount = (TextView) findViewById(R.id.txt_reports_event_balls_count);
        mReportsR1C1 = (TextView) findViewById(R.id.txt_reports_event_r1c1_count);
        mReportsR1C2 = (TextView) findViewById(R.id.txt_reports_event_r1c2_count);
        mReportsR1C3 = (TextView) findViewById(R.id.txt_reports_event_r1c3_count);
        mReportsR2C1 = (TextView) findViewById(R.id.txt_reports_event_r2c1_count);
        mReportsR2C2 = (TextView) findViewById(R.id.txt_reports_event_r2c2_count);
        mReportsR2C3 = (TextView) findViewById(R.id.txt_reports_event_r2c3_count);
        mReportsR3C1 = (TextView) findViewById(R.id.txt_reports_event_r3c1_count);
        mReportsR3C2 = (TextView) findViewById(R.id.txt_reports_event_r3c2_count);
        mReportsR3C3 = (TextView) findViewById(R.id.txt_reports_event_r3c3_count);

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
        if (tabSelection) {
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
        } else {
            // TODO refactor this. Will throw error when dividing by 0.
            if (pitcherPitchCount > 0) {
                if (pitcherR1C1 > 0)
                    mR1C1.getBackground().setAlpha(pitcherR1C1 * 255 / pitcherPitchCount);
                else mR1C1.getBackground().setAlpha(0);
                if (pitcherR1C2 > 0)
                    mR1C2.getBackground().setAlpha(pitcherR1C2 * 255 / pitcherPitchCount);
                else mR1C2.getBackground().setAlpha(0);
                if (pitcherR1C3 > 0)
                    mR1C3.getBackground().setAlpha(pitcherR1C3 * 255 / pitcherPitchCount);
                else mR1C3.getBackground().setAlpha(0);
                if (pitcherR2C1 > 0)
                    mR2C1.getBackground().setAlpha(pitcherR2C1 * 255 / pitcherPitchCount);
                else mR2C1.getBackground().setAlpha(0);
                if (pitcherR2C2 > 0)
                    mR2C2.getBackground().setAlpha(pitcherR2C2 * 255 / pitcherPitchCount);
                else mR2C2.getBackground().setAlpha(0);
                if (pitcherR2C3 > 0)
                    mR2C3.getBackground().setAlpha(pitcherR2C3 * 255 / pitcherPitchCount);
                else mR2C3.getBackground().setAlpha(0);
                if (pitcherR3C1 > 0)
                    mR3C1.getBackground().setAlpha(pitcherR3C1 * 255 / pitcherPitchCount);
                else mR3C1.getBackground().setAlpha(0);
                if (pitcherR3C2 > 0)
                    mR3C2.getBackground().setAlpha(pitcherR3C2 * 255 / pitcherPitchCount);
                else mR3C2.getBackground().setAlpha(0);
                if (pitcherR3C3 > 0)
                    mR3C3.getBackground().setAlpha(pitcherR3C3 * 255 / pitcherPitchCount);
                else mR3C3.getBackground().setAlpha(0);
                if (pitcherBallLow > 0)
                    mBallLow.getBackground().setAlpha(pitcherBallLow * 255 / pitcherPitchCount);
                else mBallLow.getBackground().setAlpha(0);
                if (pitcherBallHigh > 0)
                    mBallHigh.getBackground().setAlpha(pitcherBallHigh * 255 / pitcherPitchCount);
                else mBallHigh.getBackground().setAlpha(0);
                if (pitcherBallLeft > 0)
                    mBallLeft.getBackground().setAlpha(pitcherBallLeft * 255 / pitcherPitchCount);
                else mBallLeft.getBackground().setAlpha(0);
                if (pitcherBallRight > 0)
                    mBallRight.getBackground().setAlpha(pitcherBallRight * 255 / pitcherPitchCount);
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
    }



    
    public void loadComponents(Boolean tabSelection) {
        if (tabSelection) {
            mReportsHeader.setText(eventName);
            mReportsPitchCount.setText(Integer.toString(eventPitchCount));
            mReportsStrikeCount.setText(Integer.toString(eventStrikeCount));
            mReportsBallCount.setText(Integer.toString(eventBallCount));
            mReportsR1C1.setText(Integer.toString(eventR1C1));
            mReportsR1C2.setText(Integer.toString(eventR1C2));
            mReportsR1C3.setText(Integer.toString(eventR1C3));
            mReportsR2C1.setText(Integer.toString(eventR2C1));
            mReportsR2C2.setText(Integer.toString(eventR2C2));
            mReportsR2C3.setText(Integer.toString(eventR2C3));
            mReportsR3C1.setText(Integer.toString(eventR3C1));
            mReportsR3C2.setText(Integer.toString(eventR3C2));
            mReportsR3C3.setText(Integer.toString(eventR3C3));
        } else {
            mReportsHeader.setText(pitcherName + " - " + pitcherEventName);
            mReportsPitchCount.setText(Integer.toString(pitcherPitchCount));
            mReportsStrikeCount.setText(Integer.toString(pitcherStrikeCount));
            mReportsBallCount.setText(Integer.toString(pitcherBallCount));
            mReportsR1C1.setText(Integer.toString(pitcherR1C1));
            mReportsR1C2.setText(Integer.toString(pitcherR1C2));
            mReportsR1C3.setText(Integer.toString(pitcherR1C3));
            mReportsR2C1.setText(Integer.toString(pitcherR2C1));
            mReportsR2C2.setText(Integer.toString(pitcherR2C2));
            mReportsR2C3.setText(Integer.toString(pitcherR2C3));
            mReportsR3C1.setText(Integer.toString(pitcherR3C1));
            mReportsR3C2.setText(Integer.toString(pitcherR3C2));
            mReportsR3C3.setText(Integer.toString(pitcherR3C3));
        }
    }
}
