package View;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RadioButtonEditor extends AbstractCellEditor implements  TableCellEditor, ActionListener {
    private JRadioButton radioButton;
    public RadioButtonEditor(){
        this.radioButton=new JRadioButton();
        radioButton.addActionListener(this);
    }
 @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        radioButton.setSelected(Boolean.TRUE.equals(value));
        return radioButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopCellEditing();
    }

    @Override
    public Object getCellEditorValue() {
        return radioButton.isSelected();
    }


}
