package me.tomnewton.discordbot.database;

import java.util.Map;

/**
 * Represents an entry into a database, such as a Document in mongo or a Row in SQL
 */

public interface Model {

    /**
     * A method to get the identifier of this model
     *
     * @return The model's identifier
     */

    Object getId();

    /**
     * A method to get some value from the model
     *
     * @param string The path of the object to get
     *
     * @return The found object if present, else null
     */

    Object get(String string);

    /**
     * A method to get some value from the model as a String
     *
     * @param string The path of the object to get
     *
     * @return The found object as a String, else <code>"null"</code>
     *
     * @see Model#get(String)
     * @see Object#toString()
     */

    default String getString(String string) {
        final Object value = get(string);
        if (value == null) return "null";
        return value.toString();
    }

    /**
     * A method to set a string path to a value
     *
     * @param string The path of the object
     * @param value  The value to set it to
     */

    void set(String string, Object value);

    /**
     * A method to get all of the values in the model
     *
     * @return All of the values in a map
     */

    Map<String, Object> getValues();

}