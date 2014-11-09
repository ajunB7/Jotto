import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;


/**
 * Created by Ajun on 14-11-02.
 */
public class HintsView extends JPanel implements IView {
    private JottoModel model;
    private JScrollPane scroll;
    private JList list;
    private JPanel letterHints;
//    Wraps everything
    private JPanel all;
    private JLabel[] letters;
//    Border Around the list of words
    private JPanel hintList;
    private ArrayList<String> listOfWords;
    private JLabel yellow;
    private JLabel green;



    public HintsView(JottoModel jModel) {
        hintList = new JPanel();
        model = jModel;
        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(220, 240));
        list = new JList();

        all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.LINE_AXIS));

        letterHints = new JPanel();
        JPanel outLineHints = new JPanel();
       outLineHints.setLayout(new BoxLayout(outLineHints, BoxLayout.PAGE_AXIS));
       TitledBorder title;
       title = BorderFactory.createTitledBorder("Guesses");
       title.setTitleJustification(TitledBorder.CENTER);
       outLineHints.setBorder(title);
       letterHints.setLayout(new GridLayout(7, 4));
       letterHints.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
       createHints();

       title = BorderFactory.createTitledBorder("All Words");
        hintList.setBorder(title);

        hintList.add(scroll);
        outLineHints.add(letterHints);

        JPanel helpSummary = new JPanel();
        JPanel helpText = new JPanel();
        JPanel summaryText = new JPanel();
        helpText.setLayout(new BoxLayout(helpText, BoxLayout.PAGE_AXIS));
        summaryText.setLayout(new BoxLayout(summaryText, BoxLayout.Y_AXIS));

//        helpSummary.setLayout(new BoxLayout(helpSummary, BoxLayout.PAGE_AXIS));

         helpSummary.setLayout(new BorderLayout());

        JLabel red = new JLabel("Red = Already Guessed");
        red.setAlignmentX(Component.CENTER_ALIGNMENT);
        yellow = new JLabel("Yellow = Partially Correct");
        yellow.setAlignmentX(Component.CENTER_ALIGNMENT);
        green = new JLabel("Green = Exactly Correct");
        green.setAlignmentX(Component.CENTER_ALIGNMENT);


        title = BorderFactory.createTitledBorder("Help");
        helpText.setBorder(title);
        helpText.add(red);
        helpText.add(yellow);
        helpText.add(green);



        JLabel author = new JLabel("Ajunpreet Bambrah");
        author.setForeground(Color.GRAY);
        author.setAlignmentX(Component.CENTER_ALIGNMENT);

        summaryText.add(author);

        helpSummary.add(helpText, BorderLayout.NORTH);
        JPanel summary = new JPanel();
        summary.setLayout(new BoxLayout(summary, BoxLayout.PAGE_AXIS));
//        helpSummary.add(Box.createRigidArea(new Dimension(0, 100)));

        try{
            BufferedImage logo = ImageIO.read(new File("rsz_1ajunlogo.png"));

            JLabel pic = new JLabel(new ImageIcon(logo));
            pic.setAlignmentX(Component.CENTER_ALIGNMENT);
            summary.add(pic);

        }catch(IOException e){
            System.out.println(e.toString());
        }
        summary.add(summaryText);
        helpSummary.add(summary, BorderLayout.SOUTH);


        all.add(hintList);
        all.add(outLineHints);
        all.add(Box.createRigidArea(new Dimension(60,0)));
        all.add(helpSummary);

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
            letterHints.add(letters[i]);
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
        if (model.getShowHints()){
            green.setVisible(true);
            yellow.setVisible(true);
        }else {
            green.setVisible(false);
            yellow.setVisible(false);
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
        hintList.setBorder(title);
    }

    // IView interface
    public void updateView() {
        updateList();
        updateLetters();
    }
}
