package server;

import server.components.form.Form;
import server.components.form.fields.ChoiceBox;
import server.components.form.fields.PasswordField;
import server.components.form.fields.TextField;
import server.data.Admin;
import server.data.Database;
import server.data.helper.Callable;
import server.data.users.Stuff;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Login implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<Admin> adminsField;
    @FXML
    private Form form;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminsField.setItems(Admin.values());
        form.setSubmitAction(onSubmit());
        Launcher.setTitle("Login");
        Launcher.smallWindow();
    }

    public Callable onSubmit() {
        return () -> {
            if (form.isAllFilled()) {
                Bson usernameFilter = Filters.eq("username",
                        usernameField.getValue().toLowerCase()),
                        passwordFilter = Filters.eq("password",
                                passwordField.getValue().toLowerCase()),
                        titleFilter = Filters.eq("title",
                                adminsField.getValue().name().toLowerCase());
                Stuff stuff = Database.findOne(Filters.and(usernameFilter, passwordFilter,
                        titleFilter), "stuff",
                        Stuff.class);

                if (stuff == null) { // No Such a User
                    if (Database.findOne(usernameFilter, "stuff", Stuff.class) == null)
                        usernameField.setWarning("Error Username");
                    else if (Database.findOne(Filters.and(usernameFilter, passwordFilter), "stuff",
                            Stuff.class) == null)
                        passwordField.setWarning("Error Password");
                    else
                        adminsField.setWarning("Error Title");
                } else { // Logged Successfully
                    App.setName(stuff.getName());
                    App.setUsername(stuff.getUsername());
                    App.setAdmin(adminsField.getValue());

                    try {
                        Launcher.setRoot("app");
                    } catch (IOException e) {
                        System.out.println("Error in Loading app.fxml " + e.getMessage());
                    }
                }
            }
        };
    }
}
