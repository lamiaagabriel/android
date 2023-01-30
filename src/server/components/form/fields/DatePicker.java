package server.components.form.fields;

import java.time.LocalDate;

import server.components.form.InputField;

public class DatePicker extends InputField<LocalDate> {
    private javafx.scene.control.DatePicker dateField;

    public DatePicker() {
        dateField = new javafx.scene.control.DatePicker();
        add(dateField);
    }

    public LocalDate getValue() {
        return dateField.getValue();
    }

    public void setValue(LocalDate date) {
        dateField.setValue(date);
    }

    @Override
    public void reset() {
        super.reset();
        setValue(null);
    }

    @Override
    public boolean isEmpty() {
        if (getValue() == null)
            error.setVisible(true);

        return getValue() == null;
    }
}
