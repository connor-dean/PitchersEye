package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.Objects.PitcherStats;
import pitcherseye.pitcherseye.R;

public class StatisticsActivity extends AppCompatActivity {
    private static RecyclerView mEventRecyclerView;
    private DatabaseReference mEventRef;
    private DatabaseReference mPitcherRef;
    private static Context mContext;
    int tabPosition = 0;
    private TabLayout mStatisticsTabLayout;

    public static ArrayList<String> eventNameList = new ArrayList<>();
    public static ArrayList<Integer> eventPitchCount = new ArrayList<>();
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
    public static ArrayList<Integer> eventBallCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventBallLowArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventBallHighArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventBallLeftArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventBallRightArrayList = new ArrayList<>();

    public static ArrayList<String> pitcherNameList = new ArrayList<>();
    public static ArrayList<String> pitcherEventNameList = new ArrayList<>();
    public static ArrayList<Integer> pitcherPitchCount = new ArrayList<>();
    public static ArrayList<Integer> pitcherStrikeCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR1C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR1C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR1C3ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR2C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR2C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR2C3ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR3C1ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR3C2ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherR3C3ArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherBallCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherBallLowArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherBallHighArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherBallLeftArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherBallRightArrayList = new ArrayList<>();
    FirebaseRecyclerAdapter<EventStats,EventStatsViewHolder> eventRecyclerAdapter;
    FirebaseRecyclerAdapter<PitcherStats, PitcherStatsViewHolder> pitcherRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mEventRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_eventstats);
        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStatisticsTabLayout = (TabLayout) findViewById(R.id.tablayout_statistics);
        mEventRef = FirebaseDatabase.getInstance().getReference("/eventStats");
        mPitcherRef = FirebaseDatabase.getInstance().getReference("/pitcherStats");

        // Set the adapter on startup
        //mEventRecyclerView.setAdapter(eventRecyclerAdapter);

        // See which tab is selected
        mStatisticsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mEventRecyclerView.setAdapter(eventRecyclerAdapter);
                } else {
                    mEventRecyclerView.setAdapter(pitcherRecyclerAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        eventRecyclerAdapter = new FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder>(
                EventStats.class,
                R.layout.individual_row_events,
                EventStatsViewHolder.class,
                mEventRef
        ) {
            @Override
            protected void populateViewHolder(EventStatsViewHolder viewHolder, EventStats model, int position) {
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventType(model.getGame());
                viewHolder.setEventLocation(model.getHome());
                viewHolder.loadIndexArray(model.getEventName(), model.getPitchCount(), position);
                viewHolder.loadStrikeLocationArray(model.getStrikeCount(), model.getEventR1C1Count(), model.getEventR1C2Count(),
                        model.getEventR1C3Count(), model.getEventR2C1Count(), model.getEventR2C2Count(),
                        model.getEventR2C3Count(), model.getEventR3C1Count(), model.getEventR3C2Count(),
                        model.getEventR3C3Count(), position);
                viewHolder.loadBallLocationArray(model.getEventBallCount(), model.getEventBallCountLow(), model.getEventBallCountHigh(),
                        model.getEventBallCountLeft(), model.getEventBallCountRight(), position);
            }
        };

        pitcherRecyclerAdapter = new FirebaseRecyclerAdapter<PitcherStats, PitcherStatsViewHolder>(
                PitcherStats.class,
                R.layout.individual_row_pitchers,
                PitcherStatsViewHolder.class,
                mPitcherRef
        ) {
            @Override
            protected void populateViewHolder(PitcherStatsViewHolder viewHolder, PitcherStats model, int position) {
                viewHolder.setPitcherName(model.getPitcherName());
                viewHolder.setPitcherEventName(model.getEventName());
                viewHolder.setPitcherDate(model.getEventDate());
                viewHolder.setPitcherEventType(model.getGame());
                viewHolder.setPitcherEventLocation(model.getHome());
                viewHolder.loadPitcherIndexArray(model.getPitcherName(), model.getEventName(),
                        model.getPitchCount(), position);
                viewHolder.loadPitcherStrikeLocationArray(model.getStrikeCount(), model.getPitcherR1C1Count(), model.getPitcherR1C2Count(),
                        model.getPitcherR1C3Count(), model.getPitcherR2C1Count(), model.getPitcherR2C2Count(),
                        model.getPitcherR2C3Count(), model.getPitcherR3C1Count(), model.getPitcherR3C2Count(),
                        model.getPitcherR3C3Count(), position);
                viewHolder.loadPitcherBallLocationArray(model.getPitcherBallCount(), model.getPitcherBallCountLow(), model.getPitcherBallCountHigh(),
                        model.getPitcherBallCountLeft(), model.getPitcherBallCountRight(), position);
            }
        };

        // TODO this is where the adapter sets
        mEventRecyclerView.setAdapter(eventRecyclerAdapter);
    }

    public static class EventStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        TextView mStatisticsEventName;
        TextView mStatisticsDate;
        TextView mStatisticsEventType;
        TextView mStatisticsEventLocation;
        ImageButton mStatisticsViewEvent;

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
            intent.putExtra("tabSelection", true);
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
            intent.putExtra("eventBallCount", eventBallCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallLow", eventBallLowArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallHigh", eventBallHighArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallLeft", eventBallLeftArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallRight", eventBallRightArrayList.get(getAdapterPosition()));

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
            Log.e("R1C1: ", eventR1C1ArrayList.get(position) + "");
        }

        // Load Ball information
        public void loadBallLocationArray(int eventBallCount, int eventBallLow, int eventBallHigh,
                                          int eventBallLeft, int eventBallRight, int position) {
            eventBallCountArrayList.add(eventBallCount);
            eventBallLowArrayList.add(eventBallLow);
            eventBallHighArrayList.add(eventBallHigh);
            eventBallLeftArrayList.add(eventBallLeft);
            eventBallRightArrayList.add(eventBallRight);
            Log.e("Ball Count: ", eventBallCountArrayList.get(position) + "");
        }
    }

    public static class PitcherStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mPitcherView;
        TextView mStatisticsPitcherName;
        TextView mStatisticsPitcherEventName;
        TextView mStatisticsPitcherDate;
        TextView mStatisticsPitcherEventType;
        TextView mStatisticsPitcherEventLocation;
        ImageButton mStatisticsPitcherViewEvent;

        public PitcherStatsViewHolder(final View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mPitcherView = itemView;

            mStatisticsPitcherName = (TextView) itemView.findViewById(R.id.txt_pitcher_stats_name);
            mStatisticsPitcherEventName = (TextView) itemView.findViewById(R.id.txt_pitcher_event_name);
            mStatisticsPitcherDate = (TextView) itemView.findViewById(R.id.txt_pitcher_stats_date);
            mStatisticsPitcherEventType = (TextView) itemView.findViewById(R.id.txt_stats_pitcher_type);
            mStatisticsPitcherEventLocation = (TextView) itemView.findViewById(R.id.txt_stats_pitcher_location);
            mStatisticsPitcherViewEvent = (ImageButton) itemView.findViewById(R.id.img_button_view_pitcher);
            mStatisticsPitcherViewEvent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final Intent intent;
            intent = new Intent(view.getContext(), ReportsActivity.class);

            // Send values through the intent to ReportsActivity
            intent.putExtra("index", getAdapterPosition());
            intent.putExtra("tabSelection", false);
            intent.putExtra("pitcherName", pitcherNameList.get(getAdapterPosition()));
            intent.putExtra("eventName", pitcherEventNameList.get(getAdapterPosition()));
            intent.putExtra("totalPitchCount", pitcherPitchCount.get(getAdapterPosition()));
            intent.putExtra("eventStrikeCount", pitcherStrikeCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C1", pitcherR1C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C2", pitcherR1C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR1C3", pitcherR1C3ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C1", pitcherR2C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C2", pitcherR2C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR2C3", pitcherR2C3ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C1", pitcherR3C1ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C2", pitcherR3C2ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventR3C3", pitcherR3C3ArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallCount", pitcherBallCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallLow", pitcherBallLowArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallHigh", pitcherBallHighArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallLeft", pitcherBallLeftArrayList.get(getAdapterPosition()));
            intent.putExtra("eventBallRight", pitcherBallRightArrayList.get(getAdapterPosition()));

            mContext.startActivity(intent);
        }

        public void loadPitcherIndexArray(String pitcherName, String pitcherEventName, int totalPitchCount,  int position) {
            pitcherNameList.add(pitcherName);
            pitcherEventNameList.add(pitcherEventName);
            pitcherPitchCount.add(totalPitchCount);
            Log.e("Loaded pitch # array", pitcherEventNameList.get(position) + "");
        }

        // Load Strike information
        public void loadPitcherStrikeLocationArray(int pitcherStrikeCount, int pitcherR1C1Count, int pitcherR1C2Count, int pitcherR1C3Count,
                                            int pitcherR2C1Count, int pitcherR2C2Count, int pitcherR2C3Count,
                                            int pitcherR3C1Count, int pitcherR3C2Count, int pitcherR3C3Count,
                                            int position) {
            // Investigate a better model to store these
            pitcherStrikeCountArrayList.add(pitcherStrikeCount);
            pitcherR1C1ArrayList.add(pitcherR1C1Count);
            pitcherR1C2ArrayList.add(pitcherR1C2Count);
            pitcherR1C3ArrayList.add(pitcherR1C3Count);
            pitcherR2C1ArrayList.add(pitcherR2C1Count);
            pitcherR2C2ArrayList.add(pitcherR2C2Count);
            pitcherR2C3ArrayList.add(pitcherR2C3Count);
            pitcherR3C1ArrayList.add(pitcherR3C1Count);
            pitcherR3C2ArrayList.add(pitcherR3C2Count);
            pitcherR3C3ArrayList.add(pitcherR3C3Count);
            Log.e("Pitcher R1C1: ", pitcherR1C1ArrayList.get(position) + "");
        }

        // Load Ball information
        public void loadPitcherBallLocationArray(int pitcherBallCount, int pitcherBallLow, int pitcherBallHigh,
                                          int pitcherBallLeft, int pitcherBallRight, int position) {
            pitcherBallCountArrayList.add(pitcherBallCount);
            pitcherBallLowArrayList.add(pitcherBallLow);
            pitcherBallHighArrayList.add(pitcherBallHigh);
            pitcherBallLeftArrayList.add(pitcherBallLeft);
            pitcherBallRightArrayList.add(pitcherBallRight);
            Log.e("Pitcher Ball Count: ", pitcherBallCountArrayList.get(position) + "");
        }

        public void setPitcherName(String pitcherName)
        {
            mStatisticsPitcherName.setText(pitcherName);
        }

        public void setPitcherEventName(String pitcherEventName) {
            mStatisticsPitcherEventName.setText(pitcherEventName);
        }

        public void setPitcherDate(String pitcherDate) {
            mStatisticsPitcherDate.setText(pitcherDate);
        }

        public void setPitcherEventType(Boolean pitcherEventType) {
            // TODO
            // Need to fix the null error once we sanitize Firebase
            if (pitcherEventType == null) {
                mStatisticsPitcherEventType.setText("Null");
            } else if (pitcherEventType){
                mStatisticsPitcherEventType.setText("Game");
            } else {
                mStatisticsPitcherEventType.setText("Practice");
            }
        }

        public void setPitcherEventLocation(Boolean pitcherEventLocation) {
            // TODO
            // Need to fix the null error once we sanitize Firebase
            if (pitcherEventLocation == null) {
                mStatisticsPitcherEventLocation.setText("Null");
            } else if (pitcherEventLocation){
                mStatisticsPitcherEventLocation.setText("Home");
            } else {
                mStatisticsPitcherEventLocation.setText("Away");
            }
        }
    }

        // Clear out the ArrayLists to ensure that we're reloading them
    @Override
    public void onBackPressed() {
        eventNameList.clear();
        eventPitchCount.clear();
        eventStrikeCountArrayList.clear();
        eventR1C1ArrayList.clear();
        eventR1C2ArrayList.clear();
        eventR1C3ArrayList.clear();
        eventR2C1ArrayList.clear();
        eventR2C2ArrayList.clear();
        eventR2C3ArrayList.clear();
        eventR3C1ArrayList.clear();
        eventR3C2ArrayList.clear();
        eventR3C3ArrayList.clear();

        pitcherNameList.clear();
        pitcherEventNameList.clear();
        pitcherPitchCount.clear();
        pitcherStrikeCountArrayList.clear();
        pitcherR1C1ArrayList.clear();
        pitcherR1C2ArrayList.clear();
        pitcherR1C3ArrayList.clear();
        pitcherR2C1ArrayList.clear();
        pitcherR2C2ArrayList.clear();
        pitcherR2C3ArrayList.clear();
        pitcherR3C1ArrayList.clear();
        pitcherR3C2ArrayList.clear();
        pitcherR3C3ArrayList.clear();
        
        finish();
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }

}