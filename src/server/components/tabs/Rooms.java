package server.components.tabs;

import server.Launcher;
import server.components.Tab;
import server.components.form.Form;
import server.components.form.fields.ChoiceBox;
import server.components.form.fields.TextField;
import server.components.table.Table;
import server.components.table.ActionColumn;
import server.data.Database;
import server.data.helper.Callable;
import server.data.rooms.Packages;
import server.data.rooms.Room;
import server.data.rooms.Types;

import java.io.IOException;
import org.bson.conversions.Bson;

import com.mongodb.client.model.Updates;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Rooms extends Tab {
    private String collection = "rooms";
    private ObservableList<Room> rooms;
    @FXML
    private Table<Room> table;
    @FXML
    private ActionColumn<String> actions;
    @FXML
    private Form form;
    @FXML
    private TextField numberField;
    @FXML
    private ChoiceBox<Packages> packagesField;

    @FXML
    private ChoiceBox<Types> typesField;

    public Rooms() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/rooms");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading rooms.fxml, " + e.getMessage());
        }
        packagesField.setItems(Packages.values());
        typesField.setItems(Types.values());

        rooms = Database.find(collection, Room.class);
        table.getItems().setAll(rooms);

        actions.onEdit(onEdit());
        actions.onDelete(onDelete());
        form.setSubmitAction(onSubmit());
        turnToggleOff();
        showTableCenter();
        showFormLeft();
    }

    public Callable onEdit() {
        return () -> {
            Room selectedGuest = table.getItems().get(actions.getSelectedIndex());

            numberField.setValue(selectedGuest.getNum().toString());
            packagesField.setValue(Packages.valueOf(selectedGuest.getPackg()));
            typesField.setValue(Types.valueOf(selectedGuest.getType()));

            form.setSubmitText("Edit");
            form.setSubmitAction(onSubmitEdit());
        };
    }

    public Callable onSubmitEdit() {
        return () -> {
            if (form.isAllFilled()) {
                Integer id = (Integer) table.getItems().get(actions.getSelectedIndex()).getId();

                Bson updates = Updates.combine(
                        Updates.set("name", numberField.getValue()),
                        Updates.set("package", packagesField.getValue().name().toLowerCase()),
                        Updates.set("type", typesField.getValue().name().toLowerCase()));

                Database.updateOne(collection, id, updates);

                table.getItems().get(actions.getSelectedIndex()).setNum(Integer.parseInt(numberField.getValue()));
                table.getItems().get(actions.getSelectedIndex())
                        .setPackg(packagesField.getValue().name().toLowerCase());
                table.getItems().get(actions.getSelectedIndex())
                        .setType(typesField.getValue().name().toLowerCase());
                table.refresh();
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
                Room room = new Room();
                room.setNum(Integer.parseInt(numberField.getValue()));
                room.setPackg(packagesField.getValue().name().toLowerCase());
                room.setType(typesField.getValue().name().toLowerCase());

                Integer id = (Integer) Database.insertOne(collection, room.toDocument());
                rooms = table.getItems();
                room.setId(id);
                rooms.add(room);
                table.setItems(rooms);

                table.refresh();
                actions.setSelectedIndex(-1);
                form.setSubmitText("Submit");
                form.clear();
            }
        };
    }

}
