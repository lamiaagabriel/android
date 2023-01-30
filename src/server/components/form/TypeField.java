package server.components.form;

import javafx.scene.control.TextField;

public abstract class TypeField<T> extends InputField<T> {
    protected TextField textfield;

    public TypeField() {
        textfield = new TextField();
        add(textfield);
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    @Override
    public void reset() {
        super.reset();
        textfield.setText("");
    }

    @Override
    public boolean isEmpty() {
        if (getValue().equals(""))
            error.setVisible(true);

        return getValue().equals("");
    }
}
