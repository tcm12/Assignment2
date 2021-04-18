package model;

public class User {

    private String eMail;
    private String password;
    private String name;

    public User() { }

    public User(String eMail, String password, String name) {
        this.eMail = eMail;
        this.password = password;
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return eMail;
    }

    public void setMail(String eMail) {
        this.eMail = eMail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
