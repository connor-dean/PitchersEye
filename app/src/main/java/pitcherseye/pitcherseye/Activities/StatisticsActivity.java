package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import pitcherseye.pitcherseye.R;

//Need to do layout constraints for everything
public class StatisticsActivity extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ListView listView;
    Button findButton;
    Button deleteButton;
    EditText itemText;
    Boolean searchMode = false;
    Boolean itemSelected = false;
    int selectedPosition = 0;


    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("eventStats");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        listView = (ListView)findViewById(R.id.statistics_list_view);
        findButton = (Button)findViewById(R.id.findButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);
        itemText = (EditText)findViewById(R.id.itemText);

        itemText.setText("");
        deleteButton.setEnabled(false);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listItems);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add((String) dataSnapshot.getKey());
                listKeys.add(dataSnapshot.getKey());
                //adapter.add((String) dataSnapshot.child("eventStats").getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if(index != -1){
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = i;
                itemSelected = true;
                deleteButton.setEnabled(true);
            }
        });

    }

    public void findItems(View view){
        Query query;
        if(!searchMode){
            findButton.setText("Clear");
            query = dbref.orderByChild("eventStats").equalTo(itemText.getText().toString());
            searchMode = true;
        } else{
            searchMode = false;
            findButton.setText("Find");
            query = dbref.orderByKey();
        }
        if(itemSelected){
            listView.setItemChecked(selectedPosition, false);
            itemSelected = false;
            deleteButton.setEnabled(false);
        }
        //query.addListenerForSingleValueEvent(queryValueListener);
    }


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }
}
