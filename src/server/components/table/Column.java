package server.components.table;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

@DefaultProperty(value = "subColumns")
public class Column<T, E> extends TableColumn<T, E> {
    public Column() {
        setStyle("-fx-alignment: CENTER;");
    }

    public Column(String label) {
        setText(label);
    }

    public Column(String label, String key) {
        super(label);
        setCellValueFactory(new PropertyValueFactory<T, E>(key));
    }

    public final ObservableList<javafx.scene.control.TableColumn<T, ?>> getSubColumns() {
        return super.getColumns();
    }

    public void setLabel(String label) {
        setText(label);
    }

    public String getLabel() {
        return getText();
    }

    public void setKey(String key) {
        setCellValueFactory(new PropertyValueFactory<T, E>(key));
    }

    public String getKey() {
        return getCellValueFactory().toString();
    }

    public void setFixedSize(String w) {
        setMinWidth(Double.parseDouble(w));
        setMaxWidth(Double.parseDouble(w));
        setPrefWidth(Double.parseDouble(w));
    }

    public String getFixedSize() {
        return Double.toString(getPrefWidth());
    }

    public void setFixedSize(double w) {
        setFixedSize(Double.toString(w));
    }
}
