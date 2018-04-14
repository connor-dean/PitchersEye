package pitcherseye.pitcherseye.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import pitcherseye.pitcherseye.R;

public class ReportsActivity extends AppCompatActivity {

    TextView mReportsEventHeader;
    TextView mReportsEventPitchCount;
    TextView mReportsEventStrikeCount;
    TextView mReportsEventBallCount;
    TextView mReportsEventR1C1;
    TextView mReportsEventR1C2;
    TextView mReportsEventR1C3;
    TextView mReportsEventR2C1;
    TextView mReportsEventR2C2;
    TextView mReportsEventR2C3;
    TextView mReportsEventR3C1;
    TextView mReportsEventR3C2;
    TextView mReportsEventR3C3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Load values into variables
        index = getIntent().getIntExtra("index", 99);
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

        // Instantiate widgets
        mReportsEventHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportsEventPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);
        mReportsEventStrikeCount = (TextView) findViewById(R.id.txt_reports_event_strikes_count);
        mReportsEventBallCount = (TextView) findViewById(R.id.txt_reports_event_balls_count);
        mReportsEventR1C1 = (TextView) findViewById(R.id.txt_reports_event_r1c1_count);
        mReportsEventR1C2 = (TextView) findViewById(R.id.txt_reports_event_r1c2_count);
        mReportsEventR1C3 = (TextView) findViewById(R.id.txt_reports_event_r1c3_count);
        mReportsEventR2C1 = (TextView) findViewById(R.id.txt_reports_event_r2c1_count);
        mReportsEventR2C2 = (TextView) findViewById(R.id.txt_reports_event_r2c2_count);
        mReportsEventR2C3 = (TextView) findViewById(R.id.txt_reports_event_r2c3_count);
        mReportsEventR3C1 = (TextView) findViewById(R.id.txt_reports_event_r3c1_count);
        mReportsEventR3C2 = (TextView) findViewById(R.id.txt_reports_event_r3c2_count);
        mReportsEventR3C3 = (TextView) findViewById(R.id.txt_reports_event_r3c3_count);

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

        mReportsEventHeader.setText(eventName);
        mReportsEventPitchCount.setText(Integer.toString(eventPitchCount));
        mReportsEventStrikeCount.setText(Integer.toString(eventStrikeCount));
        mReportsEventBallCount.setText(Integer.toString(eventBallCount));
        mReportsEventR1C1.setText(Integer.toString(eventR1C1));
        mReportsEventR1C2.setText(Integer.toString(eventR1C2));
        mReportsEventR1C3.setText(Integer.toString(eventR1C3));
        mReportsEventR2C1.setText(Integer.toString(eventR2C1));
        mReportsEventR2C2.setText(Integer.toString(eventR2C2));
        mReportsEventR2C3.setText(Integer.toString(eventR2C3));
        mReportsEventR3C1.setText(Integer.toString(eventR3C1));
        mReportsEventR3C2.setText(Integer.toString(eventR3C2));
        mReportsEventR3C3.setText(Integer.toString(eventR3C3));

        adjustHeatMap();
    }

    // TODO we'll want to see if we can add this to Utilities eventually since we're duplicating
    // methods from TaggingActivity. UI components usually don't like to play well when coming
    // from other Activities.
    private void adjustHeatMap() {
        // TODO refactor this. Will throw error when dividing by 0.
        if (eventR1C1 > 0) mR1C1.getBackground().setAlpha(eventR1C1  * 255 / eventPitchCount);
            else mR1C1.getBackground().setAlpha(0);
        if (eventR1C2 > 0) mR1C2.getBackground().setAlpha(eventR1C2  * 255 / eventPitchCount);
            else mR1C2.getBackground().setAlpha(0);
        if (eventR1C3 > 0) mR1C3.getBackground().setAlpha(eventR1C3  * 255 / eventPitchCount);
            else mR1C3.getBackground().setAlpha(0);
        if (eventR2C1 > 0) mR2C1.getBackground().setAlpha(eventR2C1  * 255 / eventPitchCount);
            else mR2C1.getBackground().setAlpha(0);
        if (eventR2C2 > 0) mR2C2.getBackground().setAlpha(eventR2C2  * 255 / eventPitchCount);
            else mR2C2.getBackground().setAlpha(0);
        if (eventR2C3 > 0) mR2C3.getBackground().setAlpha(eventR2C3  * 255 / eventPitchCount);
            else mR2C3.getBackground().setAlpha(0);
        if (eventR3C1 > 0) mR3C1.getBackground().setAlpha(eventR3C1  * 255 / eventPitchCount);
            else mR3C1.getBackground().setAlpha(0);
        if (eventR3C2 > 0) mR3C2.getBackground().setAlpha(eventR3C2  * 255 / eventPitchCount);
            else mR3C2.getBackground().setAlpha(0);
        if (eventR3C3 > 0) mR3C3.getBackground().setAlpha(eventR3C3  * 255 / eventPitchCount);
            else mR3C3.getBackground().setAlpha(0);
        if (eventBallLow > 0) mBallLow.getBackground().setAlpha(eventBallLow * 255 / eventPitchCount);
            else mBallLow.getBackground().setAlpha(0);
        if (eventBallHigh > 0) mBallHigh.getBackground().setAlpha(eventBallHigh * 255 / eventPitchCount);
            else mBallHigh.getBackground().setAlpha(0);
        if (eventBallLeft > 0) mBallLeft.getBackground().setAlpha(eventBallLeft * 255 / eventPitchCount);
            else mBallLeft.getBackground().setAlpha(0);
        if (eventBallRight > 0) mBallRight.getBackground().setAlpha(eventBallRight * 255 / eventPitchCount);
            else mBallRight.getBackground().setAlpha(0);
    }
}
