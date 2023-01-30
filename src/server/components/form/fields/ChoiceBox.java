package server.components.form.fields;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import server.components.form.InputField;

public class ChoiceBox<T> extends InputField<T> {
    private javafx.scene.control.ChoiceBox<T> choicebox;

    public ChoiceBox() {
        choicebox = new javafx.scene.control.ChoiceBox<T>();
        add(choicebox);
        getStyleClass().add("choice-box-container");
    }

    public javafx.scene.control.ChoiceBox<T> getChoicebox() {
        return choicebox;
    }

    public T getValue() {
        return choicebox.getValue();
    }

    public void setValue(T value) {
        if (choicebox.getItems().indexOf(value) >= 0)
            choicebox.setValue(choicebox.getItems().get(choicebox.getItems().indexOf(value)));
    }

    public void addItem(T item) {
        choicebox.getItems().add(item);
    }

    public void setItems(T[] items) {
        getItems().setAll(items);
        if (size() > 0)
            choicebox.setValue(getItems().get(0));
    }

    public void setItems(ArrayList<T> items) {
        getItems().setAll(items);
        if (size() > 0)
            choicebox.setValue(getItems().get(0));
    }

    public ObservableList<T> getItems() {
        return choicebox.getItems();
    }

    public int size() {
        return getItems().size();
    }

    @Override
    public void reset() {
        super.reset();
        if (size() > 0) {
            setValue(choicebox.getItems().get(0));
        }
    }

    @Override
    public boolean isEmpty() {
        if (getValue() == null)
            error.setVisible(true);

        return getValue() == null;
    }

}
