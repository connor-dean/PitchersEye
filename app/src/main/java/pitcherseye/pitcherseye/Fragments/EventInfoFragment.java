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

    Button mConfirmChange;
    CheckBox mEventType;
    CheckBox mEventLocation;
    DatabaseReference mDatabase;
    EditText mEventName;
    Spinner mSpinnerPitchers;

    String pitcherName = "";
    String eventName = "";

    TaggingActivity taggingActivity = (TaggingActivity) getActivity();

    public interface OnInputListener {
        void sendInput(String eventName, Boolean isGame, Boolean isHome, String pitcherName, int pitcherSpinnerIndex);
    }

    public OnInputListener mOnInputListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        mConfirmChange = (Button) view.findViewById(R.id.btn_confirm_info);
        mEventType = (CheckBox) view.findViewById(R.id.chck_bx_event_type);
        mEventLocation = (CheckBox) view.findViewById(R.id.chck_bx_event_location);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mEventName = (EditText) view.findViewById(R.id.edt_txt_event_name_entry);
        mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_pitcher_names);

        // Display previously entered values
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();
        mEventName.setText(taggingActivity.getEventName());
        mEventLocation.setChecked(taggingActivity.getHome());
        mEventType.setChecked(taggingActivity.getGame());

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

                // Load values into Spinner and set the index
                TaggingActivity taggingActivity = (TaggingActivity) getActivity();
                mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_pitcher_names);
                ArrayAdapter<String> pitchersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pitchers);
                pitchersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerPitchers.setAdapter(pitchersAdapter);
                mSpinnerPitchers.setSelection(taggingActivity.getPitcherSpinnerIndex());
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
                if (mEventName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter an event name to start session", Toast.LENGTH_SHORT).show();
                } else if (mSpinnerPitchers.getSelectedItem().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                } else if (mSpinnerPitchers.getSelectedItem().toString().trim().equals(taggingActivity.getPitcherName())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter a new pitcher to continue session", Toast.LENGTH_SHORT).show();
                } else {

                    // Check to see if it's the beginning of the game before we update the statistics in TaggingActivity
                    if (taggingActivity.getPitcherPitchCount() > 0) {
                        taggingActivity.sendPitcherStatsWrapper();
                    }

                    pitcherName = mSpinnerPitchers.getSelectedItem().toString();
                    int pitcherIndex = mSpinnerPitchers.getSelectedItemPosition();
                    eventName = mEventName.getText().toString().trim();

                    if (!mEventType.isChecked()) {
                        isGame = false;
                    }
                    if (!mEventLocation.isChecked()) {
                        isHome = false;
                    }

                    // Send the information to TaggingActivity
                    mOnInputListener.sendInput(eventName, isGame, isHome, pitcherName, pitcherIndex);

                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException cce){
            Log.e("CCE", "onAttach: ClassCastException: " + cce.getMessage());
        }
    }

    public String getPitcherName() {
        return pitcherName;
    }

    public void setPitcherName(String pitcherName) {
        this.pitcherName = pitcherName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
