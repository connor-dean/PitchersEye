package pitcherseye.pitcherseye;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    IntentHelper aIntentHelper = new IntentHelper();

    // Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeIntent();
    }

    public void changeIntent() {
        Button mTestButton = (Button) findViewById(R.id.test_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aIntentHelper.changeActivity(v, IntentHelper.class);
            }
        });
    }
}
