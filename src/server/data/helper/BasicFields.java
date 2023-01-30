package server.data.helper;

import org.bson.Document;

public abstract class BasicFields {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract Document toDocument();

}
