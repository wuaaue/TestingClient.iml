package View;

import Model.Variant;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VariantTableModelOrder extends AbstractTableModel {
    private List<Variant> variants;

    public VariantTableModelOrder(List<Variant> variants) {
        super();
        this.variants = variants;
    }


    @Override
    public int getRowCount() {
        return variants.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (rowIndex + 1);
            case 1:
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
                result = "Вариант ответа";
                break;
        }
        return result;
    }
}
