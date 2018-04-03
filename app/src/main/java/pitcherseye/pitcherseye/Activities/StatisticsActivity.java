package pitcherseye.pitcherseye.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pitcherseye.pitcherseye.Objects.EventStats;
import pitcherseye.pitcherseye.Objects.RecyclerViewAdapter;
import pitcherseye.pitcherseye.R;

//https://www.androidhive.info/2016/01/android-working-with-recycler-view/
//https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
//https://firebaseui.com/docs/android/index.html?com/firebase/ui/FirebaseRecyclerViewAdapter.html\
//https://www.coderefer.com/firebaseui-android-firebase-database/
//https://www.coderefer.com/android-recyclerview-cardview-tutorial/
//Need to do layout constraints for everything
public class StatisticsActivity extends AppCompatActivity {
    //DatabaseReference databaseReference; // = FirebaseDatabase.getInstance().getReference().child("eventStats");
    private RecyclerView recyclerView;
    private DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myref = FirebaseDatabase.getInstance().getReference("https://pitcherseye-3cdd0.firebaseapp.com/");
        FirebaseRecyclerViewAdapter<EventStats, EventStatsViewHolder> adapter;
        adapter = new FirebaseRecyclerViewAdapter<EventStats, EventStatsViewHolder>(EventStats.class, R.layout.individual_row, EventStatsViewHolder.class, myref){
            @Override
            public void populateViewHolder(EvenStatsViewHolder eventStatsViewHolder, EventStats eventStats){

                        eventStatsViewHolder.nameText.setText(eventStats.getEventName());
                        eventStatsViewHolder.messageText.setText(eventStats.getEventID());
            }
        };
        /*FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder>
            recyclerAdapter = new FirebaseRecyclerAdapter<EventStats, EventStatsViewHolder>(EventStats.class, R.layout.individual_row,EventStatsViewHolder.class, myref){
                    @Override
                    protected void populateViewHolder(EventStatsViewHolder viewHolder, EventStats model,int position){
                    viewHolder.setTitle(model.getEventName());
                    viewHolder.setDescription(model.getEventID());

            }
        };*/
        //recyclerView.setAdapter(adapter);

    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }
    public static class EventStatsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView textView_title;
        TextView textView_description;
        TextView textView_otherdescription;
        ImageView imageView;
        public EventStatsViewHolder(View itemView){
            super(itemView);

            mView = itemView;
            textView_title = (TextView)itemView.findViewById(R.id.title);
            textView_description = (TextView)itemView.findViewById(R.id.description);
            textView_otherdescription = (TextView)itemView.findViewById(R.id.otherdescription);
            imageView = (ImageView)itemView.findViewById(R.id.image);
        }

        public void setTitle(String title){
            textView_title.setText(title+"");
        }
        public void setDescription(String description){
            textView_description.setText(description);
        }
    }
}
