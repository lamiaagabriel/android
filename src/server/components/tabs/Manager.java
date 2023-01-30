package server.components.tabs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import server.Launcher;
import server.components.Tab;

public class Manager extends Tab {
    public Manager() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/manager");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading manager.fxml --> " + e.getMessage());
        }
    }

}
