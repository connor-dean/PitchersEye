package pitcherseye.pitcherseye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Connor on 1/22/2018.
 */

public class IntentHelper extends AppCompatActivity{

    int REQUEST_CODE_CALCULATE = 0;

    public static Intent setIntent(Context packageContext, Class thisClass) {
        Intent i = new Intent(packageContext, thisClass);
        return i;
    }

    public void newIntent(Class newClass, Context packageContext, Class thisClass, int REQUEST_CODE_CALCULATE) {
        IntentHelper aIntentHelper = new IntentHelper();
        // Intent i = CalculateActivity.newIntent(MainActivity.this);
        Intent i = this.setIntent(packageContext, thisClass);
        startActivityForResult(i, REQUEST_CODE_CALCULATE);
    }


    public void changeActivity(View view, Class newClass) {
        Intent intent = new Intent(this, newClass);
        startActivity(intent);
    }


}
