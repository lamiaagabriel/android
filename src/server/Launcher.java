package server;

import java.io.IOException;
import java.io.InputStream;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Launcher extends Application {
    private static Scene scene;
    private static Stage stage;

    public static FXMLLoader loadFXML(String fxml) throws IOException {
        return new FXMLLoader(Launcher.class.getResource("../client/" + fxml + ".fxml"));
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml).load());
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void smallWindow() {
        stage.setResizable(false);
        stage.setMaximized(false);
    }

    public static void largeWindow() {
        stage.setResizable(true);
        stage.setMaximized(true);
    }

    public static InputStream getResource(String str) throws IOException {
        return Launcher.class.getResourceAsStream("../assets/" + str);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Launcher.stage = stage;

        scene = new Scene(loadFXML("launcher").load());
        scene.getStylesheets().add(getClass().getResource("../assets/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.getIcons().add(new Image(Launcher.getResource("icon.png")));
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        smallWindow();
        stage.show();

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(3000));
        fadeTransition.setNode(scene.getRoot());
        fadeTransition.setOnFinished(event -> {
            try {
                Launcher.setRoot("login");
            } catch (IOException e) {
                System.out.println("Error in Loading login.fxml " + e.getMessage());
            }
        });
        fadeTransition.play();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
