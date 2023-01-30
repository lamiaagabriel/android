package server.data.rooms;

import org.bson.Document;

public class Room {
    protected Integer id, num;
    protected String packg, type, pricePerNight;

    public Room() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getPackg() {
        return packg;
    }

    public void setPackg(String packg) {
        this.packg = packg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(String pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Document toDocument() {
        return new Document("num", num)
                .append("pricePerNight", pricePerNight)
                .append("package", packg)
                .append("type", type);
    }

    @Override
    public String toString() {
        return "#room: " + num;
    }

}
