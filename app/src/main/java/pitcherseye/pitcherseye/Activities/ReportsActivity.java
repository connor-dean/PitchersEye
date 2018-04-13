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
    String eventName;
    int index;
    int eventPitchCount;

    final ArrayList<String> eventNameListReports = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        // Load values into variables
        index = getIntent().getIntExtra("index", 99);
        eventName = getIntent().getStringExtra("eventName");
        eventPitchCount = getIntent().getIntExtra("totalPitchCount", 99);

        // Instantiate widgets
        mReportHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        mReportPitchCount = (TextView) findViewById(R.id.txt_reports_event_pitch_count);

        mReportHeader.setText(eventName);
        mReportPitchCount.setText(Integer.toString(eventPitchCount));
    }
}
