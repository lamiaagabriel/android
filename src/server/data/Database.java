package server.data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database<E> {
    private static String uri = "mongodb://localhost:27017/hotel", db = "hotel-desktop";

    public static <E> ObservableList<E> find(String col, Class<E> cls) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<E> collection = database.getCollection(col, cls);

            return collection.find().into(FXCollections.observableArrayList());
        } catch (MongoException e) {
            System.out.println("------------------------------------ Error -> Find()");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static <E> ArrayList<E> findArray(String col, Class<E> cls) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<E> collection = database.getCollection(col, cls);

            return collection.find().into(new ArrayList<E>());
        } catch (MongoException e) {
            System.out.println("------------------------------------ Error -> Find()");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static <E> ArrayList<E> aggregate(String col, Class<E> cls, Document filter) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<E> collection = database.getCollection(col, cls);

            return collection.aggregate(Arrays.asList(new Document("$project",
                    filter))).into(new ArrayList<E>());
        } catch (MongoException e) {
            System.out.println("------------------------------------ Error -> Find()");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static <E> E findOne(Bson filter, String col, Class<E> cls) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider));

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db).withCodecRegistry(pojoCodecRegistry);
            MongoCollection<E> collection = database.getCollection(col, cls);

            System.out.println(" ------------------------------------ Found Successfully.");
            return collection.find(filter).first();
        } catch (MongoException e) {
            System.out.println("------------------------------------ Error -> FindOne()");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Integer insertOne(String col, Document doc) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db);
            MongoCollection<Document> collection = database.getCollection(col);

            LocalDateTime now = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
            collection.insertOne(doc
                    .append("_id", findMaxID(col) + 1)
                    .append("createdAt", now)
                    .append("updatedAt", now));

            System.out.println(" -------------------------------------------- Inserted Successfully.");
        } catch (MongoException e) {
            System.out.println(" -------------------------------------------- Error -> InsertOne()");
            System.out.println(e.getMessage());
            return -1;
        }
        return (Integer) doc.get("_id");
    }

    public static boolean updateOne(String col, Integer id, Bson updates) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db);
            MongoCollection<Document> collection = database.getCollection(col);

            collection.updateOne(
                    new Document("_id", id),
                    Updates.combine(updates, Updates.currentTimestamp("updatedAt")),
                    new UpdateOptions().upsert(true));
            System.out.println(" ------------------------------------ Updated[ Successfully.");
        } catch (Exception e) {
            System.out.println(" ------------------------------------ Error In Updating.");
            return false;
        }

        return true;
    }

    public static boolean deleteOne(String col, Integer id) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(db);
            MongoCollection<Document> collection = database.getCollection(col);

            DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
            System.out.println("Deleted document count: " + result.getDeletedCount());
        } catch (Exception e) {
            System.out.println(" ------------------------------------ Error In Deleting.");
            return false;
        }

        return true;
    }

    private static Integer findMaxID(String col) {
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase(db);
        MongoCollection<Document> collection = database.getCollection(col);
        if (collection.find().sort(Sorts.descending("_id")).first() == null)
            return -1;

        return Integer.parseInt(collection.find().sort(Sorts.descending("_id")).first().get("_id").toString());
    }

}
