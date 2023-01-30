package server.components.form.fields;

import server.components.form.InputField;

public class PasswordField extends InputField<String> {
    private javafx.scene.control.PasswordField passwordfield;

    public PasswordField() {
        passwordfield = new javafx.scene.control.PasswordField();
        add(passwordfield);
    }

    public String getValue() {
        return passwordfield.getText();
    }

    public void setValue(String value) {
        passwordfield.setText(value);
    }

    @Override
    public void reset() {
        super.reset();
        setValue("");
    }

    @Override
    public boolean isEmpty() {
        if (getValue().equals("")) // Empty
            error.setVisible(true);

        return getValue().equals("");
    }

}
