package View;

import Control.CacheClient;
import Model.TCPConnection;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;


public class DialogInitScreen extends JDialog {
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private User user;
    private boolean change = false;
    private boolean admin = false;
    private JButton jbtClose;
    private JButton jbtSave;
    private List<User> records;
    private TCPConnection connection;

    public DialogInitScreen(TCPConnection connection, Frame frame) {
        super(frame, "Идентификация пользователя", true);
        this.connection = connection;
        this.records = CacheClient.getInstance().getUsers();
        setSize(290, 130);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addComponent();
        addButton(this);
        setVisible(true);
    }

    private void addComponent() {
        JPanel contents = new JPanel();
        contents.setLayout(new GridLayout(2, 0, 0, 5));
        loginLabel = new JLabel("Логин:");
        loginField = new JTextField("", 15);
        passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField("", 15);
        contents.add(loginLabel);
        contents.add(loginField);
        contents.add(passwordLabel);
        contents.add(passwordField);
        add(contents);
    }

    private void addButton(JDialog frame) {
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        jbtSave = new JButton("OK");
        jbtClose = new JButton("Отмена");
        frame.getRootPane().setDefaultButton(jbtSave);
        jbtSave.addActionListener(event -> {
            saveRecord();
            setVisible(false);
            dispose();
        });
        jbtClose.addActionListener(event -> {
            setVisible(false);
            dispose();

        });
        grid.add(jbtSave);
        grid.add(jbtClose);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);
        add(flow, BorderLayout.SOUTH);
    }

    private void saveRecord() {
        if (getLoginField().equalsIgnoreCase("Admin") && new String(getPasswordField()).equals("123456")) {
            change = true;
            admin = true;
        } else {
            for (Iterator<User> iterator = records.iterator(); iterator.hasNext(); ) {
                User user = iterator.next();
                if (user.getLogin().equals(getLoginField()) && user.getPassword().equals(new String(getPasswordField()))) {
                    change = true;
                    this.user = user;
                    if (user.isAdminStatus())
                        admin = true;
                    break;
                }
            }
        }
    }

    public boolean isChange() {
        return change;
    }

    public boolean isAdmin() {
        return admin;
    }

    public User getUser() {
        return user;
    }

    private String getLoginField() {
        return loginField.getText();
    }

    private char[] getPasswordField() {
        return passwordField.getPassword();
    }

}
