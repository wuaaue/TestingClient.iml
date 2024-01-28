package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;


public class StartTestScreen extends JFrame {
    private QuestionTableModel tModelQuestion;
    private JTable jTabQuestion;
    private List<Question> records;

    private List<Question> recordsAnswer;

    private JTable jTab;
    private JScrollPane jSP;
    private ModifyRecord modifyRecord;
    //    private VariantTableModel tModel;
    private AbstractTableModel tModel;
    private JTextField variantField;
    private JLabel answerLabel;

    private JButton jbtUp;
    private JButton jbtDown;

    private JButton jbtClose;
    private JButton jbtSave;

    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;


    public StartTestScreen(QuestionCard questionCard) {
        this.records = questionCard.getQuestions();
        this.recordsAnswer = clearAnswer(records);
        setTitle("Проверка заний");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(670, 400);


        tModelQuestion = new QuestionTableModel(records);
        jTabQuestion = new JTable(tModelQuestion);
        jTabQuestion.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        jTabQuestion.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTabQuestion.getColumnModel().getColumn(1).setMinWidth(610);

//        Создаем панель прокрутки и включаем в нее состав нашу таблицу
//        Устанавливаем размер прокручиваемой области
        jTabQuestion.setPreferredScrollableViewportSize(new Dimension(650, 165));
        jPanel1 = new JPanel();
        jPanel1.add(new JScrollPane(jTabQuestion));
        add(jPanel1, BorderLayout.BEFORE_FIRST_LINE);

        jPanel2 = new JPanel();
        add(jPanel2, BorderLayout.WEST);

        jTabQuestion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = jTabQuestion.getSelectedRow();
//              System.out.println(records.get(index).toString());
                if (modifyRecord != null && modifyRecord.getClass() == VariantWithInputAnswer.class) {
                    VariantWithInputAnswer variant = (VariantWithInputAnswer) modifyRecord;
                    variant.setVariant(new Variant(variantField.getText(), true));
                }
                modifyRecord = recordsAnswer.get(index).getVatiants();
                if (modifyRecord != null) {
                    clearComponent();
                    addComponent();
                    repaint();
                    revalidate();
                } else {
                    clearComponent();
                    repaint();
                    revalidate();
                }
            }
        });

        // Создание панели с табличным расположением
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        // Добавление кнопок в панель

        jbtSave = new JButton("OK");
        jbtClose = new JButton("Отмена");

        jbtSave.addActionListener(event -> {
            checkAnswer();
            setVisible(false);
            dispose();
            System.exit(0);
        });
        jbtClose.addActionListener(event -> {
            setVisible(false);
            dispose();
            System.exit(0);

        });

        grid.add(jbtSave);
        grid.add(jbtClose);


        // Создание панели с последовательным расположением компонентов и
        // выравниванием по правому краю
        jPanel3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        jPanel3.add(grid);

        // Размещение панели с кнопками внизу справа


        add(jPanel3, BorderLayout.SOUTH);

        setVisible(true);

    }

    private void clearComponent() {
        if (jPanel2.getComponentZOrder(jSP) != -1)
            jPanel2.remove(jSP);
        if (jPanel2.getComponentZOrder(answerLabel) != -1)
            jPanel2.remove(answerLabel);
        if (jPanel2.getComponentZOrder(variantField) != -1)
            jPanel2.remove(variantField);
        if (jPanel2.getComponentZOrder(jbtDown) != -1)
            jPanel2.remove(jbtDown);
        if (jPanel2.getComponentZOrder(jbtUp) != -1)
            jPanel2.remove(jbtUp);

    }

    private void addComponent() {
        if (modifyRecord.getClass() == VariantWithOneAnswerTrue.class ||
                modifyRecord.getClass() == VariantWithManyAnswerTrue.class ||
                modifyRecord.getClass() == VariantWithOrderAnswer.class) {
            if (modifyRecord.getClass() == VariantWithOneAnswerTrue.class) {
                tModel = new VariantTableModelOne(((VariantWithOneAnswerTrue) modifyRecord).getVariants());


                jTab = new JTable(tModel);
                jTab.getColumnModel().getColumn(1).setCellRenderer(new RadioButtonRenderer());
                jTab.getColumnModel().getColumn(1).setCellEditor(new RadioButtonEditor());
            }


            if (modifyRecord.getClass() == VariantWithManyAnswerTrue.class) {
                tModel = new VariantTableModel(((VariantWithManyAnswerTrue) modifyRecord).getVariants());
                jTab = new JTable(tModel);
            }

            if (modifyRecord.getClass() == VariantWithOrderAnswer.class) {
                tModel = new VariantTableModelOrder(((VariantWithOrderAnswer) modifyRecord).getVariants());
                jTab = new JTable(tModel);
            }

            jTab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            if (jTab.getColumnModel().getColumnCount() == 2) {
                jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
                jTab.getColumnModel().getColumn(1).setPreferredWidth(330);
                jTab.setPreferredScrollableViewportSize(new Dimension(370, 80));
                jbtUp = new JButton("В");
                jbtDown = new JButton("Н");
                jbtUp.addActionListener(event -> {
                    List<Variant> variants = ((VariantWithOrderAnswer) modifyRecord).getVariants();
                    int index;
                    if ((index = jTab.getSelectedRow()) != -1) {
                        if ((index - 1) > -1) {
                            Variant tmp = variants.get(index);
                            variants.set(index, variants.get(index - 1));
                            variants.set(index - 1, tmp);
                            tModel.fireTableDataChanged();
                        }
                    } else {
                        System.out.println("Запись не выбрана");
                    }
                });
                jbtDown.addActionListener(event -> {
                    List<Variant> variants = ((VariantWithOrderAnswer) modifyRecord).getVariants();
                    int index;
                    if ((index = jTab.getSelectedRow()) != -1) {
                        if ((index + 1) < variants.size()) {
                            Variant tmp = variants.get(index);
                            variants.set(index, variants.get(index + 1));
                            variants.set(index + 1, tmp);
                            tModel.fireTableDataChanged();
                        }
                    } else {
                        System.out.println("Запись не выбрана");
                    }
                });
                jPanel2.add(jbtUp);
                jPanel2.add(jbtDown);

            } else {
                jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
                jTab.getColumnModel().getColumn(1).setPreferredWidth(60);
                jTab.getColumnModel().getColumn(2).setMinWidth(270);
                jTab.setPreferredScrollableViewportSize(new Dimension(370, 80));
            }
            jSP = new JScrollPane(jTab);


//
            jPanel2.add(jSP, FlowLayout.LEFT);
//            add(jSP);
        } else if (modifyRecord.getClass() == VariantWithInputAnswer.class) {
            VariantWithInputAnswer variant = (VariantWithInputAnswer) modifyRecord;
            answerLabel = new JLabel("Правильный ответ:");
            variantField = new JTextField(variant.getVariant().getDescription(), 34);
            jPanel2.add(answerLabel);
            jPanel2.add(variantField);
        }
    }

    private List<Question> clearAnswer(List<Question> questions) {
        List<Question> records = new ArrayList<>();
        Question question = null;
        for (Iterator<Question> iterator = questions.iterator(); iterator.hasNext(); ) {
            Question questionOld = iterator.next();
            ModifyRecord modifyRecord = questionOld.getVatiants();
            if (modifyRecord != null) {
                if (modifyRecord.getClass() == VariantWithOneAnswerTrue.class) {
                    VariantWithOneAnswerTrue variantsOld = (VariantWithOneAnswerTrue) modifyRecord;
                    VariantWithOneAnswerTrue variantsNew = new VariantWithOneAnswerTrue();
                    for (Iterator<Variant> variantsOldIterator = variantsOld.getVariants().iterator(); variantsOldIterator.hasNext(); ) {
                        Variant variant = variantsOldIterator.next();
                        variantsNew.getVariants().add(new Variant(variant.getDescription(), false));
                    }
                    modifyRecord = variantsNew;

                }
                if (modifyRecord.getClass() == VariantWithManyAnswerTrue.class) {
                    VariantWithManyAnswerTrue variantsOld = (VariantWithManyAnswerTrue) modifyRecord;
                    VariantWithManyAnswerTrue variantsNew = new VariantWithManyAnswerTrue();
                    for (Iterator<Variant> variantsOldIterator = variantsOld.getVariants().iterator(); variantsOldIterator.hasNext(); ) {
                        Variant variant = variantsOldIterator.next();
                        variantsNew.getVariants().add(new Variant(variant.getDescription(), false));
                    }
                    modifyRecord = variantsNew;

                }
                if (modifyRecord.getClass() == VariantWithOrderAnswer.class) {
                    VariantWithOrderAnswer variantsOld = (VariantWithOrderAnswer) modifyRecord;
                    VariantWithOrderAnswer variantsNew = new VariantWithOrderAnswer();
                    for (Iterator<Variant> variantsOldIterator = variantsOld.getVariants().iterator(); variantsOldIterator.hasNext(); ) {
                        Variant variant = variantsOldIterator.next();
                        variantsNew.getVariants().add(new Variant(variant.getDescription(), variant.isStatus()));
                    }
                    Collections.shuffle(variantsNew.getVariants());
                    modifyRecord = variantsNew;

                }
                if (modifyRecord.getClass() == VariantWithInputAnswer.class) {

                    VariantWithInputAnswer variantsOld = (VariantWithInputAnswer) modifyRecord;
                    VariantWithInputAnswer variantsNew = new VariantWithInputAnswer();

                    Variant variant = variantsOld.getVariant();
                    variantsNew.setVariant(new Variant("", variant.isStatus()));
                    modifyRecord = variantsNew;

                }
            }
            question = new Question(questionOld.getId(), questionOld.getDescription(), modifyRecord);
            records.add(question);
//            System.out.println(question.toString());
        }

        return records;
    }

    private void checkAnswer() {

        boolean[] result;

        result = new boolean[records.size()];
        for (int i = 0; i < records.size(); i++) {
            /*System.out.println(records.get(i).toString());
            System.out.println(recordsAnswer.get(i).toString());
            System.out.println("-------------------------------------------------------");*/
            ModifyRecord modifyRecordQ = records.get(i).getVatiants();
            ModifyRecord modifyRecordA = recordsAnswer.get(i).getVatiants();
            if (modifyRecordA != null && modifyRecordQ != null) {

                boolean flag = true;
                if (modifyRecordQ.getClass() == VariantWithManyAnswerTrue.class) {
                    List<Variant> variantsQ = ((VariantWithManyAnswerTrue) modifyRecordQ).getVariants();
                    List<Variant> variantsA = ((VariantWithManyAnswerTrue) modifyRecordA).getVariants();
                    for (int j = 0; j < variantsQ.size(); j++) {
                        if (!(variantsA.get(j).isStatus() == variantsQ.get(j).isStatus())) {
                            flag = false;
                            break;
                        }
                    }
                    result[i] = flag;
                }
                if (modifyRecordQ.getClass() == VariantWithOneAnswerTrue.class) {
                    List<Variant> variantsQ = ((VariantWithOneAnswerTrue) modifyRecordQ).getVariants();
                    List<Variant> variantsA = ((VariantWithOneAnswerTrue) modifyRecordA).getVariants();
                    for (int j = 0; j < variantsQ.size(); j++) {
                        if (!(variantsA.get(j).isStatus() == variantsQ.get(j).isStatus())) {
                            flag = false;
                            break;
                        }
                    }
                    result[i] = flag;
                }
                if (modifyRecordQ.getClass() == VariantWithOrderAnswer.class) {
                    List<Variant> variantsQ = ((VariantWithOrderAnswer) modifyRecordQ).getVariants();
                    List<Variant> variantsA = ((VariantWithOrderAnswer) modifyRecordA).getVariants();
                    for (int j = 0; j < variantsQ.size(); j++) {
                        if (!(variantsA.get(j).getDescription().equals(variantsQ.get(j).getDescription()))) {
                            flag = false;
                            break;
                        }
                    }
                    result[i] = flag;
                }
                if (modifyRecordQ.getClass() == VariantWithInputAnswer.class) {
                    Variant variantsQ = ((VariantWithInputAnswer) modifyRecordQ).getVariant();
                    Variant variantsA = ((VariantWithInputAnswer) modifyRecordA).getVariant();
                    if (!variantsA.getDescription().equals(variantsQ.getDescription())) {
                        flag = false;
                    }

                    result[i] = flag;
                }
            }

        }
        int count = 0;
        for (int i = 0; i < result.length; i++) {

            System.out.println("Вопрос N" + (i + 1) + "=" + result[i]);
            if (result[i]) {
                count++;
            }
        }
        System.out.println("Результат правильных ответов равен:" + (100 * count / result.length) + "%");


    }
}


