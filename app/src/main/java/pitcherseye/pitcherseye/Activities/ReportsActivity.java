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
    int index;
    final ArrayList<String> eventNameListReports = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Bundle b = getIntent().getExtras();

        if (null != b) {
            ArrayList<String> eventNameListReports = b.getStringArrayList("eventNameList");
            Log.i("List", "Passed Array List :: " + eventNameListReports);
        }


        ArrayList<String> eventNamesListReports = (ArrayList<String>) getIntent().getSerializableExtra("eventName");

        final StatisticsActivity statisticsActivity = new StatisticsActivity();


        //List<String> eventNameListReports = new ArrayList<String>(statisticsActivity.getEventNameList());

       for (int i = 0; i < eventNameListReports.size(); i++) {
            Log.e("Indexing list", "List: " + eventNameListReports.get(i) + "");
        }

        mReportHeader = (TextView) findViewById(R.id.txt_reports_event_header);
        //mReportHeader.setText(statisticsActivity.getEventName());
        mReportHeader.setText(statisticsActivity.getEventName());
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getIndex() { return index; }

    public void loadIndexArray(String eventName, int position) {
        //eventNameListReports.add(eventName);
        Log.e("Load into Reports", eventName + " position: " + position + "");
    }
}
