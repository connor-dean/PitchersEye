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

import pitcherseye.pitcherseye.R;

public class EventInfoFragment extends DialogFragment {
   /* // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EventInfoFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventInfoFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static EventInfoFragment newInstance(String param1, String param2) {
        EventInfoFragment fragment = new EventInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

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
                String eventName = mEventName.getText().toString().trim();
                Boolean isGame = true;
                Boolean isHome = true;
                if (!mEventType.isChecked()) {
                    isGame = false;
                }
                if (!mEventLocation.isChecked()) {
                    isHome = false;
                }
                Boolean eventSet = true;

                String pitcherName = "";
                if (mSpinnerPitchers.getSelectedItem().toString() == " ") {
                    Toast.makeText(getActivity(), "Enter a pitcher to start session", Toast.LENGTH_SHORT).show();
                } else {
                    pitcherName = mSpinnerPitchers.getSelectedItem().toString();
                }


                Boolean pitcherSet = true;

                mOnInputListener.sendInput(eventName, isGame, isHome, pitcherName);

                getDialog().dismiss();
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
