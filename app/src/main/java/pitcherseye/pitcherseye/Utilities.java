package pitcherseye.pitcherseye;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.Random;

/**
 * Created by Connor on 2/6/2018.
 */

public class Utilities {

    // Method to generate a random Hex number for IDs
    public static String createRandomHex(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        while(sb.length() < length) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString();
    }

    // Method to help hide virtual keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
