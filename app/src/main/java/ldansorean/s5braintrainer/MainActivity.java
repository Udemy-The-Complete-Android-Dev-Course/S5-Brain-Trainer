package ldansorean.s5braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int GAME_DURATION_MS = 10 * 1000;
    private GameState gameState;
    private TextView sumLabel, timeLabel, resultLabel, scoreLabel;
    private Button playAgain;
    private TableLayout possibleSumsTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sumLabel = findViewById(R.id.sumLabel);
        timeLabel = findViewById(R.id.timeLabel);
        resultLabel = findViewById(R.id.resultLabel);
        possibleSumsTable = findViewById(R.id.possibleSumsTable);
        scoreLabel = findViewById(R.id.scoreLabel);
        playAgain = findViewById(R.id.playAgain);
        gameState = new GameState();

        //initially hide all gameplay view components and show only the start button
        findViewById(R.id.go).setVisibility(View.VISIBLE);
        findViewById(R.id.topLabels).setVisibility(View.INVISIBLE);
        possibleSumsTable.setVisibility(View.INVISIBLE);
        resultLabel.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
    }

    public void go(View view) {
        findViewById(R.id.go).setVisibility(View.INVISIBLE);
        findViewById(R.id.topLabels).setVisibility(View.VISIBLE);
        possibleSumsTable.setVisibility(View.VISIBLE);
        resultLabel.setVisibility(View.VISIBLE);
        scoreLabel.setVisibility(View.VISIBLE);

        startNewGame();
    }

    public void sumSelected(View view) {
        int position = Integer.parseInt(view.getTag().toString());
        if (gameState.isSumCorrect(position)) {
            resultLabel.setText("Correct!");
        } else {
            resultLabel.setText("Wrong!");
        }
        gameState.markAnswer(position);
        scoreLabel.setText( gameState.getCorrectAnswers() + "/" + gameState.getNoOfQuestions() );
        newChallenge();
    }

    private void newChallenge() {
        gameState.newChallenge();
        displayNewChallenge();
    }

    private void displayNewChallenge() {
        sumLabel.setText(gameState.getFirstNumber() + " + " + gameState.getSecondNumber());
        for (int i = 0; i < 4; i++) {
            TextView sumTextView = possibleSumsTable.findViewWithTag(String.valueOf(i));
            sumTextView.setText(String.valueOf(gameState.getSum(i)));
        }
    }

    private void setSumButtonsEnabled(boolean enabled) {
        for (int i = 0; i < 4; i++) {
            TextView sumTextView = possibleSumsTable.findViewWithTag(String.valueOf(i));
            sumTextView.setEnabled(enabled);
        }
    }

    private void startCountDownTimer() {
        new CountDownTimer(GAME_DURATION_MS + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                timeLabel.setText(secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                setSumButtonsEnabled(false);
                timeLabel.setText("0s");
                resultLabel.setText("Your score: " + gameState.getCorrectAnswers() + " out of " + gameState.getNoOfQuestions());
                playAgain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void onPlayAgainClick(View view) {
        startNewGame();
    }

    private void startNewGame() {
        gameState.startGame();
        playAgain.setVisibility(View.INVISIBLE);
        resultLabel.setText("");
        scoreLabel.setText("0/0");
        displayNewChallenge();
        setSumButtonsEnabled(true);
        startCountDownTimer();
    }
}
