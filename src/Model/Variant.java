package Model;

import java.io.Serializable;

public class Variant implements Serializable {
    private String description;
    private boolean status;

    public Variant() {
    }

    public Variant(String description) {
        this.description = description;
    }

    public Variant(String description, boolean status) {
        this.description = description;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}