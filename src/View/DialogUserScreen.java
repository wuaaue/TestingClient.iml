package View;

import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogUserScreen extends JDialog implements ActionListener {
    private JLabel lastnameLabel;
    private JLabel firstnameLabel;
    private JLabel patronymicLabel;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private TextField lastnameField;
    private TextField firstnameField;
    private TextField patronymicField;
    private TextField loginField;
    private JPasswordField passwordField;

    private JButton jbtclose;
    private JButton jbtsave;
    private User user;
    private JCheckBox jchbadminStatus;
    private boolean change = false;

    public DialogUserScreen(String title, User u) {
        this.user = u;
        setTitle(title);
        setModal(true);
        setSize(290, 260);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(6, 2, 0, 5));

        lastnameLabel = new JLabel("Фамилия:");
        lastnameField = new TextField(user.getLastname(), 15);

        firstnameLabel = new JLabel("Имя:");
        firstnameField = new TextField(user.getFirstname(), 15);

        patronymicLabel = new JLabel("Отчество:");
        patronymicField = new TextField(user.getPatronymic(), 15);

        loginLabel = new JLabel("Логин:");
        loginField = new TextField(user.getLogin(), 15);

        passwordLabel = new JLabel("Пароль");
        passwordField = new JPasswordField(user.getPassword(), 15);
        jchbadminStatus = new JCheckBox("Админ", user.isAdminStatus());

        contents.add(lastnameLabel);
        contents.add(lastnameField);
        contents.add(firstnameLabel);
        contents.add(firstnameField);
        contents.add(patronymicLabel);
        contents.add(patronymicField);
        contents.add(loginLabel);
        contents.add(loginField);
        contents.add(passwordLabel);
        contents.add(passwordField);
        contents.add(jchbadminStatus);
        JPanel field = new JPanel(new FlowLayout(FlowLayout.LEADING));
        field.add(contents);

        add(field);

        // Создание панели с табличным расположением
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        // Добавление кнопок в панель

        jbtsave = new JButton("OK");
        jbtsave.setActionCommand("Save");
        jbtsave.addActionListener(this);

        jbtclose = new JButton("Отмена");
        jbtclose.setActionCommand("Close");
        jbtclose.addActionListener(this);

        grid.add(jbtsave);
        grid.add(jbtclose);


        // Создание панели с последовательным расположением компонентов и
        // выравниванием по правому краю
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flow.add(grid);

        // Размещение панели с кнопками внизу справа

        add(flow, BorderLayout.SOUTH);
        setVisible(true);

    }
    private void saveRecord(){
        if(user.getId()==null)
            user.setId(Math.round(Math.random() * 1000 + System.currentTimeMillis()));
        user.setLastname(lastnameField.getText());
        user.setFirstname(firstnameField.getText());
        user.setPatronymic(patronymicField.getText());
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        user.setAdminStatus(jchbadminStatus.isSelected());
        change = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comStr = e.getActionCommand();
        switch (comStr) {
            case "Save":
                saveRecord();
                setVisible(false);
                dispose();
                break;
            case "Close":
                setVisible(false);
                dispose();
                break;
            default:
                break;
        }

    }

    public User getUser() {
        return user;
    }

    public boolean isChange() {
        return change;
    }
}
