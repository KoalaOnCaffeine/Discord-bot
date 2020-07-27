package me.tomnewton.discordbot.database.mongo;

import me.tomnewton.discordbot.database.Model;
import org.bson.Document;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class representing a document in mongo, in the form of a {@link Model}
 */

public class MongoModel implements Model {

    private final Document document;
    private final Object id;
    private final Map<String, Object> values;

    /**
     * A constructor to create MongoModel instances
     */

    public MongoModel(Document document) {
        this.document = document;
        this.id = document.get("_id");
        this.values = document.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * A getter for the id field
     *
     * @return The {@link #id} field
     */

    @Override
    public Object getId() {
        return id;
    }

    /**
     * A method to get an Object from a specified path
     *
     * @param string The path to get the object from
     *
     * @return The object found, else null
     */

    @Override
    public Object get(String string) {
        return document.get(string);
    }

    /**
     * A getter for the values field
     *
     * @return The {@link #values} field
     */

    @Override
    public Map<String, Object> getValues() {
        return values;
    }
}
