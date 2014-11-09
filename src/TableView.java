import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;


/**
 * Created by Ajun on 14-10-31.
 */
class TableView extends JPanel implements IView {
    private JTable table = new JTable();
    private JottoModel model;
    private JScrollPane scrollPane;
    private DefaultTableModel dtm;
    private JPanel all;
    private static int tableWidth = 600;
    private static int tableHeight = 160;

    public TableView(JottoModel jModel) {
        this.model = jModel;
        all = new JPanel();

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));

        initTable();

        scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane);


        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.LINE_AXIS));


        all.add(tablePanel);
        this.add(all);
    }

    private void initTable(){
        table.setPreferredScrollableViewportSize(new Dimension(tableWidth, tableHeight));
        table.setFillsViewportHeight(true);

        String[] myDataColumnNames = {"Words", "Exact" , "Partial"};
        dtm = new DefaultTableModel(0, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        dtm.setColumnIdentifiers(myDataColumnNames);

        table.setModel(dtm);
    }

    public void updateTable(){
        if(model.getWindowChange()){
            int size = model.getDimension().height - 600 + tableHeight;
            if(size > 160) {
                table.setPreferredScrollableViewportSize(new Dimension(tableWidth, size));
                table.setFillsViewportHeight(true);
                revalidate();
            }
        }
        else if (model.getNewGame()){
            for (int i = (dtm.getRowCount() - 1); i > -1; i--) {
                dtm.removeRow(i);
            }
        }
        else if (model.getValidation() && !model.getChangedHints() && !model.getAutoCompleteStatus()) {
            dtm.addRow(new Object[]{model.getGuessString(), model.getExactMatches(), model.getPartialMatches()});
        }


        if (model.getGameOver()){
            String text = "";
            if (model.getWon()){
                text = "YOU WIN! - Play Again?";
            }else {
                text = "You Lose! - The correct word was " + model.getAnswer() + " - Play Again?";
            }
            JFrame newFrame = new JFrame();
            String title = "Game Over!";
            int choose = JOptionPane.showConfirmDialog(newFrame, text, title, JOptionPane.YES_NO_OPTION);
            if(choose == JOptionPane.YES_OPTION){
                model.init();
            }else{
                System.exit(0);
            }
        }
    }

    // IView interface
    public void updateView() {
        updateTable();

    }

}
