package me.tomnewton.discordbot.database;

/**
 * Represents something which can connect and disconnect from a database
 */

public interface DatabaseConnector {

    /**
     * A method to connect to a database
     */

    void connect();

    /**
     * A method to disconnect from a database
     */

    void disconnect();

}
