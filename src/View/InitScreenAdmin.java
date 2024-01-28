package View;

import Model.TCPConnection;

import javax.swing.*;

public class InitScreenAdmin extends JFrame {
    private JPanel jPanelTest;
    private JPanel jPanelUser;
    private JPanel jPanelQuestion;

    public InitScreenAdmin(TCPConnection connection){
        setTitle("Проверка знаний. Приветствую, админ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane jtp = new JTabbedPane();

        jPanelTest = new TestsScreen(connection);
        jPanelUser = new UsersScreen(connection);
        jPanelQuestion = new QuestionScreen(connection);

        jtp.addTab("Тесты", jPanelTest);
        jtp.addTab("Пользователи", jPanelUser);
        jtp.addTab("Вопросы", jPanelQuestion);
        add(jtp);
        pack();
        setVisible(true);
    }
}
