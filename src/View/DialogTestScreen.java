package View;

import Control.CacheClient;
import Model.Question;
import Model.QuestionCard;
import Model.Test;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DialogTestScreen extends JDialog implements ActionListener {
    private JLabel testLabel;
    //private JLabel dateLabel;
    private JLabel userLabel;
    private JTextArea testField;
    private TextField dateField;
    private TestUserTableModel tModelUser;
    private JTable jTabUser;
    private Test test;
    private List<QuestionCard> questionCards;//список наборов вопросов
    private List<User> users;//список пользователей
    private JButton jbtclose;
    private JButton jbtsave;
    private JButton jbtGeneration;
    private BoundedRangeModel model;
    private JProgressBar pbUIManager;
    private boolean change = false;

    public DialogTestScreen(String title, Test test) {
        this.test = test;
        users = new ArrayList<>();//выделение памяти для коллекции пользователей
        questionCards = new ArrayList<>();//выделение памяти для коллекции набора вопросов
        for (Iterator<User> iterator = test.getUsers().iterator(); iterator.hasNext(); ) {
            users.add(iterator.next());
        }
        for (Iterator<QuestionCard> iterator = test.getQuestionCards().iterator(); iterator.hasNext(); ) {
            questionCards.add(iterator.next());
        }
        setTitle(title);
        setModal(true);
        setSize(520, 450);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contents = new JPanel();
        contents.setLayout(new FlowLayout(FlowLayout.LEFT));
        testLabel = new JLabel("Название теста:");

        testField = new JTextArea(test.getDescription(), 3, 36);
        contents.add(testLabel);
        contents.add(new JScrollPane(testField));

        userLabel = new JLabel("Тестируемые пользователи:");
        contents.add(userLabel);
        Container box = Box.createHorizontalBox();

        JButton jbtAdd = new JButton("Добавить");
        jbtAdd.setActionCommand("Добавить");
        jbtAdd.addActionListener(this);
        box.add(jbtAdd);

        JButton jbtDel = new JButton("Удалить");
        jbtDel.setActionCommand("Удалить");
        jbtDel.addActionListener(this);
        box.add(jbtDel);
        contents.add(box);

        tModelUser = new TestUserTableModel(users);
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
        jbtGeneration = new JButton("Генерация билетов");
        jbtGeneration.setActionCommand("Generation");
        jbtGeneration.addActionListener(this);
        contents.add(jbtGeneration);

        // Создание стандартной модели
        model = new DefaultBoundedRangeModel(5, 0, 0, 100);
        // Настройка параметрой UI-представителя
        UIManager.put("ProgressBar.cellSpacing", new Integer(2));
        UIManager.put("ProgressBar.cellLength", new Integer(6));
        // Создание индикатора
        pbUIManager = new JProgressBar(model);
        pbUIManager.setVisible(false);
        contents.add(pbUIManager);

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
            case "Добавить":
                addUserScreen();
                break;
            case "Удалить":
                delUserScreen();
                break;
            case "Save":
                saveRecord();
                setVisible(false);
                dispose();
                break;
            case "Close":
                setVisible(false);
                dispose();
                break;
            case "Generation":
                pbUIManager.setVisible(true);
                generationCard();
                break;
            default:
                break;
        }

    }

    private void saveRecord() {
        if (test.getId() == null)
            test.setId(Math.round(Math.random() * 1000 + System.currentTimeMillis()));
        test.setDescription(testField.getText());
        test.setUsers(users);
        test.setQuestionCards(questionCards);
        change = true;
    }

    public Test getTest() {
        return test;
    }

    public boolean isChange() {
        return change;
    }

    private void addUserScreen() {
        DialogSelectionUserScreen addSUS = new DialogSelectionUserScreen("Добавить запись");
        List<User> users = addSUS.getUsers();
        if (addSUS.isChange()) {
            for (User user : users)
                this.users.add(user);
            tModelUser.fireTableDataChanged();
        }
    }


    private void delUserScreen() {
        int indexDel = jTabUser.getSelectedRow();
        this.users.remove(indexDel);
        tModelUser.fireTableDataChanged();
    }


    private void generationCard() {
        this.questionCards = new ArrayList<>();

        // Проверка завершения процесса
        while (model.getValue() < model.getMaximum()) {
            try {

                // Увеличение текущего значение
                model.setValue(model.getValue() + 10);
                QuestionCard questionCard = createQuestionCard();
                this.questionCards.add(questionCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private QuestionCard createQuestionCard() {
        List<Question> questions = CacheClient.getInstance().getQuestions();
        QuestionCard questionCard = new QuestionCard();
        List<Question> questionList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            try {
                int index = (int) (Math.random() * questions.size());
                Question question = questions.get(index);
                questionList.add(question);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        questionCard.setQuestions(questionList);

        return questionCard;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DialogTestScreen("", new Test());
        });
    }
}

