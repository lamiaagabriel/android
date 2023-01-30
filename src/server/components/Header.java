package server.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import server.App;
import server.Launcher;

public class Header extends BorderPane {
    @FXML
    private Label label, name, username, title;
    @FXML
    private Button logout;

    public Header() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/header");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Error in Loading header.fxml");
            System.out.println(e.getMessage());
        }
        logout.setOnAction(e -> logout());
    }

    public String getLabel() {
        return label.textProperty().get();
    }

    public void setLabel(String label) {
        this.label.textProperty().set(label);
    }

    public void resetAdmin() {
        name.setText(App.getName());
        username.setText("@" + App.getUsername());
        title.setText(App.getAdmin().name().toLowerCase());
    }

    public void logout() {
        try {
            Launcher.setRoot("login");
        } catch (IOException e) {
            System.out.println("Error in Loading app.fxml " + e.getMessage());
        }
    }
}
