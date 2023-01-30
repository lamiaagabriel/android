package server.components.table;

import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

@DefaultProperty(value = "tableColumns")
public class Table<T> extends TableView<T> {
    private Column<T, Integer> id;

    // private Callable<Integer> onEdit;
    // private Callable<Integer> onDelete;

    @SuppressWarnings("unchecked")
    public Table() {
        id = new Column<T, Integer>("ID", "id");
        id.setFixedSize(25);
        setEditable(false);
        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        super.getColumns().addAll(id);

    }

    public ObservableList<TableColumn<T, ?>> getTableColumns() {
        return super.getColumns();
    }

}
