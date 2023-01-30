package server.data.users;

import org.bson.Document;

public abstract class User {
    private String name, gender, country, city;
    private Integer id;
    private Long phone;
    // private Card card;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // public Card getCard() {
    // if (card == null)
    // card = new Card();
    // return card;
    // }

    // public void setCard(Card card) {
    // if (card == null)
    // card = new Card();
    // this.card = card;
    // }

    public Document toDocument() {
        return new Document("name", name)
                .append("gender", gender)
                .append("phone", phone)
                .append("country", country)
                .append("city", city);
    }

    @Override
    public String toString() {
        return "#" + id + ": " + name;
    }
}
