import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Ajun on 14-11-02.
 */
public class HintsView extends JPanel implements IView {
    private JottoModel model;
    private JScrollPane scroll;
    private JList list;
    private JPanel hints;
    private JPanel all;
    private JLabel[] letters;
    private JPanel hintWords;
    private ArrayList<String> listOfWords;



    public HintsView(JottoModel jModel) {
        hintWords = new JPanel();
        model = jModel;
        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(150, 250));
        list = new JList();

        all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.LINE_AXIS));

        hints = new JPanel();
        JPanel outLineHints = new JPanel();
       outLineHints.setLayout(new BoxLayout(outLineHints, BoxLayout.PAGE_AXIS));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Guesses");
       title.setTitleJustification(TitledBorder.CENTER);
       outLineHints.setBorder(title);
       hints.setLayout(new GridLayout(7,4));
       hints.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
       createHints();

       title = BorderFactory.createTitledBorder("All Words");
        hintWords.setBorder(title);


        hintWords.add(scroll);
        outLineHints.add(hints);
        all.add(hintWords);
        all.add(outLineHints);

        this.add(all);

    }

    private void createHints(){
        letters = new JLabel[26];
        getLables();
    }



    private void getLables(){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < alphabet.length; i++) {
            letters[i] = new JLabel(String.valueOf(alphabet[i]));
            letters[i].setOpaque(true);
            letters[i].setBackground(Color.LIGHT_GRAY);
            letters[i].setFont(letters[i].getFont().deriveFont(26f));
            hints.add(letters[i]);
        }
    }

    public void updateLetters(){
        ArrayList<Integer> exact = model.getExactSoFar();
        ArrayList<Integer> partial = model.getPartialSoFar();
        ArrayList<Integer> any = model.getGuessedSoFar();
        if(model.getNewGame()){
            for (int i = 0; i < letters.length; i++) {
                letters[i].setBackground(Color.LIGHT_GRAY);
            }
        } else {
            for (int i : any) {
                letters[i].setBackground(Color.RED);
            }
            if (model.getShowHints()) {
                for (int i : partial) {
                    letters[i].setBackground(Color.YELLOW);
                }


                for (int i : exact) {
                    letters[i].setBackground(Color.GREEN);
                }
            }

        }


    }

    public void updateList(){
        DefaultListModel listModel = new DefaultListModel();

        if (model.getAutoCompleteStatus()){
            for (String word : model.getAutoComplete()) {
                listModel.addElement(word);
            }
            list.setModel(listModel);
            scroll.setViewportView(list);
        }else if(model.getShowHints() || model.getNewGame()) {
            listOfWords = model.getHintsWords();
            model.setListUsed(listOfWords);
            for (String word : listOfWords) {
                listModel.addElement(word);
            }
            list.setModel(listModel);
            scroll.setViewportView(list);
        }else{
            model.setListUsed(listOfWords);
            for (String word : listOfWords) {
                listModel.addElement(word);
            }
            list.setModel(listModel);
            scroll.setViewportView(list);
        }
        TitledBorder title;
        if(model.getShowHints()){
            title = BorderFactory.createTitledBorder("Possible Words");
        }else{
            title = BorderFactory.createTitledBorder("All Words");
        }
        title.setTitleJustification(TitledBorder.CENTER);
        hintWords.setBorder(title);
    }

    // IView interface
    public void updateView() {
        updateList();
        updateLetters();
    }
}
