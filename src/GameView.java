import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.awt.Component;

/**
 * Created by Ajun on 14-10-30.
 */
class GameView extends JPanel implements IView {
    private JottoModel model;
    private JTextField guessInput = new JTextField(10);
    private JButton guessButton = new JButton("Enter");
    private JButton hitsButton = new JButton("Show Hints");
    private JButton giveUpButton = new JButton("Give Up");

    GameView(JottoModel jModel){
        model = jModel;

        JPanel guessArea = new JPanel();
        JLabel guessAreaLabel = new JLabel("Guess a word of 5 letters: ");
        guessAreaLabel.setLabelFor(guessInput);
        guessArea.setLayout(new BoxLayout(guessArea, BoxLayout.LINE_AXIS));
//        guessArea.setAlignmentX(Component.LEFT_ALIGNMENT);
//        guessAreaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        guessInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        guessAreaLabel.setHorizontalAlignment(SwingConstants.LEFT);
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        guessArea.setBorder(loweredetched);
        guessArea.add(guessAreaLabel);
        guessArea.add(guessInput);
        guessArea.add(Box.createRigidArea(new Dimension(10, 0)));
        guessArea.add(guessButton);
        guessArea.add(Box.createRigidArea(new Dimension(180,0)));
        guessArea.add(hitsButton);
        guessArea.add(Box.createRigidArea(new Dimension(10,0)));
        guessArea.add(giveUpButton);


        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGuessString(guessInput.getText());
            }
        });

        this.add(guessArea);

    }



    // IView interface
    public void updateView() {
        System.out.println("GameView: updateView");

    }


}

