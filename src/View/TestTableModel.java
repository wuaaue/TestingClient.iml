package View;

import Model.Test;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TestTableModel extends AbstractTableModel {
    private List<Test> tests;

    public TestTableModel(List<Test> tests) {
        super();
        this.tests = tests;
    }


    @Override
    public int getRowCount() {
        return tests.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (rowIndex + 1);
            case 1:
                return tests.get(rowIndex).getDate();
            case 2:
                return tests.get(rowIndex).getDescription();
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
                result = "Дата";
                break;
            case 2:
                result = "Название теста";
                break;
        }
        return result;
    }
}
