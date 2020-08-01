package me.tomnewton.discordbot.database.models;

import me.tomnewton.discordbot.database.Model;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultGuildModel implements Model {

    private static final Document GUILD_DOCUMENT;

    static {
        Document _GUILD_DOCUMENT = null;
        try {
            final String json = new String(ClassLoader.getSystemResource("guild.json").openStream().readAllBytes());
            _GUILD_DOCUMENT = Document.parse(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUILD_DOCUMENT = _GUILD_DOCUMENT;
    }

    private final String id = ObjectId.get().toString();
    private Map<String, Object> values =
        GUILD_DOCUMENT.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public DefaultGuildModel(long guildID) {
        set("_id", ObjectId.get());
        set("Guild ID", guildID);
    }

    /**
     * A method to get the identifier of this model
     *
     * @return The model's identifier
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * A method to get some value from the model
     *
     * @param string The path of the object to get
     *
     * @return The found object if present, else null
     */
    @Override
    public Object get(String string) {
        return values.get(string);
    }

    /**
     * A method to set a string path to a value
     *
     * @param string The path of the object
     * @param value  The value to set it to
     */
    @Override
    public void set(String string, Object value) {
        values.put(string, value);
    }

    /**
     * A method to get all of the values in the model
     *
     * @return All of the values in a map
     */
    @Override
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * A method to set all of the values in the model
     *
     * @param values The values to set for the model
     */
    @Override
    public void setValues(Map<String, Object> values) {
        this.values = values;
    }


}
