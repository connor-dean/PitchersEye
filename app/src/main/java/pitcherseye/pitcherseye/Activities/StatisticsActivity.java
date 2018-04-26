/*
 This Activity does a lot of heavy lifting in interaction with Firebase. This allows the user to see
 previously entered statistics from both events and pitchers and allows them to select a certain item
 to see more information in the ReportsActivity.

 We do this by getting references to both the eventStats and pitcherStats children in Firebase and load
 them in to individual items in the individual_row_events.xml or individual_row_pitcher.xml CardViews.
 When the appropriate values are loaded we add them to our RecylerView in activity_statistics.xml.

 This could use some refactoring, but we load the RecyclerViews by using the Firebase child references
 and pass them through our EventStats and PitcherStats objects, add the individual items into the appropriate
 ArrayLists, and load those ArrayLists into the UI components based off the index that they were loaded in.

 When we select an item in the RecyclerView, we grab the index of the selection, reference the position
 of the ArrayLists and insert those values into intents to send to the ReportsActivity.
 */

package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
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

    // TODO see if there is a better data type for these. RecyclerViews are difficult to load into
    // when associated with a database.
    public static ArrayList<String> eventNameList = new ArrayList<>();
    public static ArrayList<Integer> eventPitchCount = new ArrayList<>();
    public static ArrayList<Integer> eventStrikeCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventFastballCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventChangeupCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventCurveballCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventSliderCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> eventOtherCountArrayList = new ArrayList<>();
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
    public static ArrayList<Integer> pitcherFastballCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherChangeupCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherCurveballCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherSliderCountArrayList = new ArrayList<>();
    public static ArrayList<Integer> pitcherOtherCountArrayList = new ArrayList<>();
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

    // Instantiated the FirebaseRecyclerAdapters and RecyclerView
    static FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder> eventRecyclerAdapter;
    static FirebaseRecyclerAdapter<PitcherStats, PitcherStatsViewHolder> pitcherRecyclerAdapter;
    private static RecyclerView mEventRecyclerView;

    // Create Context variable to use intents and toasts in our classes
    private static Context mContext;

    // Create reference to our database and it's respective children
    private DatabaseReference mEventRef;
    private DatabaseReference mPitcherRef;

    // Create a reference to our TabLayout, this will help to see what index is selected to use in ReportsActivity
    private TabLayout mStatisticsTabLayout;

    // We'll call this in other Activities to access this Activity
    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }

    // When we were deleting items in the RecyclerView, the indexes weren't updating when directing to the
    // ReportsActivity. This forces the RecyclerView adapters to reflect the changes when we enter the Activity.
    @Override
    public void onStart() {
        if (eventRecyclerAdapter != null && pitcherRecyclerAdapter != null) {
            eventRecyclerAdapter.notifyDataSetChanged();
            pitcherRecyclerAdapter.notifyDataSetChanged();
        }
        super.onStart();
    }

    // RecyclerViews seem lazy and don't adjust internally, so when we exit the Activity
    // completely clear them out to make sure they adjust
    @Override
    public void onDestroy() {
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
        eventFastballCountArrayList.clear();
        eventChangeupCountArrayList.clear();
        eventCurveballCountArrayList.clear();
        eventSliderCountArrayList.clear();
        eventOtherCountArrayList.clear();
        eventBallCountArrayList.clear();
        eventBallLowArrayList.clear();
        eventBallHighArrayList.clear();
        eventBallLeftArrayList.clear();
        eventBallRightArrayList.clear();

        pitcherNameList.clear();
        pitcherEventNameList.clear();
        pitcherPitchCount.clear();
        pitcherStrikeCountArrayList.clear();
        pitcherFastballCountArrayList.clear();
        pitcherChangeupCountArrayList.clear();
        pitcherCurveballCountArrayList.clear();
        pitcherSliderCountArrayList.clear();
        pitcherOtherCountArrayList.clear();
        pitcherR1C1ArrayList.clear();
        pitcherR1C2ArrayList.clear();
        pitcherR1C3ArrayList.clear();
        pitcherR2C1ArrayList.clear();
        pitcherR2C2ArrayList.clear();
        pitcherR2C3ArrayList.clear();
        pitcherR3C1ArrayList.clear();
        pitcherR3C2ArrayList.clear();
        pitcherR3C3ArrayList.clear();
        pitcherBallCountArrayList.clear();
        pitcherBallLowArrayList.clear();
        pitcherBallHighArrayList.clear();
        pitcherBallLeftArrayList.clear();
        pitcherBallRightArrayList.clear();

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mEventRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_eventstats);
        mEventRecyclerView.setHasFixedSize(true); // Avoid invalidating the whole layout when its adapter contents change.
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStatisticsTabLayout = (TabLayout) findViewById(R.id.tablayout_statistics);

        // Create references to Firebase children
        mEventRef = FirebaseDatabase.getInstance().getReference("eventStats");
        mPitcherRef = FirebaseDatabase.getInstance().getReference("pitcherStats");

        // See which tab is selected so we can notify the RecyclerViews and ReportsActivity
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
                Log.i("Tab unselected", tab.toString()); // For debugging
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i("Tab reselected", tab.toString()); // For debugging
            }
        });

        // Instantiate the Event RecylerView adapter, we'll use EventStats as our model and
        // EventStatsViewHolder to do our heavy lifting.
        eventRecyclerAdapter = new FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder>(
                EventStats.class,
                R.layout.individual_row_events,
                EventStatsViewHolder.class,
                mEventRef
        ) {
            @Override
            protected void populateViewHolder(EventStatsViewHolder viewHolder, EventStats model, int position) {
                // Get the values from our model and save them to their designated ArrayLists
                // on iteration. This ensures that all of the data loaded are all in the appropriate indexes
                // for when we load the information into ReportsActivity.
                viewHolder.setEventName(model.getEventName());
                viewHolder.setEventDate(model.getEventDate());
                viewHolder.setEventType(model.getGame());
                viewHolder.setEventLocation(model.getHome());
                viewHolder.loadIndexArray(model.getEventName(), model.getPitchCount(), position);
                viewHolder.loadPitchTypeArray(model.getEventFastballCount(), model.getEventChangeupCount(), model.getEventCurveballCount(),
                        model.getEventSliderCount(), model.getEventOtherCount(), position);
                viewHolder.loadStrikeLocationArray(model.getStrikeCount(), model.getEventR1C1Count(), model.getEventR1C2Count(),
                        model.getEventR1C3Count(), model.getEventR2C1Count(), model.getEventR2C2Count(),
                        model.getEventR2C3Count(), model.getEventR3C1Count(), model.getEventR3C2Count(),
                        model.getEventR3C3Count(), position);
                viewHolder.loadBallLocationArray(model.getEventBallCount(), model.getEventBallCountLow(), model.getEventBallCountHigh(),
                        model.getEventBallCountLeft(), model.getEventBallCountRight(), position);

                // Used for debugging in Logcat
                Log.i("Child key ", mEventRef.child(Integer.toString(position + 1)).getKey() + " " + model.getEventName()
                        + "\n\tTotal: " + model.getPitchCount() + "\n\tStrike: " + model.getStrikeCount() +
                        "\n\tBall: " + model.getEventBallCount());
            }
        };

        // Instantiate the Pitcher RecylerView adapter, we'll use PitcherStats as our model and
        // PitcherStatsViewHolder to do our heavy lifting.
        pitcherRecyclerAdapter = new FirebaseRecyclerAdapter<PitcherStats, PitcherStatsViewHolder>(
                PitcherStats.class,
                R.layout.individual_row_pitchers,
                PitcherStatsViewHolder.class,
                mPitcherRef
        ) {
            @Override
            protected void populateViewHolder(PitcherStatsViewHolder viewHolder, PitcherStats model, int position) {
                // Get the values from our model and save them to their designated ArrayLists
                // on iteration. This ensures that all of the data loaded are all in the appropriate indexes
                // for when we load the information into ReportsActivity.
                viewHolder.setPitcherName(model.getPitcherName());
                viewHolder.setPitcherEventName(model.getEventName());
                viewHolder.setPitcherDate(model.getEventDate());
                viewHolder.setPitcherEventType(model.getGame());
                viewHolder.setPitcherEventLocation(model.getHome());
                viewHolder.loadPitcherIndexArray(model.getPitcherName(), model.getEventName(),
                        model.getPitchCount(), position);
                viewHolder.loadPitcherPitchTypeArray(model.getPitcherFastballCount(), model.getPitcherChangeupCount(), model.getPitcherCurveballCount(),
                        model.getPitcherSliderCount(), model.getPitcherOtherCount(), position);
                viewHolder.loadPitcherStrikeLocationArray(model.getStrikeCount(), model.getPitcherR1C1Count(), model.getPitcherR1C2Count(),
                        model.getPitcherR1C3Count(), model.getPitcherR2C1Count(), model.getPitcherR2C2Count(),
                        model.getPitcherR2C3Count(), model.getPitcherR3C1Count(), model.getPitcherR3C2Count(),
                        model.getPitcherR3C3Count(), position);
                viewHolder.loadPitcherBallLocationArray(model.getPitcherBallCount(), model.getPitcherBallCountLow(), model.getPitcherBallCountHigh(),
                        model.getPitcherBallCountLeft(), model.getPitcherBallCountRight(), position);

                // Used for debugging in Logcat
                Log.i("Child key ", mEventRef.child(Integer.toString(position + 1)).getKey() + " " + model.getEventName()
                        + "\n\tTotal: " + model.getPitchCount() + "\n\tStrike: " + model.getStrikeCount() +
                        "\n\tBall: " + model.getPitcherBallCount());
            }
        };

        // Set the RecyclerView with the eventRecyclerAdapter on startup for the Activity
        mEventRecyclerView.setAdapter(eventRecyclerAdapter);
    }

    // Where we'll have the methods to load information into our RecyclerView
    public static class EventStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        TextView mStatisticsEventName;
        TextView mStatisticsDate;
        TextView mStatisticsEventType;
        TextView mStatisticsEventLocation;
        ImageButton mDeleteRecylerStatistic;
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
            mDeleteRecylerStatistic = (ImageButton) itemView.findViewById(R.id.img_button_event_delete);

            mDeleteRecylerStatistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatisticsActivity statisticsActivity = new StatisticsActivity();
                    statisticsActivity.deleteRecyclerAlertDialog("\nDo you want to permanently delete this event's statistics?",
                            "Delete Event Stats",
                            eventRecyclerAdapter, getLayoutPosition(), mEventRecyclerView, true);
                }
            });

            mStatisticsViewEvent.setOnClickListener(this);
        }

        // This adds an onClick listener to the mStatisticsPitcherViewEvent. When selected, we save
        // the information with the appropriate RecyclerView item, save them intents to access in ReportsActivity
        // and redirects us to the ReportsActivity.
        // TODO see if we can refactor this after changing the ArrayLists
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
            intent.putExtra("eventFastballCount", eventFastballCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventChangeupCount", eventChangeupCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventCurveballCount", eventCurveballCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventSliderCount", eventSliderCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventOtherCount", eventOtherCountArrayList.get(getAdapterPosition()));
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

        // TODO see if we can refactor these in the future, if we have them in the same class we
        // get NullPointerExceptions since we're using UI components
        // These methods will load the information we're pulling from our models and set them in
        // our adapter.
        public void setEventName(String eventName) {
            mStatisticsEventName.setText(eventName + "");
        }

        public void setEventDate(String eventDate) {
            mStatisticsDate.setText(eventDate + "");
        }

        public void setEventType(Boolean eventType) {
            // Need to fix the null error once we sanitize Firebase
            if (eventType == null) {
                mStatisticsEventType.setText(R.string.string_reports_location_type_placeholder);
            } else if (eventType) {
                mStatisticsEventType.setText(R.string.string_reports_type_game);
            } else {
                mStatisticsEventType.setText(R.string.string_reports_type_practice);
            }
        }

        public void setEventLocation(Boolean eventLocation) {
            // TODO
            // Need to fix the null error once we sanitize Firebase
            if (eventLocation == null) {
                mStatisticsEventLocation.setText(R.string.string_reports_location_type_placeholder);
            } else if (eventLocation) {
                mStatisticsEventLocation.setText(R.string.string_reports_location_home);
            } else {
                mStatisticsEventLocation.setText(R.string.string_reports_location_away);
            }
        }

        public void loadIndexArray(String eventName, int totalPitchCount, int position) {
            eventNameList.add(eventName);
            Log.i("eventName", eventName);
            eventPitchCount.add(totalPitchCount);
            Log.i("Loaded pitch # array", eventNameList.get(position) + " position " + position);
        }

        public void loadPitchTypeArray(int eventFastballCount, int eventChangeupCount, int eventCurveballCount,
                                       int eventSliderCount, int eventOtherCount, int position) {
            eventFastballCountArrayList.add(eventFastballCount);
            eventChangeupCountArrayList.add(eventChangeupCount);
            eventCurveballCountArrayList.add(eventCurveballCount);
            eventSliderCountArrayList.add(eventSliderCount);
            eventOtherCountArrayList.add(eventOtherCount);
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

    // Where we'll have the methods to load information into our RecyclerView
    public static class PitcherStatsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mPitcherView;
        TextView mStatisticsPitcherName;
        TextView mStatisticsPitcherEventName;
        TextView mStatisticsPitcherDate;
        TextView mStatisticsPitcherEventType;
        TextView mStatisticsPitcherEventLocation;
        ImageButton mDeleteRecylerStatistic;
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
            mDeleteRecylerStatistic = (ImageButton) itemView.findViewById(R.id.img_button_pitcher_delete);

            mDeleteRecylerStatistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StatisticsActivity statisticsActivity = new StatisticsActivity();
                    statisticsActivity.deleteRecyclerAlertDialog("\nDo you want to permanently delete this pitcher's statistics?",
                            "Delete Pitcher Stats",
                            pitcherRecyclerAdapter, getLayoutPosition(), mEventRecyclerView, false);
                }
            });

            mStatisticsPitcherViewEvent.setOnClickListener(this);
        }

        // This adds an onClick listener to the mStatisticsPitcherViewEvent. When selected, we save
        // the information with the appropriate RecyclerView item, save them intents to access in ReportsActivity
        // and redirects us to the ReportsActivity.
        // TODO see if we can refactor this after changing the ArrayLists
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
            intent.putExtra("eventStrikeCount", pitcherStrikeCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventFastballCount", pitcherFastballCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventChangeupCount", pitcherChangeupCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventCurveballCount", pitcherCurveballCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventSliderCount", pitcherSliderCountArrayList.get(getAdapterPosition()));
            intent.putExtra("eventOtherCount", pitcherOtherCountArrayList.get(getAdapterPosition()));
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

        // TODO see if we can refactor these in the future, if we have them in the same class we
        // get NullPointerExceptions since we're using UI components
        // These methods will load the information we're pulling from our models and set them in
        // our adapter.
        public void setPitcherName(String pitcherName) {
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
                mStatisticsPitcherEventType.setText(R.string.string_reports_location_type_placeholder);
            } else if (pitcherEventType) {
                mStatisticsPitcherEventType.setText(R.string.string_reports_type_game);
            } else {
                mStatisticsPitcherEventType.setText(R.string.string_reports_type_practice);
            }
        }

        public void setPitcherEventLocation(Boolean pitcherEventLocation) {
            // Need to fix the null error once we sanitize Firebase
            if (pitcherEventLocation == null) {
                mStatisticsPitcherEventLocation.setText(R.string.string_reports_location_type_placeholder);
            } else if (pitcherEventLocation) {
                mStatisticsPitcherEventLocation.setText(R.string.string_reports_location_home);
            } else {
                mStatisticsPitcherEventLocation.setText(R.string.string_reports_location_away);
            }
        }

        public void loadPitcherIndexArray(String pitcherName, String pitcherEventName, int totalPitchCount, int position) {
            pitcherNameList.add(pitcherName);
            pitcherEventNameList.add(pitcherEventName);
            pitcherPitchCount.add(totalPitchCount);
            Log.e("Loaded pitch # array", pitcherEventNameList.get(position) + " position " + position);
        }

        public void loadPitcherPitchTypeArray(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                                              int pitcherSliderCount, int pitcherOtherCount, int position) {
            pitcherFastballCountArrayList.add(pitcherFastballCount);
            pitcherChangeupCountArrayList.add(pitcherChangeupCount);
            pitcherCurveballCountArrayList.add(pitcherCurveballCount);
            pitcherSliderCountArrayList.add(pitcherSliderCount);
            pitcherOtherCountArrayList.add(pitcherOtherCount);
            Log.e("Loaded pitch fastball", pitcherFastballCountArrayList.get(position) + "");
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
    }

    // This is an AlertDialog confirming or canceling the deletion of an item in the RecylerView
    // Selecting cancel will just dismiss the dialog, but confirming with the delete button
    // will delete the passed RecyclerView's adapter. We could have used a custom made dialog,
    // but this fulfills what we're wanting to do and is more in line with Android conventions.
    private void deleteRecyclerAlertDialog(final String message, final String title, final FirebaseRecyclerAdapter recyclerAdapter, final int layoutPostition, final RecyclerView recyclerView, final Boolean isEventView) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(title).setMessage(message).setPositiveButton(
                "Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        recyclerAdapter.getRef(layoutPostition).removeValue();
                        recyclerAdapter.notifyDataSetChanged();
                        recyclerView.invalidate();
                        adjustArrayLists(layoutPostition, isEventView);
                    }
                }
        ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("Delete cancelled", title);
                dialogInterface.dismiss();
            }
        }).show();
    }

    // We use this to remove the deleted values from the deleted RecyclerView item.
    // TODO see if we can find a better data type to fulfill what we're doing
    public void adjustArrayLists(int position, Boolean isEventView) {
        if (isEventView) {
            eventNameList.remove(position);
            eventPitchCount.remove(position);
            eventStrikeCountArrayList.remove(position);
            eventFastballCountArrayList.remove(position);
            eventChangeupCountArrayList.remove(position);
            eventCurveballCountArrayList.remove(position);
            eventSliderCountArrayList.remove(position);
            eventOtherCountArrayList.remove(position);
            eventR1C1ArrayList.remove(position);
            eventR1C2ArrayList.remove(position);
            eventR1C3ArrayList.remove(position);
            eventR2C1ArrayList.remove(position);
            eventR2C2ArrayList.remove(position);
            eventR2C3ArrayList.remove(position);
            eventR3C1ArrayList.remove(position);
            eventR3C2ArrayList.remove(position);
            eventR3C3ArrayList.remove(position);
            eventBallCountArrayList.remove(position);
            eventBallLowArrayList.remove(position);
            eventBallHighArrayList.remove(position);
            eventBallLeftArrayList.remove(position);
            eventBallRightArrayList.remove(position);
        } else {
            pitcherNameList.remove(position);
            pitcherEventNameList.remove(position);
            pitcherPitchCount.remove(position);
            pitcherStrikeCountArrayList.remove(position);
            pitcherFastballCountArrayList.remove(position);
            pitcherChangeupCountArrayList.remove(position);
            pitcherCurveballCountArrayList.remove(position);
            pitcherSliderCountArrayList.remove(position);
            pitcherOtherCountArrayList.remove(position);
            pitcherR1C1ArrayList.remove(position);
            pitcherR1C2ArrayList.remove(position);
            pitcherR1C3ArrayList.remove(position);
            pitcherR2C1ArrayList.remove(position);
            pitcherR2C2ArrayList.remove(position);
            pitcherR2C3ArrayList.remove(position);
            pitcherR3C1ArrayList.remove(position);
            pitcherR3C2ArrayList.remove(position);
            pitcherR3C3ArrayList.remove(position);
            pitcherBallCountArrayList.remove(position);
            pitcherBallLowArrayList.remove(position);
            pitcherBallHighArrayList.remove(position);
            pitcherBallLeftArrayList.remove(position);
            pitcherBallRightArrayList.remove(position);
        }
    }
}