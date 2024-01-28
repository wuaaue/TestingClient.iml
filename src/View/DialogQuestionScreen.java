package View;

import Model.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;


public class DialogQuestionScreen extends JDialog {
    private JLabel questionLabel;
    private JLabel variantLabel;
    private JTextArea questionField;
    private JTextField variantField;
    private JLabel answerLabel;
    //  private VariantTableModel tModel;
    private AbstractTableModel tModel;
    private JTable jTab;
    private JScrollPane jSP;
    private Question question;
    private ModifyRecord modifyRecord;
    private JButton jbtAdd;
    private JButton jbtEdit;
    private JButton jbtDel;
    private JButton jbtUp;
    private JButton jbtDown;
    private JPanel contents;
    private Container box;
    private JButton jbtClose;
    private JButton jbtSave;
    private boolean change = false;
    private String[] elements;
    private JComboBox<String> cbFirst;
    private DefaultComboBoxModel<String> cbModel;

    public DialogQuestionScreen(String title, Question question) {

        this.question = question;
        this.modifyRecord = this.question.getVatiants();
        if (this.modifyRecord == null)
            this.modifyRecord = new VariantWithOneAnswerTrue();

        setTitle(title);
        setModal(true);
        setSize(400, 350);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        contents = new JPanel();
        contents.setLayout(new FlowLayout(FlowLayout.LEFT));
        questionLabel = new JLabel("Содержание вопроса:");

        questionField = new JTextArea(question.getDescription(), 3, 34);
        contents.add(questionLabel);
        contents.add(new JScrollPane(questionField));

        variantLabel = new JLabel("Варианты ответов:");
        contents.add(variantLabel);

        elements = new String[]{"Выбор одного варианта ответа",
                "Выбор нескольких вариантов ответа",
                "Ввод ответа с клавиатуры",
                "Расстановка в нужном порядке"};

        cbModel = new DefaultComboBoxModel<String>();
        for (int i = 0; i < elements.length; i++)
            cbModel.addElement((String) elements[i]);
        cbFirst = new JComboBox<String>(cbModel);
        cbModel.setSelectedItem("Выбор одного варианта ответа");
        cbFirst.addActionListener(event -> {
                    selectVariant(cbFirst.getSelectedIndex());
                }
        );
        contents.add(cbFirst);
        add(contents);
        addComponent();

        // Создание панели с табличным расположением
        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0));
        // Добавление кнопок в панель

        jbtSave = new JButton("OK");
        jbtClose = new JButton("Отмена");

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


        // Создание панели с последовательным расположением компонентов и
        // выравниванием по правому краю
        JPanel flow = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        flow.add(grid);

        // Размещение панели с кнопками внизу справа

        add(flow, BorderLayout.SOUTH);
//        add(container);

        setVisible(true);


    }

    private void selectVariant(int index) {
        switch (index) {
            case 0:
                modifyRecord = new VariantWithOneAnswerTrue();
                break;
            case 1:
                modifyRecord = new VariantWithManyAnswerTrue();
                break;
            case 2:
                modifyRecord = new VariantWithInputAnswer();
                break;
            case 3:
                this.modifyRecord = new VariantWithOrderAnswer();
                break;
        }
        clearComponent();
        addComponent();
        contents.repaint();
        contents.revalidate();
    }

    private void clearComponent() {
        if (contents.getComponentZOrder(jSP) != -1)
            contents.remove(jSP);
        if (contents.getComponentZOrder(box) != -1)
            contents.remove(box);
        if (contents.getComponentZOrder(answerLabel) != -1)
            contents.remove(answerLabel);
        if (contents.getComponentZOrder(variantField) != -1)
            contents.remove(variantField);
        if (contents.getComponentZOrder(jbtUp) != -1)
            contents.remove(jbtUp);
        if (contents.getComponentZOrder(jbtDown) != -1)
            contents.remove(jbtDown);
    }

    private void addButton() {
        box = Box.createHorizontalBox();

        jbtAdd = new JButton("Добавить");
        jbtEdit = new JButton("Изменить");
        jbtDel = new JButton("Удалить");

        jbtAdd.addActionListener(event -> {
            if (modifyRecord.addRecord()) {
                tModel.fireTableDataChanged();
            }
        });

        jbtEdit.addActionListener(event -> {
            int indexEdit;
            if ((indexEdit = jTab.getSelectedRow()) != -1) {
                if (modifyRecord.editRecord(indexEdit))
                    tModel.fireTableDataChanged();
            } else {
                System.out.println("Запись не выбрана");
            }

        });
        jbtDel.addActionListener(event -> {
            int indexDel;
            if ((indexDel = jTab.getSelectedRow()) != -1) {
                if (modifyRecord.delRecord(indexDel)) {
                    tModel.fireTableDataChanged();
                }
            } else {
                System.out.println("Запись не выбрана");
            }
        });

        box.add(jbtAdd);
        box.add(jbtEdit);
        box.add(jbtDel);
        contents.add(box);
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
                jbtUp.addActionListener(event->{
                    List<Variant> variants=((VariantWithOrderAnswer) modifyRecord).getVariants();
                    int index;
                    if ((index = jTab.getSelectedRow()) != -1) {
                        if((index-1)>-1){
                            Variant tmp = variants.get(index);
                            variants.set(index,variants.get(index-1));
                            variants.set(index-1,tmp);
                            tModel.fireTableDataChanged();
                        }
                    } else {
                        System.out.println("Запись не выбрана");
                    }
                });
                jbtDown.addActionListener(event->{
                    List<Variant> variants=((VariantWithOrderAnswer) modifyRecord).getVariants();
                    int index;
                    if ((index = jTab.getSelectedRow()) != -1) {
                        if((index+1)<variants.size()){
                            Variant tmp = variants.get(index);
                            variants.set(index,variants.get(index+1));
                            variants.set(index+1,tmp);
                            tModel.fireTableDataChanged();
                        }
                    } else {
                        System.out.println("Запись не выбрана");
                    }
                });
                contents.add(jbtUp);
                contents.add(jbtDown);

            } else {
                jTab.getColumnModel().getColumn(0).setPreferredWidth(40);
                jTab.getColumnModel().getColumn(1).setPreferredWidth(60);
                jTab.getColumnModel().getColumn(2).setMinWidth(270);
                jTab.setPreferredScrollableViewportSize(new Dimension(370, 80));
            }
            jSP = new JScrollPane(jTab);
           /* if (jTab.getColumnModel().getColumnCount() == 2){
                contents.add(jbtUp);
                contents.add(jbtDown);
            }*/


//
            addButton();
            contents.add(jSP);

        } else if (modifyRecord.getClass() == VariantWithInputAnswer.class) {
            VariantWithInputAnswer variant = (VariantWithInputAnswer) modifyRecord;
            answerLabel = new JLabel("Правильный ответ:");
            variantField = new JTextField(variant.getVariant().getDescription(), 34);
            contents.add(answerLabel);
            contents.add(variantField);
        }
    }

    private void saveRecord() {
        if (question.getId() == null)
            question.setId(Math.round(Math.random() * 1000 + System.currentTimeMillis()));
        if (modifyRecord.getClass() == VariantWithInputAnswer.class) {
            modifyRecord = new VariantWithInputAnswer(variantField.getText());
        }
        question.setDescription(questionField.getText());
        question.setVatiants(modifyRecord);
        change = true;
    }

    public Question getRecord() {
        return question;
    }

    public boolean isChange() {
        return change;
    }

    public static void main(String[] args) {
        new DialogQuestionScreen("String title", new Question());
    }
}

