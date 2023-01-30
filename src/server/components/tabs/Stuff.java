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
import server.data.helper.Callable;
import server.data.helper.Helper;

import java.io.IOException;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Updates;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Stuff extends Tab {
    private String collection = "stuff";
    private ObservableList<server.data.users.Stuff> stuffs;

    @FXML
    private Table<server.data.users.Stuff> table;
    @FXML
    private ActionColumn<String> actions;

    @FXML
    private Form form;
    @FXML
    private TextField nameField, usernameField, phoneField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> genderField, countryField, cityField, titleField;

    public Stuff() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/stuff");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading stuff.fxml, " + e.getMessage());
        }
        genderField.setItems(new String[] { "Male", "Female" });
        titleField.setItems(new String[] { "Manager", "Receptionist", "Servicer", "Bar Servicer" });
        countryField.setItems(Helper.COUNTRIES);
        cityField.setItems(Helper.COUNTRIES);

        stuffs = Database.find(collection, server.data.users.Stuff.class);
        table.getItems().setAll(stuffs);

        actions.onEdit(onEdit());
        actions.onDelete(onDelete());
        form.setSubmitAction(onSubmit());
    }

    public Callable onEdit() {
        return () -> {
            server.data.users.Stuff selectedGuest = table.getItems().get(actions.getSelectedIndex());

            nameField.setValue(selectedGuest.getName());
            usernameField.setValue(selectedGuest.getUsername());
            passwordField.setValue(selectedGuest.getPassword());
            phoneField.setValue(selectedGuest.getPhone().toString());
            genderField.setValue(selectedGuest.getGender());
            countryField.setValue(selectedGuest.getCountry());
            cityField.setValue(selectedGuest.getCity());
            titleField.setValue(selectedGuest.getTitle());

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
                        Updates.set("username", usernameField.getValue()),
                        Updates.set("password", passwordField.getValue()),
                        Updates.set("phone", phoneField.getValue()),
                        Updates.set("gender", genderField.getValue()),
                        Updates.set("country", countryField.getValue()),
                        Updates.set("city", cityField.getValue()),
                        Updates.set("title", titleField.getValue()));

                Database.updateOne(collection, id, updates);

                table.getItems().get(actions.getSelectedIndex()).setName(nameField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setUsername(usernameField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setPassword(passwordField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setGender(genderField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setPhone(Long.parseLong(phoneField.getValue()));
                table.getItems().get(actions.getSelectedIndex()).setCountry(countryField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setCity(cityField.getValue());
                table.getItems().get(actions.getSelectedIndex()).setTitle(titleField.getValue());

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
                server.data.users.Stuff stuff = new server.data.users.Stuff();
                stuff.setName(nameField.getValue());
                stuff.setUsername(usernameField.getValue());
                stuff.setPassword(passwordField.getValue());
                stuff.setPhone(Long.parseLong(phoneField.getValue()));
                stuff.setGender(genderField.getValue());
                stuff.setCountry(countryField.getValue());
                stuff.setCity(cityField.getValue());
                stuff.setTitle(titleField.getValue());

                Integer id = (Integer) Database.insertOne(collection, stuff.toDocument());
                stuffs = table.getItems();
                stuff.setId(id);
                stuffs.add(stuff);
                table.setItems(stuffs);

                table.refresh();
                showTableCenter();
                actions.setSelectedIndex(-1);
                form.setSubmitText("Submit");
                form.clear();
            }
        };
    }

}
