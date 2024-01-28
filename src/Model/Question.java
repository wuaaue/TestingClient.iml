package Model;

import java.io.Serializable;

public class Question implements Serializable {//класс, описывающий один вопрос
    private Long id;
    private String description;
    private ModifyRecord vatiants;

    public Question() {
    }

    public Question(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Question(String description) {
        this.description = description;
    }

    public Question(String description, ModifyRecord vatiants) {
        this.description = description;
        this.vatiants = vatiants;
    }

    public Question(Long id, String description, ModifyRecord vatiants) {
        this.id = id;
        this.description = description;
        this.vatiants = vatiants;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVatiants(ModifyRecord vatiants) {
        this.vatiants = vatiants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ModifyRecord getVatiants() {
        return vatiants;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", vatiants=" + vatiants +
                '}';
    }
}
