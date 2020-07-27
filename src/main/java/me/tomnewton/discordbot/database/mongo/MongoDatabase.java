package me.tomnewton.discordbot.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import me.tomnewton.discordbot.database.Database;
import me.tomnewton.discordbot.database.Filter;
import me.tomnewton.discordbot.database.Model;
import org.bson.Document;

/**
 * Basic mongo implementation of a {@link Database}
 */

public class MongoDatabase implements Database {

    private final MongoClient client;
    private final MongoDatabaseConnector connector;

    /**
     * A constructor to make an instance of a {@link MongoDatabase}
     * The URL is not treated specially, this constructor must take in the one with the username, password and
     * database included
     *
     * @param url            The full URL to connect to
     * @param databaseName   The database name to connect to for the {@link MongoDatabaseConnector}
     * @param collectionName The collection name to connect for the {@link MongoDatabaseConnector}
     */

    public MongoDatabase(String url, String databaseName, String collectionName) {
        this.client = new MongoClient(new MongoClientURI(url));
        this.connector = new MongoDatabaseConnector(url, databaseName, collectionName);
    }

    /**
     * Creates a {@link MongoDatabase} instance using the provided fields
     * The username, password and database name are put into the default URL for mongo databases
     *
     * @param username       The username of the database
     * @param password       The password of the database
     * @param databaseName   The name of the database
     * @param collectionName The collection of the database
     *
     * @return The created {@link MongoDatabase} instance
     */

    public static MongoDatabase create(String username, String password, String databaseName, String collectionName) {
        return new MongoDatabase(String.format("mongodb+srv://%s:%s@database-xjonx.gcp.mongodb" +
            ".net/%s?retryWrites=true&w=majority", username, password, databaseName), databaseName, collectionName);
    }

    /**
     * A method to get the underlying {@link MongoDatabaseConnector} for the {@link MongoDatabase}
     *
     * @return The underlying {@link MongoDatabaseConnector}
     */

    @Override
    public MongoDatabaseConnector getConnector() {
        return connector;
    }

    /**
     * A method to find one object matching the provided {@link Filter}
     *
     * @param filter The {@link Filter} to apply to the search
     *
     * @return The found object, or null if not present
     */
    @Override
    public Object findOne(Filter filter) {
        return connector.getCollection().find(filter).first();
    }

    /**
     * A method to get all of the objects in a database
     *
     * @return All of the present {@link Model Models} as an {@link Iterable}
     */
    @Override
    public Iterable<Model> find() {
        return connector.getCollection().find().map(MongoModel::new);
    }

    /**
     * A method to get all of the objects in a database matching the specified {@link Filter}
     *
     * @param filter The {@link Filter} to apply to the search
     *
     * @return All of the present {@link Model Models} as an {@link Iterable}
     *
     * @see #find()
     */
    @Override
    public Iterable<Model> find(Filter filter) {
        return connector.getCollection().find(filter).map(MongoModel::new);
    }

    /**
     * A method to save a provided {@link Model}
     *
     * @param model The {@link Model} to save
     */
    @Override
    public void save(Model model) {
        connector.getCollection().insertOne(new Document(model.getValues()));
    }

    /**
     * A method to get the underlying {@link MongoClient} for this
     *
     * @return The underlying {@link MongoClient}
     */

    public MongoClient getClient() {
        return client;
    }
}
