package ldansorean.s5braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_DURATION_MS = 30 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hide game visual components
        findViewById(R.id.topLabels).setVisibility(View.INVISIBLE);
    }

    public void go(View view) {
        findViewById(R.id.go).setVisibility(View.INVISIBLE);

        startCountDownTimer();
        findViewById(R.id.topLabels).setVisibility(View.VISIBLE);
    }

    private void startCountDownTimer() {
        final TextView timeLabel = findViewById(R.id.timeLabel);
        new CountDownTimer(GAME_DURATION_MS + 20, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timeLabel.setText(secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                ; //nothing for now
            }
        }.start();

    }
}
