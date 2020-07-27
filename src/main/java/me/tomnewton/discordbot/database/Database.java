package me.tomnewton.discordbot.database;

/**
 * Represents a basic database
 */

public interface Database {

    /**
     * A method to get the underlying {@link DatabaseConnector} of a database
     *
     * @return The {@link DatabaseConnector}
     */

    DatabaseConnector getConnector();

    /**
     * A method to find one object matching the provided {@link Filter}
     *
     * @param filter The {@link Filter} to apply to the search
     *
     * @return The found object, or null if not present
     */

    Object findOne(Filter filter);

    /**
     * A method to get all of the objects in a database
     *
     * @return All of the present {@link Model Models} as an {@link Iterable}
     */

    Iterable<Model> find();

    /**
     * A method to get all of the objects in a database matching the specified {@link Filter}
     *
     * @param filter The {@link Filter} to apply to the search
     *
     * @return All of the present {@link Model Models} as an {@link Iterable}
     *
     * @see #find()
     */

    Iterable<Model> find(Filter filter);

    /**
     * A method to save a provided {@link Model}
     *
     * @param model The {@link Model} to save
     */

    void save(Model model);

    /**
     * A delegation method to connect to the database via the {@link #getConnector()} method
     *
     * @see DatabaseConnector#connect()
     */

    default void connect() {
        getConnector().connect();
    }

    /**
     * A delegation method to disconnect from the database via the {@link #getConnector()} method
     *
     * @see DatabaseConnector#disconnect()
     */

    default void disconnect() {
        getConnector().disconnect();
    }

}
