package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.R;

//https://www.androidhive.info/2016/01/android-working-with-recycler-view/
//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
//https://firebaseui.com/docs/android/index.html?com/firebase/ui/FirebaseRecyclerViewAdapter.html\
//https://www.coderefer.com/firebaseui-android-firebase-database/
//https://www.coderefer.com/android-recyclerview-cardview-tutorial/
//Need to do layout constraints for everything
public class StatisticsActivity extends AppCompatActivity {
    private static RecyclerView mEventRecyclerView;
    private DatabaseReference mRef;
    private static Context mContext;
    private static String eventName;
    public static ArrayList<String> eventNameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mEventRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_eventstats);
        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRef = FirebaseDatabase.getInstance().getReference("/eventStats");

        final StatisticsActivity statisticsActivity = new StatisticsActivity();

        FirebaseRecyclerAdapter<EventStats,EventStatsViewHolder> recyclerAdapter = new FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder>(
                EventStats.class,
                R.layout.individual_row,
                EventStatsViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(EventStatsViewHolder viewHolder, EventStats model, int position) {
                viewHolder.setEventName(model.getEventName());

                loadIndexArray(model.getEventName(), position);

                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventType(model.getGame());
                viewHolder.setEventLocation(model.getHome());
            }

            public void loadIndexArray(String eventName, int position) {
                eventNameList.add(eventName);
                Log.e("Load array", eventName + " position: " + position + "");

                ReportsActivity reportsActivity = new ReportsActivity();
                reportsActivity.loadIndexArray(eventName, position);
            }
        };
        mEventRecyclerView.setAdapter(recyclerAdapter);
    }

    public static class EventStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        TextView mStatisticsEventName;
        TextView mStatisticsDate;
        TextView mStatisticsEventType;
        TextView mStatisticsEventLocation;
        ImageButton mStatisticsViewEvent;

        StatisticsActivity statisticsActivity = new StatisticsActivity();

        public EventStatsViewHolder(final View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mView = itemView;
            mStatisticsEventName = (TextView) itemView.findViewById(R.id.txt_event_stats_name);
            mStatisticsDate = (TextView) itemView.findViewById(R.id.txt_event_stats_date);
            mStatisticsEventType = (TextView) itemView.findViewById(R.id.txt_stats_event_type);
            mStatisticsEventLocation = (TextView) itemView.findViewById(R.id.txt_stats_event_location);
            mStatisticsViewEvent = (ImageButton) itemView.findViewById(R.id.img_button_view_event);
            mStatisticsViewEvent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // This works and will direct you to MainActivity

            // Get adapter position displays the index
            Toast.makeText(view.getContext(), getAdapterPosition() + "", Toast.LENGTH_SHORT).show();

            setEventName(eventName);

            final Intent intent;
            intent = new Intent(view.getContext(), ReportsActivity.class);
            mContext.startActivity(intent);
        }

        public void setEventName(String eventName)
        {
            // This is where the content of the TextViews will be loaded
            mStatisticsEventName.setText(eventName + "");
            StatisticsActivity.eventName = eventName;
            statisticsActivity.setEventName(eventName);
        }

        public void setEventDate(String eventDate) {
            mStatisticsDate.setText(eventDate + "");
        }
        public void setEventType(Boolean eventType) {
            // TODO
            // Need to fix the null error once we sanitize Firebase
            if (eventType == null) {
                mStatisticsEventType.setText("Null");
            } else if (eventType){
                mStatisticsEventType.setText("Game");
            } else {
                mStatisticsEventType.setText("Practice");
            }
        }
        public void setEventLocation(Boolean eventLocation) {
            // TODO
            // Need to fix the null error once we sanitize Firebase
            if (eventLocation == null) {
                mStatisticsEventLocation.setText("Null");
            } else if (eventLocation){
                mStatisticsEventLocation.setText("Home");
            } else {
                mStatisticsEventLocation.setText("Away");
            }
        }
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    /*public void loadIndexArray(String eventName, int position) {
        eventNameList.add(eventName);
        Log.e("Load array", eventName + " position: " + position + "");

        ReportsActivity reportsActivity = new ReportsActivity();
        reportsActivity.loadIndexArray(eventName, position);
    }*/



    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }

}
