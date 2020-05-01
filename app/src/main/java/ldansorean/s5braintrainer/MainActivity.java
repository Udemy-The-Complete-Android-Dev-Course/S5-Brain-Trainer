package ldansorean.s5braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_DURATION_MS = 5 * 1000;
    private GameChallenge gameChallenge;
    private TextView sumLabel, timeLabel, resultLabel, scoreLabel;
    private TableLayout possibleSumsTable;
    private int noOfQuestions, correctAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumLabel = findViewById(R.id.sumLabel);
        timeLabel = findViewById(R.id.timeLabel);
        resultLabel = findViewById(R.id.resultLabel);
        possibleSumsTable = findViewById(R.id.possibleSumsTable);
        scoreLabel = findViewById(R.id.scoreLabel);
        gameChallenge = new GameChallenge();

        //initially hide all gameplay view components and show only the start button
        findViewById(R.id.go).setVisibility(View.VISIBLE);
        findViewById(R.id.topLabels).setVisibility(View.INVISIBLE);
        possibleSumsTable.setVisibility(View.INVISIBLE);
        resultLabel.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
    }

    public void go(View view) {
        findViewById(R.id.go).setVisibility(View.INVISIBLE);

        newChallenge();
        startCountDownTimer();

        findViewById(R.id.topLabels).setVisibility(View.VISIBLE);
        possibleSumsTable.setVisibility(View.VISIBLE);
        resultLabel.setVisibility(View.VISIBLE);
        scoreLabel.setVisibility(View.VISIBLE);
    }

    public void sumSelected(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        if (gameChallenge.isSumCorrect(position)) {
            resultLabel.setText("Correct!");
            correctAnswers++;
        } else {
            resultLabel.setText("Wrong!");
        }
        noOfQuestions++;
        scoreLabel.setText( correctAnswers + "/" + noOfQuestions );
        newChallenge();
    }

    private void newChallenge() {
        gameChallenge.newChallenge();
        displayNewChallenge();
    }

    private void displayNewChallenge() {
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
                timeLabel.setText("0s");
                resultLabel.setText("Your score: " + correctAnswers + " out of " + noOfQuestions);
            }
        }.start();
    }
}
