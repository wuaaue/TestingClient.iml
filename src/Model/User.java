package Model;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String lastname;
    private String firstname;
    private String patronymic;
    private boolean adminStatus;
    private String login;
    private String password;

    public User()  {
    }

    public User(Long id, String lastname, String firstname, String patronymic, boolean adminStatus, String login, String password) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronymic = patronymic;
        this.adminStatus = adminStatus;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setAdminStatus(boolean adminStatus) {
        this.adminStatus = adminStatus;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public boolean isAdminStatus() {
        return adminStatus;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", adminStatus=" + adminStatus +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
