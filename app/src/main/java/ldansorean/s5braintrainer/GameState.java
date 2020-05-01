package ldansorean.s5braintrainer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class storing the game state: current challenge and score.
 * The current challenge consists of the two numbers to be added and four possible sums. One of them is the correct one.
 * The score consists of the number of questions asked and the number of correct answers.
 */
public class GameState {

    private static final int NUMBER_MAX = 20;
    private static final int NUMBER_MIN = 1;
    private static final int SUM_MAX = 40;
    private static final int NO_OF_SUMS = 4;

    private int no1, no2;
    private ArrayList<Integer> possibleSums;
    private int noOfQuestions, correctAnswers;
    private Random randomiser;

    public GameState() {
        randomiser = new Random();
        startGame();
    }

    /**
     * Generates a new game challenge. Randomly chooses two new numbers to be added and four possible sums.
     */
    public void newChallenge() {
        no1 = NUMBER_MIN + randomiser.nextInt(NUMBER_MAX - NUMBER_MIN);
        no2 = NUMBER_MIN + randomiser.nextInt(NUMBER_MAX - NUMBER_MIN);

        Set<Integer> possibleSumsSet = new HashSet<>();
        possibleSumsSet.add(no1 + no2);
        while (possibleSumsSet.size() < NO_OF_SUMS) {
            possibleSumsSet.add(randomiser.nextInt(SUM_MAX - NUMBER_MIN) + NUMBER_MIN);
        }

        possibleSums = new ArrayList<>(possibleSumsSet);
    }

    /**
     * @return the first number to be added.
     */
    public int getFirstNumber() {
        return no1;
    }

    /**
     * @return the second number to be added.
     */
    public int getSecondNumber() {
        return no2;
    }

    /**
     * @return the nth possible sum.
     */
    public int getSum(int n) {
        return possibleSums.get(n);
    }

    /**
     * @return number of question asked during the current game.
     */
    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    /**
     * @return number of questions answered correctly during the current game.
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Checks if the selected sum is correct.
     * @param n - The position of the chosen sum.
     * @return true if that is the correct sum or false otherwise.
     */
    public boolean isSumCorrect(int n) {
        return no1 + no2 == possibleSums.get(n);
    }

    /**
     * Update the score according to the sum selected by the user. Also increments the number of asked questions.
     * @param n - The position of the sum chosen by user.
     */
    public void markAnswer(int n) {
        noOfQuestions++;
        if (isSumCorrect(n)) {
            correctAnswers++;
        }
    }

    public void startGame() {
        noOfQuestions = 0;
        correctAnswers = 0;
        newChallenge();
    }
}
