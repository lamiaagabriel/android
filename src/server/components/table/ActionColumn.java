package server.components.table;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import server.components.ActionButton;
import server.data.helper.Callable;

public class ActionColumn<T> extends Column<T, String> {
    private Callable onEdit, onDelete;
    private Integer index;

    public ActionColumn() {
        setLabel("Actions");
        setFixedSize(100);
        setSelectedIndex(-1);

        setCellFactory(e -> {
            return new TableCell<T, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox box = new HBox(7);
                        box.setAlignment(Pos.CENTER);
                        ActionButton editButton = new ActionButton("edit"),
                                deleteButton = new ActionButton("delete");
                        box.getChildren().addAll(editButton, deleteButton);
                        setSelectedIndex(-1);
                        editButton.setOnAction(e -> {

                            setSelectedIndex(getIndex());
                            onEdit.call();
                        });

                        deleteButton.setOnAction(e -> {

                            setSelectedIndex(getIndex());
                            onDelete.call();
                        });
                        setGraphic(box);
                    }
                    setText(null);
                }
            };
        });
    }

    public Integer getSelectedIndex() {
        return index;
    }

    public void setSelectedIndex(Integer index) {
        this.index = index;
    }

    public void onEdit(Callable onEdit) {
        this.onEdit = onEdit;
    }

    public void onDelete(Callable onDelete) {
        this.onDelete = onDelete;
    }
}
