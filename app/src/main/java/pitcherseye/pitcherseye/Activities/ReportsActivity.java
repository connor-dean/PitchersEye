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

    TextView mReportHeader;
    TextView mReportPitchCount;
    TextView mReportStrikeCount;

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
        mReportHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);
        mReportStrikeCount = (TextView) findViewById(R.id.txt_reports_event_strikes_count);

        mReportHeader.setText(eventName);
        mReportPitchCount.setText(Integer.toString(eventPitchCount));
        mReportStrikeCount.setText(Integer.toString(eventStrikeCount));
    }
}
