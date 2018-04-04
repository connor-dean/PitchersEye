package pitcherseye.pitcherseye.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    int undoPitchType = 0;

    int pitcherFastballCount = 0;
    int eventFastballCount = 0;
    int pitcherChangeupCount = 0;
    int eventChangeupCount = 0;
    int pitcherCurveballCount = 0;
    int eventCurveballCount = 0;
    int pitcherSliderCount = 0;
    int eventSliderCount = 0;
    int pitcherOtherCount = 0;
    int eventOtherCount = 0;

    TaggingActivity taggingActivity = (TaggingActivity) getActivity();

    public interface OnInputListener {
        void sendInput(int pitcherFastballCount, int pitcherChangeupCount, int pitcherCurveballCount,
                       int pitcherSliderCount, int pitcherOtherCount);
    }

    public OnInputListener mOnInputListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_results, container, false);

        // Make sure the user can't exit the DialogFragment without confirming their input
        getDialog().setCanceledOnTouchOutside(false);

        // Buttons
        mFastball = (Button) view.findViewById(R.id.btn_result_fastball);
        mChangeup = (Button) view.findViewById(R.id.btn_result_changeup);
        mCurveball = (Button) view.findViewById(R.id.btn_result_curve);
        mSlider = (Button) view.findViewById(R.id.btn_result_slider);
        mOther = (Button) view.findViewById(R.id.btn_result_other);

        //final TaggingActivity taggingActivity = new TaggingActivity();

        // Result events
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherFastballCount;
                ++eventFastballCount;
                /*mOnInputListener.sendInput(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                //taggingActivity.setPitcherFastballCount(++pitcherFastballCount);
                /*taggingActivity.updatePitcherResultsCounts(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                getDialog().dismiss();
            }
        });

        mChangeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherChangeupCount;
                ++eventChangeupCount;
                /*mOnInputListener.sendInput(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                /*taggingActivity.updatePitcherResultsCounts(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                getDialog().dismiss();
            }
        });

        mCurveball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherCurveballCount;
                ++eventCurveballCount;
                /*mOnInputListener.sendInput(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                /*taggingActivity.updatePitcherResultsCounts(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                getDialog().dismiss();
            }
        });

        mSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherSliderCount;
                ++eventSliderCount;
                /*mOnInputListener.sendInput(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                /*taggingActivity.updatePitcherResultsCounts(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                getDialog().dismiss();
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase count
                ++pitcherOtherCount;
                ++eventOtherCount;
                /*mOnInputListener.sendInput(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                /*taggingActivity.updatePitcherResultsCounts(pitcherFastballCount, pitcherChangeupCount, pitcherCurveballCount,
                        pitcherSliderCount, pitcherOtherCount);*/
                getDialog().dismiss();
            }
        });

        return view;
    }

}
