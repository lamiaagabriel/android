package server.components.tabs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import server.Launcher;
import server.components.Tab;

public class Transactions extends Tab {
    public Transactions() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/tabs/transactions");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading transactions.fxml --> " + e.getMessage());
        }
    }

}
