package server.data.users;

import org.bson.Document;

public class Guest extends User {
    private String email;

    public Guest() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Document toDocument() {
        return super.toDocument()
                .append("email", email.toLowerCase());
        // .append("phone", getPhone())
        // .append("card",
        // new Document("name", getCard().getName())
        // .append("number", getCard().getNumber())
        // .append("exp", getCard().getExp()));
    }
}
