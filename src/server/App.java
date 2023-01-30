package server;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import server.data.Admin;

public class App implements Initializable {
    private static String name, username;
    private static Admin admin;

    @FXML
    private TabPane content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        App.name = "Lamiaa Monaem";
        App.username = "lamiaamonaem";
        App.admin = Admin.MANAGER;
        App.admin.resetAdminInfo();
        content.getTabs().clear();
        content.getTabs().setAll(App.admin.getTabs());
        Launcher.setTitle(App.admin.name());
        Launcher.largeWindow();
        
}

    // User Info
    public static String getName() {
        return App.name;
    }

    public static void setName(String name) {
        App.name = name;
    }

    public static String getUsername() {
        return App.username;
    }

    public static void setUsername(String username) {
        App.username = username;
    }

    public static Admin getAdmin() {
        return App.admin;
    }

    public static void setAdmin(Admin admin) {
        App.admin = admin;
    }

}
