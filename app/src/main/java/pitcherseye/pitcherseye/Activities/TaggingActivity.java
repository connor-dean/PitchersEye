package pitcherseye.pitcherseye.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pitcherseye.pitcherseye.R;

public class TaggingActivity extends Activity {

    // Buttons
    Button mR1C1;
    Button mR1C2;
    Button mR1C3;
    Button mR2C1;
    Button mR2C2;
    Button mR2C3;
    Button mR3C1;
    Button mR3C2;
    Button mR3C3;
    TextView mPitchCount;
    TextView mTextR1C1;
    TextView mTextR1C2;
    TextView mTextR1C3;
    TextView mTextR2C1;
    TextView mTextR2C2;
    TextView mTextR2C3;
    TextView mTextR3C1;
    TextView mTextR3C2;
    TextView mTextR3C3;

    // Request Code
    int REQUEST_CODE_CALCULATE = 0;

    int totalPitchCount = 0;

    int count_R1C1 = 0;
    int count_R1C2 = 0;
    int count_R1C3 = 0;
    int count_R2C1 = 0;
    int count_R2C2 = 0;
    int count_R2C3 = 0;
    int count_R3C1 = 0;
    int count_R3C2 = 0;
    int count_R3C3 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagging);

        // Instantiate Buttons
        mR1C1 = (Button) findViewById(R.id.btnR1C1);
        mR1C2 = (Button) findViewById(R.id.btnR1C2);
        mR1C3 = (Button) findViewById(R.id.btnR1C3);
        mR2C1 = (Button) findViewById(R.id.btnR2C1);
        mR2C2 = (Button) findViewById(R.id.btnR2C2);
        mR2C3 = (Button) findViewById(R.id.btnR2C3);
        mR3C1 = (Button) findViewById(R.id.btnR1C1);
        mR3C2 = (Button) findViewById(R.id.btnR2C2);
        mR3C3 = (Button) findViewById(R.id.btnR3C3);

        // Instantiate TextViews
        mPitchCount = (TextView) findViewById(R.id.txt_pitch_count);
        mTextR1C1 = (TextView) findViewById(R.id.txt_R1C1);
        mTextR1C2 = (TextView) findViewById(R.id.txt_R1C2);
        mTextR1C3 = (TextView) findViewById(R.id.txt_R1C3);
        mTextR2C1 = (TextView) findViewById(R.id.txt_R2C1);
        mTextR2C2 = (TextView) findViewById(R.id.txt_R2C2);
        mTextR2C3 = (TextView) findViewById(R.id.txt_R2C3);
        mTextR3C1 = (TextView) findViewById(R.id.txt_R3C1);
        mTextR3C2 = (TextView) findViewById(R.id.txt_R3C2);
        mTextR3C3 = (TextView) findViewById(R.id.txt_R3C3);

//        GridPress(mR1C1, mTextR1C1, count_R1C1);
//        GridPress(mR1C2, mTextR1C2);
//        GridPress(mR1C3, mTextR1C3);
//        GridPress(mR2C1, mTextR2C1);
//        GridPress(mR2C2, mTextR2C2);
//        GridPress(mR2C3, mTextR2C3);
//        GridPress(mR3C1, mTextR3C1);
//        GridPress(mR3C2, mTextR3C2);
//        GridPress(mR3C3, mTextR3C3);

        mR1C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR1C1.setText(Integer.toString(count_R1C1++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
            }
        });

        mR1C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextR1C2.setText(Integer.toString(count_R1C2++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
            }
        });

    }

    //implement the onClick method here
    /*public void onClick(View v) {
        // Perform action on click
        switch(v.getId()) {
            case R.id.btnR1C1:
                //totalPitchCount++;
                //count_R1C1++;
                mTextR1C1.setText(Integer.toString(count_R1C1++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR1C2:
                *//*totalPitchCount++;
                count_R1C2++;*//*
                mTextR1C1.setText(Integer.toString(count_R1C2++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR1C3:
                *//*totalPitchCount++;
                count_R1C3++;*//*
                mTextR1C1.setText(Integer.toString(count_R1C3++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR2C1:
                *//*totalPitchCount++;
                count_R2C1++;*//*
                mTextR1C1.setText(Integer.toString(count_R2C1++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR2C2:
                *//*totalPitchCount++;
                count_R2C2++;*//*
                mTextR1C1.setText(Integer.toString(count_R2C2++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR2C3:
               *//* totalPitchCount++;
                count_R2C3++;*//*
                mTextR1C1.setText(Integer.toString(count_R2C3++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR3C1:
                *//*totalPitchCount++;
                count_R3C1++;*//*
                mTextR1C1.setText(Integer.toString(count_R3C1++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR3C2:
                *//*totalPitchCount++;
                count_R3C2++;*//*
                mTextR1C1.setText(Integer.toString(count_R3C2++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
            case R.id.btnR3C3:
                *//*totalPitchCount++;
                count_R3C3++;*//*
                mTextR1C1.setText(Integer.toString(count_R3C3++));
                mPitchCount.setText(Integer.toString(totalPitchCount++));
                break;
        }
    }*/

    public void adjustCounts(int gridCount, int totalPitchCount) {

    }

/*    public void GridPress(Button gridButton, final TextView gridText, int count, int gridCount) {
        gridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalPitchCount++;

                // Set the count to the appropriate grid location
                gridText.setText(Integer.toString(count));

                // Adjust the pitch count
                mPitchCount.setText(Integer.toString(count));
            }
        });
    }*/

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, TaggingActivity.class);
        return i;
    }

}
