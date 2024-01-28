package View;

import Model.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TestUserTableModel extends AbstractTableModel {

    private List<User> users;

    public TestUserTableModel(List<User> users) {
        super();
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (rowIndex + 1);
            case 1:
                return users.get(rowIndex).getLastname();
            case 2:
                return users.get(rowIndex).getFirstname();
            case 3:
                return users.get(rowIndex).getPatronymic();
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
                result = "Фамилия";
                break;
            case 2:
                result = "Имя";
                break;
            case 3:
                result = "Отчество";
                break;
        }
        return result;
    }
}
