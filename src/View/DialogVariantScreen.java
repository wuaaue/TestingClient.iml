package View;

import Model.Variant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogVariantScreen extends JDialog implements ActionListener {
    private JLabel variantLabel;
    private JTextArea variantField;
    private JButton jbtclose;
    private JButton jbtsave;
    private Variant variant;
    private boolean change = false;

    public DialogVariantScreen(String title, Variant variant) {
        this.variant = variant;
        setTitle(title);
        setModal(true);
        setSize(400, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contents = new JPanel();
        contents.setLayout(new FlowLayout(FlowLayout.LEFT));
        variantLabel = new JLabel("Содержание ответа:");

        variantField = new JTextArea(variant.getDescription(), 5, 34);
        contents.add(variantLabel);
        contents.add(new JScrollPane(variantField));
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
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comStr = e.getActionCommand();
        switch (comStr) {
            case "Save":
                variant = new Variant(variantField.getText());
                change = true;
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

    public Variant getVariant() {
        return variant;
    }

    public boolean isChange() {
        return change;
    }

}
