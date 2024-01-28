package View;

import Model.QuestionCard;
import Model.TCPConnection;
import Model.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChoiceQuestionCardScreen extends JFrame implements ActionListener {
    private TCPConnection connection;
    private List<QuestionCard> questionCards;
    private Button[] buttons;


    public ChoiceQuestionCardScreen(Test test, TCPConnection connection) {
        this.connection = connection;
        questionCards = test.getQuestionCards();
        setTitle("Проверка заний");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pl = new JPanel();
        GridLayout gl = new GridLayout(5, 5, 5, 5);
        pl.setLayout(gl);
        buttons = new Button[questionCards.size()];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button("Билет N" + (i + 1));
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(String.valueOf(i));
            pl.add(buttons[i]);
        }
        add(pl);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        QuestionCard questionCard =questionCards.get(Integer.valueOf(str));
        new StartTestScreen(questionCard);
        setVisible(false);
        dispose();
    }
}
