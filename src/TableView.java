import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.Component;
import javax.swing.table.*;
import java.util.Vector;

/**
 * Created by Ajun on 14-10-31.
 */
class TableView extends JPanel implements IView {
    private JTable table = new JTable();
    private JottoModel model;
    private JScrollPane scrollPane;
    private DefaultTableModel dtm;

    public TableView(JottoModel jModel) {
        this.model = jModel;
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        initTable();

        scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);
        this.add(tablePanel);
    }

    private void initTable(){
        table.setPreferredScrollableViewportSize(new Dimension(750, 200));
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
