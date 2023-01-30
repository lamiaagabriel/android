package server.components.form.fields;

import server.components.form.TypeField;

public class TextField extends TypeField<String> {

    public String getValue() {
        return textfield.getText();
    }

    public void setValue(String value) {
        textfield.setText(value.toString());
    }
}
