package View;

import Control.CacheClient;
import Model.TCPConnection;
import Model.Test;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class InitScreenUser extends JFrame {
    private User user;
    private List<Test> tests;
    private TestTableModel tableModel;
    private JTable jTable;
    private JScrollPane scrollPane;
    private JButton jbtClose;
    private JButton jbtSave;
    private TCPConnection connection;

    public InitScreenUser(User user, TCPConnection connection) {
        this.user=user;
        this.connection=connection;
        setTitle("Проверка знаний. Привествую, пользователь");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tests=getTests();
        tableModel = new TestTableModel(tests);
        jTable = new JTable(tableModel);

        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTable.getColumnModel().getColumn(2).setMinWidth(520);

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу
//        Устанавливаем размер прокручиваемой области
        jTable.setPreferredScrollableViewportSize(new Dimension(650, 250));
        scrollPane = new JScrollPane(jTable);

        add(scrollPane);

        addButton();
        pack();
        setVisible(true);
    }
    private List<Test> getTests(){
        List<Test> userTests = new ArrayList<>();
        for (Iterator<Test> testIterator = CacheClient.getInstance().getTests().iterator();testIterator.hasNext();){
            Test test = testIterator.next();
            for (Iterator<User> userIterator = test.getUsers().iterator();userIterator.hasNext();){
                User user = userIterator.next();
                if (this.user.getId().equals(user.getId()))
                {
                    userTests.add(test);
                }
            }
        }


        return userTests;
    }

    private void addButton() {
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        jbtSave = new JButton("OK");
        jbtClose = new JButton("Отмена");

        jbtSave.addActionListener(event -> {
            saveRecord();
            setVisible(false);
            this.dispose();
        });
        jbtClose.addActionListener(event -> {
            setVisible(false);
            this.dispose();
        });
        grid.add(jbtSave);
        grid.add(jbtClose);
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        flow.add(grid);
        add(flow, BorderLayout.SOUTH);
    }
    private void saveRecord() {
        int index = jTable.getSelectedRow();
        if (index != -1) {
            new ChoiceQuestionCardScreen(tests.get(index),connection);
        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выбрать строку для продолжения");
        }


    }
}
