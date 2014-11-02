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
 * Created by Ajun on 14-11-02.
 */
public class LettersHints extends JPanel implements IView {
    private JottoModel model;

   public LettersHints (JottoModel jModel) {
        model = jModel;
    }

    // IView interface
    public void updateView() {

    }
}
