package server.data;

import org.bson.Document;

public class Reservation {
    private Integer id, roomId, guestId;
    private Integer nights, pricePerNight;

    public Reservation() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Document toDocument() {
        return new Document("roomId", roomId)
                .append("guestId", guestId)
                .append("pricePerNight", pricePerNight)
                .append("nights", nights);
    }
}
