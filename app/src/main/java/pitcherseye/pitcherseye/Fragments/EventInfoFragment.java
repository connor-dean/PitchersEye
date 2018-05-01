/*
 This Fragment gathers user inputs about the event's information on startup and finish of the TaggingActivity.
 On startup we'll ask for the user to input the information and on finish we'll ask for them to confirm the
 information that they put in earlier.
 */

package pitcherseye.pitcherseye.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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

    // UI Components
    DatabaseReference mUserReference;
    Button mConfirmChange;
    RadioGroup mRadioGroupEventType;
    RadioButton mRadioGame;
    RadioButton mRadioPractice;
    RadioGroup mRadioGroupLocation;
    RadioButton mRadioHome;
    RadioButton mRadioAway;
    DatabaseReference mDatabase;
    EditText mEventName;
    Spinner mSpinnerPitchers;
    TextView mEventInfo;
    Boolean isGame;
    Boolean isHome;
    String pitcherName = "";
    String eventName = "";

    public OnInputListener mOnInputListener;
    public ValueEventListener mValueReadUsersFirebase;

    // Send the user inputs from the dialog to TaggingActivity
    public interface OnInputListener {
        void sendInput(String eventName, Boolean isGame, Boolean isHome, String pitcherName, int pitcherSpinnerIndex);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_info, container, false);
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        // TODO Reference
        mUserReference = FirebaseDatabase.getInstance().getReference().child("users");

        // Disable the back button on selection
        // We'll check if the original event info is set, if it hasn't been, disable the back button.
        // If it HAS been set, enable the back button in case the user wants to keep tagging.
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (!taggingActivity.getEventInfoSet()) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Please enter event info", Toast.LENGTH_SHORT).show();
                        Log.i("Back Pressed", "EventInfoFragment");
                        return true;
                    }
                }
                return false;
            }
        });

        // Set the checkboxes on startup. This helps with confirming the event information at
        // the end of the event.
        isGame = taggingActivity.getGame();
        isHome = taggingActivity.getHome();

        // Instantiate UI Components
        mConfirmChange = (Button) view.findViewById(R.id.btn_confirm_info);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mEventName = (EditText) view.findViewById(R.id.edt_txt_event_name_entry);
        mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_pitcher_names);
        mEventInfo = (TextView) view.findViewById(R.id.txt_edit_event);
        mRadioGroupEventType = (RadioGroup) view.findViewById(R.id.radio_group_event_type);
        mRadioGame = (RadioButton) view.findViewById(R.id.radio_event_type_game);
        mRadioPractice = (RadioButton) view.findViewById(R.id.radio_event_type_practice);
        mRadioGroupLocation = (RadioGroup) view.findViewById(R.id.radio_group_location);
        mRadioHome = (RadioButton) view.findViewById(R.id.radio_event_location_home);
        mRadioAway = (RadioButton) view.findViewById(R.id.radio_event_location_away);

        // Check to see if we are finishing the game or doing the initial entry
        if (taggingActivity.getEventInfoSet()) {
            mEventInfo.setText("Confirm Information");
            mConfirmChange.setText("Finish Event");
            mSpinnerPitchers.setVisibility(View.GONE);

            // Display previously entered values
            mEventName.setText(taggingActivity.getEventName());
        }

        // Set the checkboxes
        if (!isGame) {
            mRadioPractice.setChecked(true);
        }
        if (!isHome) {
            mRadioAway.setChecked(true);
        }

        // Event handler for the RadioGroups. Set values on change.
        mRadioGroupEventType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mRadioGame.isChecked()) {
                    isGame = true;
                } else {
                    isGame = false;
                }
            }
        });

        // Event handler for the RadioGroups. Set values on change.
        mRadioGroupLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mRadioHome.isChecked()) {
                    isHome = true;
                } else {
                    isHome = false;
                }
            }
        });

        // Confirmation handler for the mConfirmChange button
        mConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the event info has been set before, this will ask for confirmation from the user
                if (taggingActivity.getEventInfoSet()) {
                    if (mEventName.getText().toString().trim().isEmpty()) {
                        // Make sure there is a valid input for event name
                        Toast.makeText(getActivity(), "Please enter an event name to end the session", Toast.LENGTH_SHORT).show();
                    } else {
                        // If inputs are confirmed, save them, send them to TaggingActivity and end the event
                        saveEventInputs();
                        taggingActivity.finishGameHelper();
                    }
                } else {
                    // If this is the first time that information is being entered, validate that inputs are correct
                    // Check to make sure there is an entry in the spinner
                    if (mEventName.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Enter an event name to start session", Toast.LENGTH_SHORT).show();
                    } else if (mSpinnerPitchers.getSelectedItem().toString().trim().isEmpty() || mSpinnerPitchers.getSelectedItem().toString().trim().equals("<Select Pitcher>")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                    } else if (mSpinnerPitchers.getSelectedItem().toString().trim().equals(taggingActivity.getPitcherName()) && taggingActivity.getEventInfoSet()) {
                        getDialog().dismiss();
                    } else {
                        // Check to see if it's the beginning of the game before we update the statistics in TaggingActivity
                        if (taggingActivity.getPitcherPitchCount() > 0) {
                            taggingActivity.sendPitcherStatsHelper();
                        }
                        saveEventInputs();
                        getDialog().dismiss();
                    }
                }
            }
        });

        return view;
    }

    // This method helps with saving the information that we gather from the user inputs
    public void saveEventInputs() {
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();
        int pitcherIndex = mSpinnerPitchers.getSelectedItemPosition();

        pitcherName = mSpinnerPitchers.getSelectedItem().toString();
        eventName = mEventName.getText().toString().trim();
        mOnInputListener.sendInput(eventName, isGame, isHome, pitcherName, pitcherIndex); // Send the information to TaggingActivity
        taggingActivity.setEventInfoSet(true); // Event info has been set

        getDialog().dismiss();
    }

    // Helper for loading the pitcher information into the spinner. Helps with avoiding
    // NullPointerExceptions
    @Override
    public void onStart() {
        super.onStart();

        mValueReadUsersFirebase = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> pitchers = new ArrayList<String>();
                pitchers.add(getActivity().getResources().getString(R.string.string_select_pitcher));

                // Load pitcher information from the "users" child in Firebase and concatenate the values into List.
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String pitcherFName = areaSnapshot.child("fname").getValue(String.class);
                    String pitcherLName = areaSnapshot.child("lname").getValue(String.class);
                    String pitcherFullName = pitcherFName + " " + pitcherLName;
                    pitchers.add(pitcherFullName);
                }

                // Load values into Spinner and set the index
                TaggingActivity taggingActivity = (TaggingActivity) getActivity();
                ArrayAdapter<String> pitchersAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pitchers);
                pitchersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerPitchers.setAdapter(pitchersAdapter);
                mSpinnerPitchers.setSelection(taggingActivity.getPitcherSpinnerIndex());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity().getApplicationContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        mUserReference.addValueEventListener(mValueReadUsersFirebase);
    }

    // Exception handling for the interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException cce) {
            Log.e("CCE", "onAttach: ClassCastException: " + cce.getMessage());
        }
    }

    // Helps with connectivity issues in Firebase
    @Override
    public void onStop() {
        if (mValueReadUsersFirebase != null && mUserReference != null) {
            mUserReference.removeEventListener(mValueReadUsersFirebase);
        }
        super.onStop();
    }

    // Helps with connectivity issues in Firebase
    @Override
    public void onPause() {
        if (mValueReadUsersFirebase != null && mUserReference != null) {
            mUserReference.removeEventListener(mValueReadUsersFirebase);
        }
        super.onPause();
    }
}