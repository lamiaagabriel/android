package server.data.users;

import org.bson.Document;

public class Stuff extends User {
    private String username, password, title;

    public Stuff() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Document toDocument() {
        return super.toDocument()
                .append("username", username.toLowerCase())
                .append("password", password.toLowerCase())
                .append("title", title.toLowerCase());
    }
}
