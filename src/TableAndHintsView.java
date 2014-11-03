import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;


/**
 * Created by Ajun on 14-10-31.
 */
class TableAndHintsView extends JPanel implements IView {
    private JTable table = new JTable();
    private JottoModel model;
    private JScrollPane scrollPane;
    private DefaultTableModel dtm;
    private JPanel all;
    private JLabel[] letters;
    JPanel hints;
    private int letterSpace;

    public TableAndHintsView(JottoModel jModel) {
        this.model = jModel;
        all = new JPanel();
        hints = new JPanel();

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));

        initTable();

        scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);

        JPanel outLineHints = new JPanel();
        hints.setLayout(new GridLayout(7,4));
        hints.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        createHints();

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Letters Guessed");
        title.setTitleJustification(TitledBorder.CENTER);
        outLineHints.setBorder(title);
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));


        outLineHints.add(hints);
        all.add(tablePanel);
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
            letters[i].setFont(letters[i].getFont().deriveFont(25f));
            hints.add(letters[i]);
        }
    }

    private void initTable(){
        table.setPreferredScrollableViewportSize(new Dimension(600, 235));
        table.setFillsViewportHeight(true);

        String[] myDataColumnNames = {"Words", "Exact" , "Partial"};
        dtm = new DefaultTableModel(0, 0);
        dtm.setColumnIdentifiers(myDataColumnNames);
        table.setModel(dtm);
    }

    public void updateTable(){
        if (model.getNewGame()){
            for (int i = (dtm.getRowCount() - 1); i > -1; i--) {
                dtm.removeRow(i);
            }
        }
        else if (model.getValidation() && !model.getChangedHints()) {
            dtm.addRow(new Object[]{model.getGuessString(), model.getExactMatches(), model.getPartialMatches()});
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

    // IView interface
    public void updateView() {
        updateTable();
        updateLetters();

    }

}
