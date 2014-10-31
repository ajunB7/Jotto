import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;

public class Main{

    public static void main(String[] args){
        JFrame frame = new JFrame("Jotto");


        JottoModel model = new JottoModel();
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


        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}