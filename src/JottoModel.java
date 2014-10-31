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

    // all views of this model
    private ArrayList<IView> views = new ArrayList<IView>();
    // set the view observer
    public void addView(IView view) {
        views.add(view);
        // update the view to current state of the model
        notifyObservers();
    }

    // notify the IView observer
    private void notifyObservers() {
        for (IView view : this.views) {
            System.out.println("Model: notify View");
            view.updateView();
        }
    }





}