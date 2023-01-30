package server.components.tabs;

import server.Launcher;
import server.components.Tab;
import server.components.form.Form;
import server.components.form.fields.ChoiceBox;
import server.components.form.fields.DatePicker;
import server.components.form.fields.TextField;
import server.components.table.Column;
import server.components.table.Table;
import server.components.table.ActionColumn;
import server.data.users.Card;
import server.data.users.Guest;
import server.data.Database;
import server.data.helper.Callable;
import server.data.helper.Helper;

import java.io.IOException;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Updates;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Guests extends Tab {
    private String collection = "guests";
    private ObservableList<Guest> guests;

    @FXML
    private Table<Guest> table;
    @FXML
    private ActionColumn<String> actions;

    @FXML
    private Form form;
    @FXML
    private TextField nameField, emailField, phoneField;
    @FXML
    private ChoiceBox<String> genderField, countryField, cityField;

    public Guests() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/guests");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading guests.fxml, " + e.getMessage());
        }
        genderField.setItems(new String[] { "Male", "Female" });
        countryField.setItems(Helper.COUNTRIES);
        cityField.setItems(Helper.COUNTRIES);

        guests = Database.find(collection, Guest.class);
        table.getItems().setAll(guests);

        actions.onEdit(onEdit());
        actions.onDelete(onDelete());
        form.setSubmitAction(onSubmit());
    }

    public Callable onEdit() {
        return () -> {
            Guest selectedGuest = table.getItems().get(actions.getSelectedIndex());

            nameField.setValue(selectedGuest.getName());
            emailField.setValue(selectedGuest.getEmail());
            phoneField.setValue(selectedGuest.getPhone().toString());
            genderField.setValue(selectedGuest.getGender());
            countryField.setValue(selectedGuest.getCountry());
            cityField.setValue(selectedGuest.getCity());

            form.setSubmitText("Edit");
            form.setSubmitAction(onSubmitEdit());
            showFormCenter();
        };
    }

    public Callable onSubmitEdit() {
        return () -> {
            if (form.isAllFilled()) {
                Integer id = (Integer) table.getItems().get(actions.getSelectedIndex()).getId();

                Bson updates = Updates.combine(
                        Updates.set("name", nameField.getValue()),
                        Updates.set("email", emailField.getValue()),
                        Updates.set("gender", genderField.getValue()),
                        Updates.set("phone", phoneField.getValue()),
                        Updates.set("country", countryField.getValue()),
                        Updates.set("city", cityField.getValue()));
                // Updates.set("card",
                // Updates.combine(
                // Updates.set("name", cardNameField.getValue()),
                // Updates.set("number", cardNumberField.getValue()),
                // Updates.set("exp", cardExpField.getValue()))),

                Database.updateOne(collection, id, updates);

                table.getItems().get(actions.getSelectedIndex()).setName(nameField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setEmail(emailField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setGender(genderField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setPhone(Long.parseLong(phoneField.getValue()));
                table.getItems().get(actions.getSelectedIndex()).setCountry(countryField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setCity(cityField.getValue());

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
                Guest guest = new Guest();
                guest.setName(nameField.getValue());
                guest.setEmail(emailField.getValue());
                guest.setPhone(Long.parseLong(phoneField.getValue()));
                guest.setGender(genderField.getValue());
                guest.setCountry(countryField.getValue());
                guest.setCity(cityField.getValue());

                Integer id = (Integer) Database.insertOne(collection, guest.toDocument());
                guests = table.getItems();
                guest.setId(id);
                guests.add(guest);
                table.setItems(guests);

                table.refresh();
                showTableCenter();
                actions.setSelectedIndex(-1);
                form.setSubmitText("Submit");
                form.clear();
            }
        };
    }

}
