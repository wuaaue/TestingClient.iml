package View;

import Control.CacheClient;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class DialogSelectionUserScreen extends JDialog implements ActionListener {
    private List<User> records;
    private List<User> users;
    private JLabel testLabel;
    private TestUserTableModel tModelUser;
    private JTable jTabUser;
    private JButton jbtclose;
    private JButton jbtsave;
    private boolean change = false;

    public DialogSelectionUserScreen(String title) {

        this.records = CacheClient.getInstance().getUsers();
        users = new ArrayList<>();
        setTitle(title);
        setModal(true);
        setSize(520, 350);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contents = new JPanel();
        contents.setLayout(new FlowLayout(FlowLayout.LEFT));
        testLabel = new JLabel("Список пользователей:");
        contents.add(testLabel);
        tModelUser = new TestUserTableModel(records);
        jTabUser = new JTable(tModelUser);
        jTabUser.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTabUser.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTabUser.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTabUser.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTabUser.getColumnModel().getColumn(3).setMinWidth(150);

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу
        JScrollPane jscrlp1 = new JScrollPane(jTabUser);
//        Устанавливаем размер прокручиваемой области
        jTabUser.setPreferredScrollableViewportSize(new Dimension(490, 200));
        contents.add(jscrlp1);
        add(contents);
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
//        add(container);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comStr = e.getActionCommand();
        switch (comStr) {
            case "Save":
                for (int i = 0; i < jTabUser.getSelectedRows().length; i++) {
                    users.add(records.get(jTabUser.getSelectedRows()[i]));
                }
                if (!users.isEmpty()) {
                    change = true;
                }

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



    public List<User> getUsers() {
        return users;
    }

    public boolean isChange() {
        return change;
    }
}
