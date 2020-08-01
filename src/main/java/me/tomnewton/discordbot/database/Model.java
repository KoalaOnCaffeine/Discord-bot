package me.tomnewton.discordbot.database;

import me.tomnewton.discordbot.utils.MutableBoolean;

import java.util.Map;
import java.util.function.Function;

/**
 * Represents an entry into a database, such as a Document in mongo or a Row in SQL
 */

public interface Model {

    /**
     * Updates the provided model {@link Map} (always {@link Model#getValues()}) to match the provided updateTo
     * {@link Map}, leaving values that are the same type alone, but replacing them if they are not
     * This method was safe to suppress because checking the instance means that both of the parameters are safe to
     * be casted
     *
     * @param model    The {@link Map} to update
     * @param updateTo The {@link Map} for the tempalte
     *
     * @return Whether the model was updated
     */
    @SuppressWarnings("unchecked")
    private static boolean updateIfNeeded(Map<String, Object> model, Map<String, Object> updateTo) {
        final MutableBoolean updated = new MutableBoolean();
        updateTo.forEach((key, value) -> {
            // If the template key isn't there, set the value in the map to the default from the template
            // If it did have the key, it just stays as its own key (doesn't overwrite it)
            if (model.compute(key, (modelKey, modelObject) -> {
                // If the object isn't there or its not the right type, return the template's value
                if (modelObject == null || !modelObject.getClass().isInstance(value)) {
                    updated.set(true);
                    return value;
                }
                // Otherwise just let it keep its value
                return modelObject;

            }) instanceof Map)
                updated.orEquals(updateIfNeeded(((Map<String, Object>) model.get(key)),
                    (Map<String, Object>) value));
            // If that value that was set is a map, check if that needs an update (missing values etc)
            // update.orEquals
        });
        return updated.get();
    }

    /**
     * A static method to create a basic model instance
     *
     * @param values     The values to base the model off of
     * @param valuesToID A function for the values parameter to return the ID for the model
     *
     * @return The new model instance
     */

    static Model of(Map<String, Object> values, Function<Map<String, Object>, String> valuesToID) {
        return new Model() {
            private final String id = valuesToID.apply(values);
            private Map<String, Object> map = values;

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Object get(String string) {
                return map.get(string);
            }

            @Override
            public void set(String string, Object value) {
                map.put(string, value);
            }

            @Override
            public Map<String, Object> getValues() {
                return map;
            }

            @Override
            public void setValues(Map<String, Object> values) {
                map = values;
            }
        };
    }

    /**
     * A default method for the {@link #updateIfNeeded(Map, Map)}
     *
     * @param template The template map to base off of
     *
     * @return Whether the map was updated
     */

    default boolean updateIfNeeded(Map<String, Object> template) {
        return updateIfNeeded(getValues(), template);
    }

    /**
     * A method to get the identifier of this model
     *
     * @return The model's identifier
     */

    String getId();

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

    /**
     * A method to set all of the values in the model
     *
     * @param values The values to set for the model
     */

    void setValues(Map<String, Object> values);

}