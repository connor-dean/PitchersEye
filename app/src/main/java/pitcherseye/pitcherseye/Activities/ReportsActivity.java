package pitcherseye.pitcherseye.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pitcherseye.pitcherseye.R;

public class ReportsActivity extends AppCompatActivity {

    TextView mReportsEventHeader;
    TextView mReportsEventPitchCount;
    TextView mReportsEventStrikeCount;
    TextView mReportsEventR1C1;
    TextView mReportsEventR1C2;
    TextView mReportsEventR1C3;
    TextView mReportsEventR2C1;
    TextView mReportsEventR2C2;
    TextView mReportsEventR2C3;
    TextView mReportsEventR3C1;
    TextView mReportsEventR3C2;
    TextView mReportsEventR3C3;

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

    final ArrayList<String> eventNameListReports = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Load values into variables
        index = getIntent().getIntExtra("index", 99);
        eventName = getIntent().getStringExtra("eventName");
        eventPitchCount = getIntent().getIntExtra("totalPitchCount", 99);
        eventStrikeCount = getIntent().getIntExtra("eventStrikeCount", 99);
        eventR1C1 = getIntent().getIntExtra("eventR1C1", 99);
        eventR1C2 = getIntent().getIntExtra("eventR1C2", 99);
        eventR1C3 = getIntent().getIntExtra("eventR1C3", 99);
        eventR2C1 = getIntent().getIntExtra("eventR2C1", 99);
        eventR2C2 = getIntent().getIntExtra("eventR2C2", 99);
        eventR2C3 = getIntent().getIntExtra("eventR2C3", 99);
        eventR3C1 = getIntent().getIntExtra("eventR3C1", 99);
        eventR3C2 = getIntent().getIntExtra("eventR3C2", 99);
        eventR3C3 = getIntent().getIntExtra("eventR3C3", 99);

        // Instantiate widgets
        mReportsEventHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportsEventPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);
        mReportsEventStrikeCount = (TextView) findViewById(R.id.txt_reports_event_strikes_count);
        mReportsEventR1C1 = (TextView) findViewById(R.id.txt_reports_event_r1c1_count);
        mReportsEventR1C2 = (TextView) findViewById(R.id.txt_reports_event_r1c2_count);
        mReportsEventR1C3 = (TextView) findViewById(R.id.txt_reports_event_r1c3_count);
        mReportsEventR2C1 = (TextView) findViewById(R.id.txt_reports_event_r2c1_count);
        mReportsEventR2C2 = (TextView) findViewById(R.id.txt_reports_event_r2c2_count);
        mReportsEventR2C3 = (TextView) findViewById(R.id.txt_reports_event_r2c3_count);
        mReportsEventR3C1 = (TextView) findViewById(R.id.txt_reports_event_r3c1_count);
        mReportsEventR3C2 = (TextView) findViewById(R.id.txt_reports_event_r3c2_count);
        mReportsEventR3C3 = (TextView) findViewById(R.id.txt_reports_event_r3c3_count);

        mReportsEventHeader.setText(eventName);
        mReportsEventPitchCount.setText(Integer.toString(eventPitchCount));
        mReportsEventStrikeCount.setText(Integer.toString(eventStrikeCount));
        mReportsEventR1C1.setText(Integer.toString(eventR1C1));
        mReportsEventR1C2.setText(Integer.toString(eventR1C2));
        mReportsEventR1C3.setText(Integer.toString(eventR1C3));
        mReportsEventR2C1.setText(Integer.toString(eventR2C1));
        mReportsEventR2C2.setText(Integer.toString(eventR2C2));
        mReportsEventR2C3.setText(Integer.toString(eventR2C3));
        mReportsEventR3C1.setText(Integer.toString(eventR3C1));
        mReportsEventR3C2.setText(Integer.toString(eventR3C2));
        mReportsEventR3C3.setText(Integer.toString(eventR3C3));
    }
}
