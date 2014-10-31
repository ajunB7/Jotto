import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

public class Main{

    public static void main(String[] args){
        JFrame frame = new JFrame("Jotto");


        JottoModel model = new JottoModel();
        GameView gameView = new GameView(model);
        model.addView(gameView);


        // create the window
        JPanel p = new JPanel(new GridLayout(2,1));
        frame.getContentPane().add(p);
        p.add(gameView);


        frame.setPreferredSize(new Dimension(800,600));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}