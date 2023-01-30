package server.components;

import java.io.IOException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.Launcher;

public class ActionButton extends Button {
    private ImageView imageView;
    private String icon;

    public ActionButton() {
        createImage();
    }

    public ActionButton(String icon) {
        setIcon(icon);
    }

    public void setIcon(String icon) {
        this.icon = icon;
        if (this.imageView == null)
            createImage();

        try {
            this.imageView.setImage(new Image(Launcher.getResource("icons/" + icon + ".png")));
            setGraphic(imageView);
        } catch (IOException e) {
            throw new RuntimeException("Error in getting " + icon + ".png");
        }
    }

    public String getIcon() {
        return icon;
    }

    private void createImage() {
        imageView = new ImageView();
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }
}
