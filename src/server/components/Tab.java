package server.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import server.Launcher;
import server.components.form.Form;
import server.components.table.Table;

public class Tab extends javafx.scene.control.Tab {
    private Table table;
    private Form form;

    @FXML
    private ImageView icon;
    @FXML
    private Header header;
    @FXML
    private BorderPane scene;
    @FXML
    private Button toggle;

    public Tab() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tab");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading tab.fxml, " + e.getMessage());
        }
        toggle.setOnAction(e -> toggleCenter());
    }

    public Header getHeader() {
        return header;
    }

    public void setLabel(String label) {
        header.setLabel(label);
        Launcher.setTitle(label);
    }

    public String getLabel() {
        return header.getLabel();
    }

    public void setIcon(String path) {
        try {
            icon.setImage(new Image(Launcher.getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException("Error in getting " + path);
        }
    }

    public String getIcon() {
        return icon.getImage().getUrl();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
        scene.setCenter(table);
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public void toggleCenter() {
        if (table == scene.getCenter())
            scene.setCenter(form);
        else {
            scene.setCenter(table);
            form.clear();
        }
    }

    public void turnToggleOff() {
        toggle.setVisible(false);
    }

    public void turnToggleOn() {
        toggle.setVisible(true);
    }

    public void showTableCenter() {
        toggle.setText("Add New Element");
        scene.setCenter(table);
    }

    public void showFormCenter() {
        toggle.setText("Back To Table");
        scene.setCenter(form);
    }

    public void showFormLeft() {
        turnToggleOff();
        scene.setLeft(form);
    }

}
