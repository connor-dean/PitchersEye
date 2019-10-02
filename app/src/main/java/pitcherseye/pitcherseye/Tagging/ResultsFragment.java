/*
 This Fragment handles the input from the user after a pitch region has been selected in the TaggingActivity.
 It will display a dialog prompting the user to select a pitch type, and the user will not be able to exit this dialog
 without selecting a result.
 */

package pitcherseye.pitcherseye.Tagging;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pitcherseye.pitcherseye.Tagging.TaggingActivity;
import pitcherseye.pitcherseye.R;

public class ResultsFragment extends DialogFragment {

    // UI Components
    @BindView(R.id.btn_result_fastball) Button mFastball;
    @BindView(R.id.btn_result_changeup) Button mChangeup;
    @BindView(R.id.btn_result_curve) Button mCurveball;
    @BindView(R.id.btn_result_slider) Button mSlider;
    @BindView(R.id.btn_result_other) Button mOther;

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

    public interface OnInputListener {
        void sendResultsInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                              int pitcherSliderCount, int pitcherOtherCount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, container, false);
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();
        ButterKnife.bind(this, view);

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
        return view;
    }

    @OnClick({ R.id.btn_result_fastball, R.id.btn_result_changeup, R.id.btn_result_curve, R.id.btn_result_slider, R.id.btn_result_other })
    void pitchTypeResultIncrementer(View view) {
        final TaggingActivity taggingActivity = (TaggingActivity) getActivity();
        switch (view.getId()) {
            case R.id.btn_result_fastball:
                taggingActivity.pitchResultHelper(++eventFastballCount, ++pitcherFastballCount, 0);
                break;
            case R.id.btn_result_changeup:
                taggingActivity.pitchResultHelper(++eventChangeupCount, ++pitcherChangeupCount, 1);
                break;
            case R.id.btn_result_curve:
                taggingActivity.pitchResultHelper(++eventCurveballCount, ++pitcherCurveballCount, 2);
                break;
            case R.id.btn_result_slider:
                taggingActivity.pitchResultHelper(++eventSliderCount, ++pitcherSliderCount, 3);
                break;
            case R.id.btn_result_other:
                taggingActivity.pitchResultHelper(++eventOtherCount, ++pitcherOtherCount, 4);
                break;
        }
        getDialog().dismiss();
    }

}
