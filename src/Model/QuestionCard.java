package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionCard implements Serializable {//класс, описывающий набор вопросов
    private List<Question> questions;
    private boolean status;

    public QuestionCard() {
        List<Question> questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QuestionCard{" +
                "questions=" + questions +
                ", status=" + status +
                '}';
    }
}
