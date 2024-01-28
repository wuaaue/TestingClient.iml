package View;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class RadioButtonRenderer  implements TableCellRenderer {
    private JRadioButton radioButton;
    public RadioButtonRenderer(){
        this.radioButton=new JRadioButton();
        radioButton.setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        radioButton.setSelected(Boolean.TRUE.equals(value));
        return radioButton;
    }

}
