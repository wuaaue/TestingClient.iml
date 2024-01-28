package View;

import Control.CacheClient;
import Control.LoadFile;
import Control.Observer;
import Control.Subject;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;


public class QuestionScreen extends JPanel implements ModifyRecord, Observer {
    private List<Question> records;
    private TCPConnection connection;
    private QuestionTableModel tModel;

    private JTable jTab;
    private Subject clientData;

    private JButton jbtAdd;
    private JButton jbtEdit;
    private JButton jbtDel;

    public QuestionScreen(TCPConnection connection) {
        this.clientData = CacheClient.getInstance();
        clientData.registerObserver(this);

        this.records = CacheClient.getInstance().getQuestions();
        this.connection = connection;
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
                editRecord(index);
            } else {
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования");
            }
        });
        jbtDel.addActionListener(event -> {
            int index = jTab.getSelectedRow();
            if (index != -1) {
                delRecord(index);
            } else {
                JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления");
            }
        });

        box.add(jbtAdd);
        box.add(jbtEdit);
        box.add(jbtDel);
        box.add(Box.createHorizontalStrut(10));

        add(box, BorderLayout.NORTH);

        tModel = new QuestionTableModel(records);
        jTab = new JTable(tModel);
        jTab.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTab.getColumnModel().getColumn(1).setMinWidth(610);

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
        DialogQuestionScreen addQS = new DialogQuestionScreen("Добавить запись", new Question());
        Question question = addQS.getRecord();
        if (addQS.isChange()) {
            connection.sendObject(new AddRecord<Question>(question));
            return true;
        }
        return false;
    }

    @Override
    synchronized public boolean delRecord(int indexDel) {
        try {
            connection.sendObject(new DelRecord<Question>(records.get(indexDel)));
            return true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Записть отсутствует");
        }
        return false;
    }

    @Override
    synchronized public boolean editRecord(int indexEdit) {
        try {
            DialogQuestionScreen addQS = new DialogQuestionScreen("Изменить запись", records.get(indexEdit));
            if (addQS.isChange()) {
                connection.sendObject(new EditRecord<Question>(addQS.getRecord()));
                return true;
            } else
                return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Записть отсутствует");
        }
        return false;
    }
}