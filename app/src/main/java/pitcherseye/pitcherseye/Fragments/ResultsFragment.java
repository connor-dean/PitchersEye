package pitcherseye.pitcherseye.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        // Result events
        mFastball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    // Increase count
                mEventFastballCount.setText(Integer.toString(++eventFastballCount));

                // Increase pitcher count
                mPitcherFastballCount.setText(Integer.toString(++pitcherFastballCount));

                // Keep track of previous pitch
                undoPitchType = 10;

                // Reenable grid
                enableTagging(locationSelected = false);*/
                getDialog().dismiss();
            }
        });

        mChangeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    // Increase count
                mEventChangeupCount.setText(Integer.toString(++eventChangeupCount));

                // Increase pitcher count
                mPitcherChangeupCount.setText(Integer.toString(++pitcherChangeupCount));

                // Keep track of previous pitch
                undoPitchType = 11;

                // Reenable grid
                enableTagging(locationSelected = false);*/
                getDialog().dismiss();
            }
        });

        mCurveball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   // Increase count
                mEventCurveballCount.setText(Integer.toString(++eventCurveballCount));

                // Increase pitcher count
                mPitcherCurveballCount.setText(Integer.toString(++pitcherCurveballCount));

                // Keep track of previous pitch
                undoPitchType = 12;

                // Reenable grid
                enableTagging(locationSelected = false);*/
                getDialog().dismiss();
            }
        });

        mSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   // Increase count
                mEventSliderCount.setText(Integer.toString(++eventSliderCount));

                // Increase pitcher count
                mPitcherSliderCount.setText(Integer.toString(++pitcherSliderCount));

                // Keep track of previous pitch
                undoPitchType = 13;

                // Reenable grid
                enableTagging(locationSelected = false);*/
                getDialog().dismiss();
            }
        });

        mOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   // Increase count
                mEventOtherCount.setText(Integer.toString(++eventOtherCount));

                // Increase pitcher count
                mPitcherOtherCount.setText(Integer.toString(++pitcherOtherCount));

                // Keep track of previous pitch
                undoPitchType = 14;

                // Reenable grid
                enableTagging(locationSelected = false);*/
                getDialog().dismiss();
            }
        });

        return view;
    }

}
