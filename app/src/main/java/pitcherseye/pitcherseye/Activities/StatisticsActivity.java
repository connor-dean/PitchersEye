package pitcherseye.pitcherseye.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import pitcherseye.pitcherseye.R;

//Need to do layout constraints for everything
public class StatisticsActivity extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private ListView dataListView;
    private Boolean searchMode = false;
    private Boolean itemSelected = false;
    private int selectedPosition = 0;
    private EditText itemText;
    private Button findButton;
    private Button deleteButton;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Instance of the database reference configured with a path of "/todo"
    private DatabaseReference dbRef = database.getReference("todo");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        dataListView = (ListView) findViewById(R.id.statistics_list_view);
        itemText = (EditText) findViewById(R.id.itemText);
        findButton = (Button) findViewById(R.id.findButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setEnabled(false);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,listItems);
        dataListView.setAdapter(adapter);
        dataListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                selectedPosition = position;
                itemSelected = true;
                deleteButton.setEnabled(true);
            }
        });
        addChildEventListener();
    }

    public void addChildEventListener(){
        ChildEventListener childListener = new ChildEventListener(){
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s){
                adapter.add((String) dataSnapshot.child("eventStats").getValue());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s){

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1){
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        };
        dbRef.addChildEventListener(childListener);
    }
    //onChildAdded() will be called when a new child is added to the data list and will be passed
    // a datasnapshot object containing the key and value of the new child
    //onChildRemoved() will be called when a child is removed form the list.

    public void deleteItem(View view){
        dataListView.setItemChecked(selectedPosition, false);
        dbRef.child(listKeys.get(selectedPosition)).removeValue();
    }
    //Deselects current selected item in ListView nad uses the selectedPosition variable as an index
    //into the listKeys array. Having identified the key associated with the item to be deleted,
    //that key is used to remove hte child node from the database

    public void findItems(View view){
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();

                adapter.clear();
                listKeys.clear();

                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();

                    String match = (String) next.child("eventStats").getValue();
                    String key = next.getKey();
                    listKeys.add(key);
                    adapter.add(match);
                }
            }
            //onDataChange() will be called when the query returns and will be passed a data snapshot
            // containing the items that match the search criteria. Method clears both the adapter
            // and listKeys arrays and then iterates through the children in the snapshot. For each
            // child, the key is stored on the listKeys array and teh value added to the adapter so
            // that it will appear in the ListView

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Query query;

        if(!searchMode){
            findButton.setText("Clear");
            query = dbRef.orderByChild("description").equalTo(itemText.getText().toString());
            searchMode = true;
        } else {
            searchMode = false;
            findButton.setText("Find");
            query = dbRef.orderByKey();
        }
        if(itemSelected){
            dataListView.setItemChecked(selectedPosition, false);
            itemSelected = false;
            deleteButton.setEnabled(false);
        }
        query.addListenerForSingleValueEvent(queryValueListener);
    }

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, StatisticsActivity.class);
        return i;
    }
}
