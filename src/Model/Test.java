package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Test implements Serializable {
    private Long id;
    private LocalDate date;
    private String description;
    private List<QuestionCard> questionCards;
    private List<User> users;

    public Test() {
        this.date = LocalDate.now();
        this.questionCards = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Test(String description) {
        this.description = description;
        this.date = LocalDate.now();
        this.questionCards = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Test(Long id, String description) {
        this.id = id;
        this.description = description;
        this.date = LocalDate.now();
        this.questionCards = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Test(Long id, String description, List<QuestionCard> questionCards, List<User> users) {
        this.id=id;
        this.description = description;
        this.date = LocalDate.now();
        this.questionCards = questionCards;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestionCards(List<QuestionCard> questionCards) {
        this.questionCards = questionCards;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<QuestionCard> getQuestionCards() {
        return questionCards;
    }


    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", questionCards=" + questionCards +
                ", users=" + users +
                '}';
    }
}
