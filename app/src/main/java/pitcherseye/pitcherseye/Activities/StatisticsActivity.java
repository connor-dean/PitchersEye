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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.R;

public class StatisticsActivity extends AppCompatActivity {
    private static RecyclerView mEventRecyclerView;
    private DatabaseReference mRef;
    private static Context mContext;
    private static String eventName;
    public static ArrayList<String> eventNameList = new ArrayList<>();
    public static ArrayList<Integer> eventPitchCount = new ArrayList<>();

    // Trying a multidimensional arraylist
    List<ArrayList<Integer>> strikeArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventStrikeCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR1C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR1C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR1C3ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR2C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR2C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR2C3ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR3C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR3C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventR3C3ArrayList = new ArrayList<>();

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
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventType(model.getGame());
                viewHolder.setEventLocation(model.getHome());

                loadIndexArray(model.getEventName(), model.getPitchCount(), position);
                loadStrikeLocationArray(model.getStrikeCount(), model.eventR1C1Count, model.eventR1C2Count, model.eventR1C3Count,
                                        model.eventR2C1Count, model.eventR2C2Count, model.eventR2C3Count,
                                        model.eventR3C1Count, model.eventR3C2Count, model.eventR3C3Count,
                                        position);
            }

            public void loadIndexArray(String eventName, int totalPitchCount,  int position) {
                eventNameList.add(eventName);
                eventPitchCount.add(totalPitchCount);
                Log.e("Loaded pitch # array", eventNameList.get(position) + "");
            }

            // Load Strike information
            public void loadStrikeLocationArray(int eventStrikeCount, int eventR1C1Count, int eventR1C2Count, int eventR1C3Count,
                                                int eventR2C1Count, int eventR2C2Count, int eventR2C3Count,
                                                int eventR3C1Count, int eventR3C2Count, int eventR3C3Count,
                                                int position) {
                // Investigate a better model to store these
                 eventStrikeCountArrayList.add(eventStrikeCount);
                 eventR1C1ArrayList.add(eventR1C1Count);
                 eventR1C2ArrayList.add(eventR1C2Count);
                 eventR1C3ArrayList.add(eventR1C3Count);
                 eventR2C1ArrayList.add(eventR2C1Count);
                 eventR2C2ArrayList.add(eventR2C2Count);
                 eventR2C3ArrayList.add(eventR2C3Count);
                 eventR3C1ArrayList.add(eventR3C1Count);
                 eventR3C2ArrayList.add(eventR3C2Count);
                 eventR3C3ArrayList.add(eventR3C3Count);
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
            final Intent intent;
            intent = new Intent(view.getContext(), ReportsActivity.class);

            // Send values through the intent to ReportsActivity
            intent.putExtra("index", getAdapterPosition());
            intent.putExtra("eventName", eventNameList.get(getAdapterPosition()));
            intent.putExtra("totalPitchCount", eventPitchCount.get(getAdapterPosition()));
            intent.putExtra("eventStrikeCount", eventStrikeCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C1", eventR1C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C2", eventR1C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C3", eventR1C3ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C1", eventR2C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C2", eventR2C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C3", eventR2C3ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C1", eventR3C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C2", eventR3C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C3", eventR3C3ArrayList.get(getAdapterPosition()));

            mContext.startActivity(intent);
        }

        public void setEventName(String eventName)
        {
            mStatisticsEventName.setText(eventName + "");
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

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }

}
