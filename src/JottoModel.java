import java.util.ArrayList;

// View interface
interface IView {
    public void updateView();
}


public class JottoModel
{
	public static int NUM_LETTERS = 5;
	public static final String[] LEVELS = {
      "Easy", "Medium", "Hard", "Any Difficulty"};
    private WordList allWords;
    private Word guessWord;
    private String guessString;
    private String answerString;
    private int[] exactMatchedCount;
    private int[] partialMatchedCount;
    private int guessCount;
    private boolean[] partialMatches;
    private boolean[] exactMatches;
    private boolean gameOver;
    private boolean validation;
    private boolean won;
    private boolean newGame;

    public void init(){
        allWords = new WordList("words.txt");
        guessWord = allWords.randomWord();
        answerString = guessWord.getWord();
        System.out.println("Guess: " + answerString);
        exactMatchedCount = new int[10];
        partialMatchedCount = new int[10];
        partialMatches = new boolean[5];
        exactMatches = new boolean[5];
        guessCount = -1;
        gameOver = false;
        validation = true;
        won = false;
        newGame = true;
        notifyObservers();
    }

    public void setGuessString(String input){
        newGame = false;
        guessString = input;
        guessString = guessString.trim().toUpperCase();
        validateGuess();
    }

    private void resetMatches(){
        for (int i = 0; i < 5; i++) {
            partialMatches[i] = false;
            exactMatches[i] = false;
        }
        partialMatchedCount[guessCount] = 0;
        exactMatchedCount[guessCount] = 0;
    }

    private void validateGuess(){
        if (validation()){
            guessCount++;
            resetMatches();

            char[] cloneAnswerString = answerString.toCharArray();
            char[] guessStringClone = guessString.toCharArray();

            for (int i = 0; i < 5; i++) {
                if(cloneAnswerString[i] == guessStringClone[i] ){
                     exactMatches[i] = true;
                     exactMatchedCount[guessCount]++;
                     cloneAnswerString[i] = '*';
                     guessStringClone[i] = '^';
//                    System.out.println("Matched: " + guessString.charAt(i));
                }
            }

            for (int i = 0; i < 5; i++) {
               for (int j = 0; j < 5; j++) {
                   if(guessStringClone[i] == cloneAnswerString[j] ){
                       partialMatches[i] = true;
                       partialMatchedCount[guessCount]++;
                       cloneAnswerString[j] = '*';
//                       System.out.println("Partial Matched: " + guessString.charAt(i));
                       break;
                   }
               }
            }

            if(guessCount == 9 || exactMatchedCount[guessCount] == 5){
                gameOver();
            }


        }
        notifyObservers();
    }

    private void gameOver(){
        gameOver = true;
        if (exactMatchedCount[guessCount] == 5){
            won = true;
        }
    }

    private boolean validation(){
        if (guessString.length() != 5){
            validation = false;
            return false;
        }else {
            validation = true;
            return true;
        }
    }

    public int getExactMatches(){
        return exactMatchedCount[guessCount];
    }
    public int getPartialMatches(){
        return partialMatchedCount[guessCount];
    }
    public String getGuessString(){
        return guessString;
    }
    public int getGuessCountLeft(){
        return (10 - guessCount - 1);
    }
    public boolean getGameOver(){
        return gameOver;
    }
    public String getAnswer(){
        return answerString;
    }
    public boolean getValidation(){
        return validation;
    }
    public boolean getWon(){
        return won;
    }
    public boolean getNewGame(){
        return newGame;
    }


    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();
    // set the view observer
    public void addView(IView view) {
        views.add(view);
        // update the view to current state of the model
    }

    // notify the IView observer
    private void notifyObservers() {
        for (IView view : this.views) {
            view.updateView();
        }
    }
}