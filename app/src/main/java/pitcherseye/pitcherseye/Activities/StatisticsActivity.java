package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.R;

//https://www.androidhive.info/2016/01/android-working-with-recycler-view/
//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
//https://firebaseui.com/docs/android/index.html?com/firebase/ui/FirebaseRecyclerViewAdapter.html\
//https://www.coderefer.com/firebaseui-android-firebase-database/
//https://www.coderefer.com/android-recyclerview-cardview-tutorial/
//Need to do layout constraints for everything
public class StatisticsActivity extends AppCompatActivity {
    private RecyclerView mEventRecyclerView;
    private DatabaseReference mRef;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        mEventRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_eventstats);
        mEventRecyclerView.setHasFixedSize(true);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRef = FirebaseDatabase.getInstance().getReference("/eventStats");

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
            }
        };
        mEventRecyclerView.setAdapter(recyclerAdapter);
    }

    public static class EventStatsViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView mStatisticsEventName;
        TextView mStatisticsDate;
        TextView mStatisticsEventType;
        TextView mStatisticsEventLocation;

        public EventStatsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mStatisticsEventName = (TextView) itemView.findViewById(R.id.txt_event_stats_name);
            mStatisticsDate = (TextView) itemView.findViewById(R.id.txt_event_stats_date);
            mStatisticsEventType = (TextView) itemView.findViewById(R.id.txt_stats_event_type);
            mStatisticsEventLocation = (TextView) itemView.findViewById(R.id.txt_stats_event_location);
        }
        public void setEventName(String eventName)
        {
            // This is where the content of the TextViews will be loaded
            mStatisticsEventName.setText(eventName + "");
        }
        public void setEventDate(String eventDate) {
            mStatisticsDate.setText(eventDate + "");
        }
        public void setEventType(Boolean eventType) {
            //if (eventType) {
                mStatisticsEventType.setText("Game" + "");
            //} else {
                //mStatisticsEventType.setText("Practice" + "");
            //}
        }
        public void setEventLocation(Boolean eventLocation) {
            //if (eventLocation) {
                mStatisticsEventLocation.setText("Home" + "");
            //} else {
            //    mStatisticsEventLocation.setText("Away" + "");
            //}
        }
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }

}
