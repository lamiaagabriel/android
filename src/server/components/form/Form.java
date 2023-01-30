package server.components.form;

import java.io.IOException;
import java.util.Map;

import org.bson.Document;

import javafx.beans.DefaultProperty;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.Launcher;
import server.data.helper.Callable;

@DefaultProperty(value = "blocks")
public class Form extends BorderPane {
    private Document fields;

    @FXML
    private HBox content;
    @FXML
    private Button submit, clear;

    public Form() {
        try {
            FXMLLoader fxmlLoader = Launcher.loadFXML("components/form");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Error in Loading form.fxml " + e.getMessage());
        }
        fields = new Document();
        clear.setOnAction(e -> clear());
        content.getChildren().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable e) {
                getFields(content.getChildren());
            };
        });
    }

    public ObservableList<Node> getBlocks() {
        return content.getChildren();
    }

    public void setBlocks(ObservableList<Node> node) {
        content.getChildren().setAll(node);
    }

    public Document getFields() {
        return fields;
    }

    public void setSubmitText(String label) {
        submit.setText(label);
    }

    public void setSubmitAction(Callable method) {
        submit.setOnAction(e -> method.call());
    }

    public void clear() {
        for (Map.Entry field : fields.entrySet())
            if (field.getValue() != null)
                ((InputField) field.getValue()).reset();
    }

    public boolean isAllFilled() {
        for (Map.Entry field : fields.entrySet())
            if (field.getValue() != null)
                if (((InputField) field.getValue()).isEmpty())
                    return false;

        return true;
    }

    private void getFields(ObservableList<Node> children) {
        for (Object obj : children.toArray()) {
            if (obj instanceof InputField && !fields.containsKey(((InputField) obj).getId()))
                fields.append(((InputField) obj).getId(), ((InputField) obj));
            else if (obj instanceof Block)
                getFields(((Block) obj).getChildren());
            else if (obj instanceof HBox)
                getFields(((HBox) obj).getChildren());
            else if (obj instanceof VBox)
                getFields(((VBox) obj).getChildren());

        }
    }
}
