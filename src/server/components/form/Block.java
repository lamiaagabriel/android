package server.components.form;

import java.util.HashSet;

import javafx.beans.DefaultProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@DefaultProperty(value = "children")
public class Block extends VBox {
    // private HashSet<InputField> fields;
    private Label label;

    public Block() {
        this.label = new Label();
        this.label.getStyleClass().add("block-title");
        getChildren().add(this.label);
    }

    public void setLabel(String label) {
        this.label.textProperty().set(label);
    }

    public String getLabel() {
        return label.textProperty().get();
    }

}
