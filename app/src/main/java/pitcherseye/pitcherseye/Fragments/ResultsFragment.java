/*
 This Fragment handles the input from the user after a pitch region has been selected in the TaggingActivity.
 It will display a dialog prompting the user to select a pitch type, and the user will not be able to exit this dialog
 without selecting a result.
 */

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

public class ResultsFragment extends DialogFragment {

    // UI Components
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

    //public OnInputListener mOnInputResultsListener; TODO
    //TaggingActivity taggingActivity = (TaggingActivity) getActivity(); TODO

    public interface OnInputListener {
        void sendResultsInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                              int pitcherSliderCount, int pitcherOtherCount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, container, false);
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        // Disable the back button on selection so the user has to select a result
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Toast.makeText(getActivity(), "Please select a pitch result", Toast.LENGTH_SHORT).show();
                    Log.i("Back Pressed", "ResultsFragment");
                    return true;
                }
                return false;
            }
        });

        // Instantiate Buttons
        mFastball = (Button) view.findViewById(R.id.btn_result_fastball);
        mChangeup = (Button) view.findViewById(R.id.btn_result_changeup);
        mCurveball = (Button) view.findViewById(R.id.btn_result_curve);
        mSlider = (Button) view.findViewById(R.id.btn_result_slider);
        mOther = (Button) view.findViewById(R.id.btn_result_other);

        // Event handlers for the result buttons
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherFastballCount;
                ++eventFastballCount;

                // Notify TaggingActivity the last result for the undo workflow
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

                // Notify TaggingActivity the last result for the undo workflow
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

                // Notify TaggingActivity the last result for the undo workflow
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

                // Notify TaggingActivity the last result for the undo workflow
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

                // Notify TaggingActivity the last result for the undo workflow
                taggingActivity.updatePitcherResultsCounts(false, false, false, false, true);
                getDialog().dismiss();
            }
        });

        return view;
    }
}
