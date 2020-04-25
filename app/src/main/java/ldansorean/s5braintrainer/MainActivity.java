package ldansorean.s5braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_DURATION_MS = 30 * 1000;
    private GameChallenge gameChallenge;
    private TextView sumLabel, timeLabel, resultLabel;
    private TableLayout possibleSumsTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumLabel = findViewById(R.id.sumLabel);
        timeLabel = findViewById(R.id.timeLabel);
        resultLabel = findViewById(R.id.resultLabel);
        possibleSumsTable = findViewById(R.id.possibleSumsTable);
        gameChallenge = new GameChallenge();

        findViewById(R.id.topLabels).setVisibility(View.INVISIBLE);
        possibleSumsTable.setVisibility(View.INVISIBLE);
        resultLabel.setVisibility(View.INVISIBLE);
    }

    public void go(View view) {
        findViewById(R.id.go).setVisibility(View.INVISIBLE);

        showNewChallenge();
        startCountDownTimer();

        findViewById(R.id.topLabels).setVisibility(View.VISIBLE);
        possibleSumsTable.setVisibility(View.VISIBLE);
        resultLabel.setVisibility(View.VISIBLE);
    }

    public void sumSelected(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        if (gameChallenge.isSumCorrect(position)) {
            resultLabel.setText("Correct!");
        } else {
            resultLabel.setText("Wrong!");
        }
        showNewChallenge();
    }

    private void showNewChallenge() {
        gameChallenge.newChallenge();
        setSums();
    }

    private void setSums() {
        sumLabel.setText(gameChallenge.getFirstNumber() + " + " + gameChallenge.getSecondNumber());
        for (int i = 0; i < 4; i++) {
            TextView sumTextView = possibleSumsTable.findViewWithTag(String.valueOf(i));
            sumTextView.setText(String.valueOf(gameChallenge.getSum(i)));
        }
    }

    private void startCountDownTimer() {
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
