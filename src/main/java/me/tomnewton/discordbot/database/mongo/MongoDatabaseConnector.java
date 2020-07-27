package me.tomnewton.discordbot.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.tomnewton.discordbot.database.DatabaseConnector;
import org.bson.Document;

/**
 * A class representing a {@link DatabaseConnector} for mongo
 */

public class MongoDatabaseConnector implements DatabaseConnector {

    private final String address;
    private final String databaseName;
    private final String collectionName;

    private final MongoClient client;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;

    /**
     * A constructor to create a {@link MongoDatabaseConnector}
     *
     * @param address        The URL to connect to
     * @param databaseName   The database name
     * @param collectionName The collection name
     */

    public MongoDatabaseConnector(String address, String databaseName, String collectionName) {
        this.address = address;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
        this.client = new MongoClient(new MongoClientURI(address));
    }

    /**
     * A method to connect to the {@link me.tomnewton.discordbot.database.mongo.MongoDatabase}
     */

    public void connect() {
        client.startSession();
        mongoDatabase = client.getDatabase(databaseName);
        collection = mongoDatabase.getCollection(collectionName);
    }

    /**
     * A method to disconnect from the {@link me.tomnewton.discordbot.database.mongo.MongoDatabase}
     */

    public void disconnect() {
        client.close();
    }

    /**
     * A getter for the address field
     *
     * @return The {@link #address} field
     */

    public String getAddress() {
        return address;
    }

    /**
     * A getter for the databaseName field
     *
     * @return The {@link #databaseName} field
     */

    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * A getter for the collectionName field
     *
     * @return The {@link #collectionName} field
     */

    public String getCollectionName() {
        return collectionName;
    }

    /**
     * A getter for the client field
     *
     * @return The {@link #client} field
     */

    public MongoClient getClient() {
        return client;
    }

    /**
     * A getter for the mongoDatabase field
     *
     * @return The {@link #mongoDatabase} field
     */

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    /**
     * A getter for the collection field
     *
     * @return The {@link #collection} field
     */

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
