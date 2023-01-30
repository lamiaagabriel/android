package server.components.tabs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import server.Launcher;
import server.components.Tab;

public class Receptionist extends Tab {
    public Receptionist() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/receptionist");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading receptionist.fxml --> " + e.getMessage());
        }
    }

}
