package View;

import Model.Question;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {

    private List<Question> questions;

    public QuestionTableModel(List<Question> questions) {
        super();
        this.questions = questions;
    }

    @Override
    public int getRowCount() {
        return questions.size();
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
                return questions.get(rowIndex).getDescription();
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
                result = "Название вопроса";
                break;
        }
        return result;
    }


}
