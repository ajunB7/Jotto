import java.awt.Color;

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
    private JButton showHintsButton = new JButton("Show Hints");
    private JButton giveUpButton = new JButton("Give Up");
    private JLabel gameStatusLabel;
    private JPanel gameStatus;

    GameView(JottoModel jModel){
        model = jModel;

        JPanel all = new JPanel();

        JPanel guessArea = new JPanel();
        JLabel guessAreaLabel = new JLabel("Guess a 5 letter word: ");
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
//        guessArea.add(Box.createRigidArea(new Dimension(10, 0)));
        guessArea.add(guessButton);
        guessArea.add(Box.createRigidArea(new Dimension(45,0)));
        guessArea.add(showHintsButton);
//        guessArea.add(Box.createRigidArea(new Dimension(10,0)));
        guessArea.add(giveUpButton);

//        guessArea.add(Box.createRigidArea(new Dimension(0,20)));

        gameStatus = new JPanel();
        gameStatus.setLayout(new BoxLayout(gameStatus, BoxLayout.X_AXIS));
//        gameStatus.add(guessArea);
        gameStatusLabel = new JLabel();
//        gameStatus.setBorder(loweredetched);
        gameStatus.add(Box.createRigidArea(new Dimension(120,20)));
        gameStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gameStatus.add(gameStatusLabel);
        gameStatus.add(Box.createRigidArea(new Dimension(120,20)));
        gameStatus.setBackground(Color.getHSBColor(0.212f,0.76f,1.0f));




        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.setGuessString(guessInput.getText());
            }
        });

        guessInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    model.setGuessString(guessInput.getText());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER){
                    String entered = guessInput.getText().toUpperCase();
                    if(entered.length() > 1) {
                        model.setAutoComplete(entered);
                    }
                    guessInput.setText(entered);
                }
            }
        });

        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newFrame = new JFrame();
                JOptionPane.showMessageDialog(newFrame, "The word was " + model.getAnswer() + " Try Again");
                model.init();
            }
        });

        showHintsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getShowHints()){
                    showHintsButton.setText("Show Hints");
                    model.setShowHints(false);
                }else{
                    showHintsButton.setText("Hide Hints");
                    model.setShowHints(true);
                }
            }
        });

        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));
        all.add(guessArea);
        all.add(Box.createRigidArea(new Dimension(0,10)));
        all.add(gameStatus);

        this.add(all);

    }

    public void updateStatus(){
        String text = "";
        if (model.getNewGame()){
            text = "Welcome to Jotto! - 10 Guesses Left!";
            gameStatusLabel.setText(text);
            gameStatus.setBackground(Color.getHSBColor(0.212f,0.76f,1.0f));
            guessInput.setText("");
            if(!(model.getLevelIsEasy())){
                showHintsButton.setVisible(false);
            }else{
                showHintsButton.setVisible(true);
            }
            System.out.println(model.getLevelIsEasy());
        }
        else if (model.getGameOver()){
            if (model.getWon()){
                text = "YOU WIN! - You guessed the correct Word";
                gameStatus.setBackground(Color.GREEN);
            }else {
                text = "Game Over! - The correct word was " + model.getAnswer();
                gameStatus.setBackground(Color.RED);
            }
            gameStatusLabel.setText(text);
        }
        else if (model.getValidation()){
            text = "You guessed " + model.getGuessString() + " - " + model.getGuessCountLeft() + " Guesses Left!";
            gameStatusLabel.setText(text);
            gameStatus.setBackground(Color.getHSBColor(0.212f,0.76f,1.0f));
            guessInput.setText("");
        } else if (!(model.getAutoCompleteStatus())) {
           text = model.getInvalidText();
            gameStatusLabel.setText(text);
            gameStatus.setBackground(Color.RED);
        }


    }



    // IView interface
    public void updateView() {
        updateStatus();

    }


}

