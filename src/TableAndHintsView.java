import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.Vector;

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

        hints.setLayout(new BoxLayout(hints, BoxLayout.PAGE_AXIS));
        TitledBorder title;
        title = BorderFactory.createTitledBorder("Letters Guessed");
        title.setTitleJustification(TitledBorder.CENTER);
        hints.setBorder(title);
        createHints();


        all.add(tablePanel);
        all.add(hints);
        this.add(all);
    }
    private void createHints(){
        JPanel AD = new JPanel();
        JPanel EH = new JPanel();
        JPanel IL = new JPanel();
        JPanel MP = new JPanel();
        JPanel QT = new JPanel();
        JPanel UX = new JPanel();
        JPanel YZ = new JPanel();

        letters = new JLabel[26];
        letterSpace = 20;
        getLables();
        addLables(0, AD);
        addLables(4, EH);
        addLables(8, IL);
        addLables(12, MP);
        addLables(16, QT);
        addLables(20, UX);
        YZ.add(letters[24]);
        YZ.add(Box.createRigidArea(new Dimension(letterSpace,0)));
        YZ.add(letters[25]);
        hints.add(YZ);
        hints.add(Box.createRigidArea(new Dimension(150,0)));



    }



    private void getLables(){
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < 26; i++) {
            letters[i] = new JLabel(String.valueOf(alphabet[i]));
            letters[i].setOpaque(true);
            letters[i].setBackground(Color.LIGHT_GRAY);
        }

    }

    private void addLables(int start, JPanel panel){
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        for (int i = start; i < (start+4) ; i++) {
            panel.add(Box.createRigidArea(new Dimension(letterSpace,0)));
            panel.add(letters[i]);
        }
        hints.add(panel);
        hints.add(Box.createRigidArea(new Dimension(0,10)));
    }

    private void initTable(){
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
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
        else if (model.getValidation()) {
            dtm.addRow(new Object[]{model.getGuessString(), model.getExactMatches(), model.getPartialMatches()});
        }
    }

    // IView interface
    public void updateView() {
        updateTable();

    }

}
