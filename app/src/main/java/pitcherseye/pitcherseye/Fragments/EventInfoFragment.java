package pitcherseye.pitcherseye.Fragments;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pitcherseye.pitcherseye.Activities.TaggingActivity;
import pitcherseye.pitcherseye.R;

public class EventInfoFragment extends DialogFragment {

    Button mConfirmEvent;
    Button mConfirmPitcher;
    Button mConfirmChange;
    CheckBox mEventType;
    CheckBox mEventLocation;
    DatabaseReference mDatabase;
    EditText mEventName;
    Spinner mSpinnerPitchers;

    public interface OnInputListener {
        void sendInput(String eventName, Boolean isGame, Boolean isHome, String pitcherName);
    }

    public OnInputListener mOnInputListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        mConfirmEvent = (Button) view.findViewById(R.id.btn_event_confirm);
        mConfirmPitcher = (Button) view.findViewById(R.id.btn_event_pitcher);
        mConfirmChange = (Button) view.findViewById(R.id.btn_confirm_info);

        mEventType = (CheckBox) view.findViewById(R.id.chck_bx_event_type);
        mEventLocation = (CheckBox) view.findViewById(R.id.chck_bx_event_location);

        // Instantiate Firebase object
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEventName = (EditText) view.findViewById(R.id.edt_txt_event_name_entry);

        mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_pitcher_names);


        // Instantiate and load pitchers into spinner
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> pitchers = new ArrayList<String>();
                pitchers.add(" ");
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String pitcherFName = areaSnapshot.child("fname").getValue(String.class);
                    String pitcherLName = areaSnapshot.child("lname").getValue(String.class);
                    String pitcherFullName = pitcherFName + " " + pitcherLName;
                    // Add empty space for the start
                    pitchers.add(pitcherFullName);
                }

                // TODO move to fragment
                mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_pitcher_names);
                ArrayAdapter<String> pitchersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pitchers);
                pitchersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerPitchers.setAdapter(pitchersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isGame = true;
                Boolean isHome = true;

                // Check to make sure there is an entry in the spinner
                String pitcherName = "";
                String eventName = "";
                if (mSpinnerPitchers.getSelectedItem().toString() == " ") {
                    Toast.makeText(getActivity(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                } else if (mEventName.getText().toString() == "") {
                    Toast.makeText(getActivity(), "Enter an event name to start session", Toast.LENGTH_SHORT).show();
                } else {
                    pitcherName = mSpinnerPitchers.getSelectedItem().toString();
                    eventName = mEventName.getText().toString().trim();

                    if (!mEventType.isChecked()) {
                        isGame = false;
                    }
                    if (!mEventLocation.isChecked()) {
                        isHome = false;
                    }
                    Boolean eventSet = true;
                    Boolean pitcherSet = true;

                    mOnInputListener.sendInput(eventName, isGame, isHome, pitcherName);

                    getDialog().dismiss();

                    // Enable tagging
                    //TaggingActivity taggingActivity = new TaggingActivity();
                    //taggingActivity.enableTagging();
                }
            }
        });

        return view;
    }

    public static EventInfoFragment newInstance(String title) {
        EventInfoFragment frag = new EventInfoFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    // mOnInputListener.sendInput(input);
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException cce){
            Log.e("CCE", "onAttach: ClassCastException: " + cce.getMessage());
        }
    }

}
