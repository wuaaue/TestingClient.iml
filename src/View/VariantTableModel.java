package View;

import Model.Variant;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VariantTableModel extends AbstractTableModel {

    private List<Variant> variants;

    public VariantTableModel(List<Variant> variants) {
        super();
        this.variants = variants;
    }

    @Override
    public int getRowCount() {
        return variants.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 1:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (rowIndex + 1);
            case 1:
                return variants.get(rowIndex).isStatus();
            case 2:
                return variants.get(rowIndex).getDescription();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        String result = "";
        switch (column) {
            case 0:
                result = "№";
                break;
            case 1:
                result = "Верный";
                break;
            case 2:
                result = "Вариант ответа";
                break;
        }
        return result;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            variants.get(rowIndex).setStatus(Boolean.TRUE.equals(aValue));
        }

    }
}
