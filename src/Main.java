import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

public class Main{

       private static JottoModel model;

    public static void main(String[] args){
        JFrame frame = new JFrame("Jotto");


        model = new JottoModel();
        GameView gameView = new GameView(model);
        TableView tableView = new TableView(model);
        model.addView(gameView);
        model.addView(tableView);
        model.init();


        // create the window
        JPanel p = new JPanel(new BorderLayout());
        frame.getContentPane().add(p);
        p.add(gameView, BorderLayout.PAGE_START);
        p.add(tableView, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("New Game");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.init();
            }
        });
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);




        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }


}