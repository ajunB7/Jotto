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
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Ajun on 14-11-02.
 */
public class WordsView extends JPanel implements IView {
    private JottoModel model;
    private JScrollPane scroll;
    private JList list;

   public WordsView(JottoModel jModel) {
       JPanel hintWords = new JPanel();
        model = jModel;
        scroll = new JScrollPane();
        scroll.setPreferredSize(new Dimension(150, 200));
        list = new JList();

        hintWords.add(scroll);
        this.add(hintWords);

    }

    public void updateList(){
        DefaultListModel listModel = new DefaultListModel();
        for(String word: model.getHintsWords()){
            listModel.addElement(word);
        }
        list.setModel(listModel);
        scroll.setViewportView(list);

    }

    // IView interface
    public void updateView() {
        updateList();
    }
}
