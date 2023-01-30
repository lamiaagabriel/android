package server.components.tabs;

import server.Launcher;
import server.components.Tab;
import server.components.form.Form;
import server.components.form.fields.ChoiceBox;
import server.components.form.fields.PasswordField;
import server.components.form.fields.TextField;
import server.components.table.Table;
import server.components.table.ActionColumn;
import server.data.Database;
import server.data.Reservation;
import server.data.helper.Callable;
import server.data.rooms.Room;
import server.data.users.Guest;

import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Updates;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class Reservations extends Tab {
    private String collection = "reservations";
    private ObservableList<Reservation> reservations;
    private ArrayList<Guest> guests;
    private ArrayList<Room> rooms;

    @FXML
    private Table<Reservation> table;
    @FXML
    private ActionColumn<String> actions;

    @FXML
    private Form form;
    @FXML
    private ChoiceBox<Room> roomsField;
    @FXML
    private ChoiceBox<Guest> guestsField;
    @FXML
    private TextField nightsField, pricePerNightField;

    @FXML
    private Label selectedRoomField, selectedGuestField;

    public Reservations() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/reservations");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading reservations.fxml, " + e.getMessage());
        }
        resetGuests();
        resetRooms();
        roomsField.getChoicebox().getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_index, Number new_index) -> {
                    if (old_index != new_index) {
                        selectedRoomField.setText(rooms.get(new_index.intValue()).getPackg());
                        pricePerNightField.setValue(rooms.get(new_index.intValue()).getPricePerNight());
                    }
                });

        guestsField.getChoicebox().getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_index, Number new_index) -> {
                    if (old_index != new_index) {
                        selectedGuestField.setText(guests.get(new_index.intValue()).getName());
                    }
                });
        reservations = Database.find(collection, Reservation.class);
        table.getItems().setAll(reservations);
        actions.onEdit(onEdit());
        actions.onDelete(onDelete());
        form.setSubmitAction(onSubmit());
        showFormLeft();
    }

    public Callable onEdit() {
        return () -> {
            Reservation selectedReservation = table.getItems().get(actions.getSelectedIndex());
            // roomsField.setValue(rooms.get(rooms.indexOf(selectedReservation.getRoomId())));
            // guestsField.setValue(guests.get(guests.indexOf(selectedReservation.getGuestId())));
            nightsField.setValue(selectedReservation.getNights().toString());
            pricePerNightField.setValue(selectedReservation.getPricePerNight().toString());

            form.setSubmitText("Edit");
            form.setSubmitAction(onSubmitEdit());
            showFormLeft();
        };
    }

    public Callable onSubmitEdit() {
        return () -> {
            if (form.isAllFilled()) {
                Integer id = (Integer) table.getItems().get(actions.getSelectedIndex()).getId();

                Bson updates = Updates.combine(
                        Updates.set("roomId", roomsField.getValue().getId()),
                        Updates.set("guestId", guestsField.getValue().getId()),
                        Updates.set("nights", Integer.parseInt(nightsField.getValue())),
                        Updates.set("pricePerNight", Integer.parseInt(pricePerNightField.getValue())));

                Database.updateOne(collection, id, updates);

                table.getItems().get(actions.getSelectedIndex()).setRoomId(roomsField.getValue().getId());
                table.getItems().get(actions.getSelectedIndex()).setGuestId(guestsField.getValue().getId());
                table.getItems().get(actions.getSelectedIndex()).setNights(Integer.parseInt(nightsField.getValue()));
                table.getItems().get(actions.getSelectedIndex())
                        .setPricePerNight(Integer.parseInt(pricePerNightField.getValue()));

                table.refresh();
                showTableCenter();
                actions.setSelectedIndex(-1);
                form.setSubmitText("Submit");
                form.setSubmitAction(onSubmit());
                form.clear();
            }
        };
    }

    public Callable onDelete() {
        return () -> {
            Integer id = (Integer) table.getItems().get(actions.getSelectedIndex()).getId();
            table.getItems().remove(actions.getSelectedIndex().intValue());
            Database.deleteOne(collection, id);
            actions.getSelectedIndex();
        };
    }

    public Callable onSubmit() {
        return () -> {
            if (form.isAllFilled()) {
                Reservation reservation = new Reservation();
                reservation.setRoomId(roomsField.getValue().getId());
                reservation.setGuestId(guestsField.getValue().getId());
                reservation.setNights(Integer.parseInt(nightsField.getValue()));
                reservation.setPricePerNight(Integer.parseInt(pricePerNightField.getValue()));

                Integer id = (Integer) Database.insertOne(collection,
                        reservation.toDocument());

                reservations = table.getItems();
                reservation.setId(id);
                reservations.add(reservation);
                table.setItems(reservations);

                table.refresh();
                showTableCenter();
                actions.setSelectedIndex(-1);
                form.setSubmitText("Submit");
                form.clear();
            }
        };
    }

    private void resetGuests() {
        guests = Database.aggregate("guests", Guest.class, new Document("_id", 1L).append("name", 1L));
        guestsField.setItems(guests);
    }

    private void resetRooms() {
        rooms = Database.findArray("rooms", Room.class);
        roomsField.setItems(rooms);
    }

}
