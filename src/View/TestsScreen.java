package View;

import Control.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestsScreen extends JPanel implements ModifyRecord, Observer {
    private List<Test> records;
    private TestTableModel tModel;
    private JTable jTab;
    private JScrollPane jSP;
    private TCPConnection connection;
    private Subject clientData;


    private JButton jbtAdd;
    private JButton jbtEdit;
    private JButton jbtDel;

    public TestsScreen(TCPConnection connection) {
        this.clientData = CacheClient.getInstance();
        clientData.registerObserver(this);

        this.connection = connection;
        this.records = CacheClient.getInstance().getTests();
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
            if (index != -1) {
                editRecord(jTab.convertRowIndexToModel(index));
            } else {
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
            }
        });
        jbtDel.addActionListener(event -> {
            int index = jTab.getSelectedRow();
            if (index != -1) {
                delRecord(jTab.convertRowIndexToModel(index));
            } else {
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления");
            }
        });


        box.add(jbtAdd);
        box.add(jbtEdit);
        box.add(jbtDel);

        add(box, BorderLayout.NORTH);
        tModel = new TestTableModel(records);
        jTab = new JTable(tModel);

        jTab.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setPreferredWidth(100);
        jTab.getColumnModel().getColumn(2).setMinWidth(520);

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу
//        Устанавливаем размер прокручиваемой области
        jTab.setPreferredScrollableViewportSize(new Dimension(650, 250));
        jSP = new JScrollPane(jTab);

        add(jSP);
        setVisible(true);
    }

    @Override
    public void update() {
        tModel.fireTableDataChanged();
    }

    @Override
    synchronized public boolean addRecord() {
        DialogTestScreen addTS = new DialogTestScreen("Добавить запись", new Test());
        Test test = addTS.getTest();
        if (addTS.isChange()) {
            connection.sendObject(new AddRecord<Test>(test));
            return true;
        }
        return false;
    }

    @Override
    synchronized public boolean delRecord(int indexDel) {
        try {
            connection.sendObject(new DelRecord<Test>(records.get(indexDel)));
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Записть отсутствует");
        }
        return false;
    }

    @Override
    synchronized public boolean editRecord(int index) {

            try {
                DialogTestScreen addTS = new DialogTestScreen("Изменить запись",records.get(index) );
                if (addTS.isChange()) {
                    connection.sendObject(new EditRecord<Test>(addTS.getTest()));
                    return true;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Записть отсутствует");
            }

            return false;

    }

}
