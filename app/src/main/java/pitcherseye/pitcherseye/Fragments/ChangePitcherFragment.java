/*
 This Fragment is used for the change pitcher workflow. When a user is ready to change pitchers,
 they can select the link in the banner to display this dialog. If the user doesn't change the user,
 we'll just dismiss the dialog. If the user does select a different pitcher, we'll send send the pitcher's
 session using the PitcherStats object to Firebase.
 */

package pitcherseye.pitcherseye.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class ChangePitcherFragment extends DialogFragment {

    // UI Components
    Button mConfirmChange;
    Spinner mSpinnerPitchers;

    DatabaseReference mDatabase;
    String pitcherName = "";

    public OnInputListener mOnInputListenerChangePitcher;

    // Send the pitcher information to TaggingActivity
    public interface OnInputListener {
        void sendInput(String pitcherName, int pitcherSpinnerIndex);
    }

    // Exception handling for the interface
    // Makes sure that the Fragment is associated with an Activity
    // Will throw java.lang.IllegalStateException: Fragment ChangePitcherFragment{9543f7f} not attached to Activity
    // without when registering a new user.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnInputListenerChangePitcher = (OnInputListener) getActivity();
        } catch (ClassCastException cce){
            Log.e("CCE", "onAttach: ClassCastException: " + cce.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_change_pitcher, container, false);
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        // Instantiate UI components
        mConfirmChange = (Button) view.findViewById(R.id.btn_confirm_pitcher_change);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mSpinnerPitchers = (Spinner) view.findViewById(R.id.spin_change_pitcher_names);

        // Instantiate and load pitchers into spinner
        // TODO see if there's a better way to handle this so that we keep this saved locally
        // after we log in
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> pitchers = new ArrayList<String>();
                pitchers.add(getString(R.string.string_select_pitcher));

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
        });

        // Confirmation handler for the mConfirmChange button
        mConfirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check to make sure there is an entry in the spinner
                if (mSpinnerPitchers.getSelectedItem().toString().trim().isEmpty() || mSpinnerPitchers.getSelectedItem().toString().trim().equals("<Select Pitcher>")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                } else if (mSpinnerPitchers.getSelectedItem().toString().trim().equals(taggingActivity.getPitcherName())) {
                    // If a new pitcher isn't selected, just close the dialog
                    getDialog().dismiss();
                } else {
                    // Check to see if it's the beginning of the game before we update the statistics in TaggingActivity
                    if (taggingActivity.getPitcherPitchCount() > 0) {
                        taggingActivity.sendPitcherStatsHelper();
                    }
                    savePitcherInputs();
                    taggingActivity.resetHeatMap();
                    getDialog().dismiss();
                }
            }
        });

        return view;
    }

    // This method helps with saving the information that we gather from the user inputs
    public void savePitcherInputs() {
        int pitcherIndex = mSpinnerPitchers.getSelectedItemPosition();

        pitcherName = mSpinnerPitchers.getSelectedItem().toString();
        mOnInputListenerChangePitcher.sendInput(pitcherName, pitcherIndex); // Send the information to TaggingActivity
    }
}