package server.components.form;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class InputField<T> extends VBox {
    protected Label label, error;

    public InputField() {
        label = new Label();
        error = new Label();
        label.getStyleClass().add("title");
        error.getStyleClass().addAll("error", "danger-text");
        errorOff();
        getChildren().addAll(label, error);
        getStyleClass().add("input-field");
    }

    public String getLabel() {
        return label.textProperty().get();
    }

    public void setLabel(String value) {
        label.textProperty().set(value);
        setError(value + " is required");
    }

    public String getError() {
        return error.textProperty().get();
    }

    public void setError(String value) {
        error.textProperty().set(value);
        error.getStyleClass().setAll("error", "danger-text");
    }

    public void setWarning(String value) {
        error.textProperty().set(value);
        error.getStyleClass().setAll("error", "warning-text");
        errorOn();
    }

    public void errorOff() {
        error.setVisible(false);
    }

    public void errorOn() {
        error.setVisible(true);
    }

    public void reset() {
        errorOff();
    }

    public void add(Node node) {
        switch (node.getClass().toString()) {
            case "class javafx.scene.control.TextField":
            case "class javafx.scene.control.PasswordField":
                ((TextField) node).focusedProperty().addListener((arg, oldValue, newValue) -> {
                    errorOff();
                    if (!newValue) { // focus-out
                        if (((TextField) node).getText().equals("")) {
                            setError(getLabel() + " is required");
                            errorOn();
                        }
                    }
                });
                break;
            // case "class javafx.scene.control.ChoiceBox":
            // ((ChoiceBox) node).focusedProperty().addListener((arg, oldValue, newValue) ->
            // {
            // errorOff();
            // if (!newValue) {
            // if (((ChoiceBox) node).getValue() == null) {
            // setError(getLabel() + " is required");
            // errorOn();
            // }
            // }
            // });
            // break;
            default:
                break;
        }

        getChildren().add(1, node);
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    public abstract boolean isEmpty();
}
