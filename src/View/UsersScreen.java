package View;

import Control.*;
import Model.*;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class UsersScreen extends JPanel implements ModifyRecord, Observer {
    private UserTableModel tModel;
    private List<User> records;
    private TCPConnection connection;
    private JTable jTab;
    private Subject clientData;

    private JButton jbtAdd;
    private JButton jbtEdit;
    private JButton jbtDel;


    public UsersScreen(TCPConnection connection) {
        this.clientData= CacheClient.getInstance();
        clientData.registerObserver(this);
        this.connection = connection;
        this.records = CacheClient.getInstance().getUsers();
        setLayout(new BorderLayout());
        Container box = Box.createHorizontalBox();

        jbtAdd = new JButton("Добавить");
        jbtEdit = new JButton("Изменить");
        jbtDel = new JButton("Удалить");

        jbtAdd.addActionListener(event -> {
            addRecord();
        });

        jbtEdit.addActionListener(event -> {
            int index = jTab.getSelectedRow();
            if (index!=-1){
                editRecord(index);
            }else{
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
            }
        });
        jbtDel.addActionListener(event -> {
            int index = jTab.getSelectedRow();
            if(index!=-1){
                delRecord(index);
            }else{
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления");
            }
        });

        box.add(jbtAdd);
        box.add(jbtEdit);
        box.add(jbtDel);
        box.add(Box.createHorizontalStrut(10));


        add(box, BorderLayout.NORTH);

        tModel = new UserTableModel(records);
        jTab = new JTable(tModel);
        jTab.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(2).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTab.getColumnModel().getColumn(4).setPreferredWidth(85);
        jTab.getColumnModel().getColumn(5).setMinWidth(30);

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу
//        Устанавливаем размер прокручиваемой области
        jTab.setPreferredScrollableViewportSize(new Dimension(650, 250));
        add(new JScrollPane(jTab));
        setVisible(true);

    }
    @Override
    public void update() {
        tModel.fireTableDataChanged();
        jTab.updateUI();

    }


    @Override
    synchronized public boolean addRecord() {
        DialogUserScreen addUS = new DialogUserScreen("Добавить запись", new User());
        User user = addUS.getUser();
        if (addUS.isChange()) {
            connection.sendObject(new AddRecord<User>(user));
            return true;
        }
        return false;
    }

    @Override
    public boolean delRecord(int indexDel) {
        try {
            connection.sendObject(new DelRecord<User>(records.get(indexDel)));
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Записть отсутствует");
        }
        return false;
    }

    @Override
    public boolean editRecord(int indexEdit) {
        try {
            DialogUserScreen addUS = new DialogUserScreen("Изменить запись",records.get(indexEdit) );
            if (addUS.isChange()) {
                connection.sendObject(new EditRecord<User>(addUS.getUser()));
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Записть отсутствует");
        }
        return false;
    }
}
