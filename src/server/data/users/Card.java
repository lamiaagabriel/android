package server.data.users;

import java.time.LocalDate;

public class Card {
    private String name, number;
    private LocalDate exp;

    public Card() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExp() {
        return exp;
    }

    public void setExp(LocalDate exp) {
        this.exp = exp;
    }
}
