import java.util.ArrayList;
import java.util.Vector;


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
    private ArrayList<String> hintsWords;
    private Word guessWord;
    private String guessString;
    private String answerString;
    private String invalidText;
    private int[] exactMatchedCount;
    private int[] partialMatchedCount;
    private int guessCount;
    private boolean[] partialMatches;
    private boolean[] exactMatches;
    private boolean gameOver;
    private boolean validation;
    private boolean won;
    private boolean newGame;
    private boolean showHints;
    private int difficulty;
    private boolean hintsToggle;
    private ArrayList<Integer> exactSoFar;
    private ArrayList<Integer> partialSoFar;
    private ArrayList<Integer> guessedSoFar;
    private String correctSoFar;


    public JottoModel(){
        allWords = new WordList("words.txt");
        difficulty = 0;
    }


    public void init(){
        if (difficulty != 3) {
            guessWord = allWords.randomWord(difficulty);
        }else {
            guessWord = allWords.randomWord();
        }
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
        showHints = false;
        hintsToggle = false;
        invalidText = "";
        exactSoFar = new ArrayList<Integer>();
        partialSoFar = new ArrayList<Integer>();
        guessedSoFar = new ArrayList<Integer>();
        correctSoFar = "";
        hintsWords = allWords.getWords();


        notifyObservers();
    }

    public void setTargetString(String input){
        init();
        answerString = input.trim().toUpperCase();
        System.out.println("Guess is now set to: " + answerString );
    }

    public void setGuessString(String input){
        newGame = false;
        guessString = input;
        guessString = guessString.trim().toUpperCase();
        validateGuess();
    }

    public void setDifficulty(int diff){
        difficulty = diff;
        init();
    }

    public void setShowHints(boolean status){
        showHints = status;
        hintsToggle = true;
        notifyObservers();
        hintsToggle = false;

    }

    private void resetMatches(){
        for (int i = 0; i < NUM_LETTERS; i++) {
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

            for (int i = 0; i < NUM_LETTERS; i++) {
                guessedSoFar.add(guessStringClone[i]-65);
            }

            for (int i = 0; i < NUM_LETTERS; i++) {
                if(cloneAnswerString[i] == guessStringClone[i] ){
                    exactMatches[i] = true;
                    exactMatchedCount[guessCount]++;
                    correctSoFar+= guessStringClone[i];
                    exactSoFar.add((int) guessStringClone[i] - 65);
                    cloneAnswerString[i] = '*';
                    guessStringClone[i] = '^';

//                    System.out.println("Matched: " + guessString.charAt(i));
                }
            }

            for (int i = 0; i < NUM_LETTERS; i++) {
               for (int j = 0; j < NUM_LETTERS; j++) {
                   if(guessStringClone[i] == cloneAnswerString[j] ){
                       partialMatches[i] = true;
                       partialMatchedCount[guessCount]++;
                       correctSoFar+= guessStringClone[i];
                       partialSoFar.add((int)guessStringClone[i]-65);
                       cloneAnswerString[j] = '*';

                       break;
                   }
               }
            }

            if(guessCount == 9 || exactMatchedCount[guessCount] == NUM_LETTERS){
                gameOver();
            }else{
                updateWordsList();
            }


        }
        notifyObservers();
    }

    private void updateWordsList(){
       hintsWords = allWords.wordLeft(correctSoFar.toCharArray(), hintsWords);
    }

    private void gameOver(){
        gameOver = true;
        if (exactMatchedCount[guessCount] == NUM_LETTERS){
            won = true;
        }
    }

    private boolean validation(){
        if (guessString.length() != NUM_LETTERS){
            validation = false;
            invalidText = "Invalid Input - Word must enter a valid 5 letter word";
        }else if (!(allWords.contains(guessString))) {
            validation = false;
            invalidText = "You must enter a valid dictionary Word";
        } else {
            validation = true;
        }
        return validation;
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
    public boolean getShowHints(){
        return showHints;
    }
    public boolean getChangedHints(){
        return hintsToggle;
    }
    public ArrayList<Integer> getExactSoFar(){
        return exactSoFar;
    }
    public ArrayList<Integer> getPartialSoFar(){
        return partialSoFar;
    }
    public ArrayList<Integer> getGuessedSoFar(){
        return guessedSoFar;
    }
    public String getInvalidText(){
        return invalidText;
    }
    public ArrayList<String> getHintsWords(){
        return hintsWords;
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