package pitcherseye.pitcherseye.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import pitcherseye.pitcherseye.Activities.TaggingActivity;
import pitcherseye.pitcherseye.R;

/**
 * Created by Connor on 3/29/2018.
 */

public class ResultsFragment extends DialogFragment {

    Button mFastball;
    Button mChangeup;
    Button mCurveball;
    Button mSlider;
    Button mOther;

    int pitcherFastballCount;
    int eventFastballCount;
    int pitcherChangeupCount;
    int eventChangeupCount;
    int pitcherCurveballCount;
    int eventCurveballCount;
    int pitcherSliderCount;
    int eventSliderCount;
    int pitcherOtherCount;
    int eventOtherCount;

    TaggingActivity taggingActivity = (TaggingActivity) getActivity();

    public interface OnInputListener {
        void sendResultsInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                       int pitcherSliderCount, int pitcherOtherCount);
    }

    public OnInputListener mOnInputResultsListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, container, false);

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        // Disable the back button on selection
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        Toast.makeText(getActivity(), "Please select a pitch result", Toast.LENGTH_SHORT).show();
                        Log.i("Back Pressed", "ResultsFragment");
                        return true;
                    }
                }
                return false;
            }
        });

        // Buttons
        mFastball = (Button) view.findViewById(R.id.btn_result_fastball);
        mChangeup = (Button) view.findViewById(R.id.btn_result_changeup);
        mCurveball = (Button) view.findViewById(R.id.btn_result_curve);
        mSlider = (Button) view.findViewById(R.id.btn_result_slider);
        mOther = (Button) view.findViewById(R.id.btn_result_other);

        //final TaggingActivity taggingActivity = new TaggingActivity();
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();

        // Result events
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherFastballCount;
                ++eventFastballCount;
                taggingActivity.updatePitcherResultsCounts(true, false, false, false, false);
                getDialog().dismiss();
            }
        });

        mChangeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherChangeupCount;
                ++eventChangeupCount;
                taggingActivity.updatePitcherResultsCounts(false, true, false, false, false);
                getDialog().dismiss();
            }
        });

        mCurveball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherCurveballCount;
                ++eventCurveballCount;
                taggingActivity.updatePitcherResultsCounts(false, false, true, false, false);
                getDialog().dismiss();
            }
        });

        mSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherSliderCount;
                ++eventSliderCount;
                taggingActivity.updatePitcherResultsCounts(false, false, false, true, false);
                getDialog().dismiss();
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherOtherCount;
                ++eventOtherCount;
                taggingActivity.updatePitcherResultsCounts(false, false, false, false, true);
                getDialog().dismiss();
            }
        });

        return view;
    }
}
