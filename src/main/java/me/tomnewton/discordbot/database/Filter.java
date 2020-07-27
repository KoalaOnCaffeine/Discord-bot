package me.tomnewton.discordbot.database;

import kotlin.Pair;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents a filter for searching through a database
 */

public interface Filter extends Bson {

    /**
     * A method showing the keys and values required to be accepted by the filter
     *
     * @return The Pairs of Strings defining the filter
     */

    Collection<Pair<String, String>> getCriteria();

    /**
     * Render the filter into a BsonDocument.
     *
     * @param tDocumentClass the document class in scope for the collection.  This parameter may be ignored, but it may be used to alter
     *                       the structure of the returned {@code BsonDocument} based on some knowledge of the document class.
     * @param codecRegistry  the codec registry.  This parameter may be ignored, but it may be used to look up {@code Codec} instances for
     *                       the document class or any other related class.
     *
     * @return the BsonDocument
     */
    default <T> BsonDocument toBsonDocument(Class<T> tDocumentClass, CodecRegistry codecRegistry) {
        return BsonDocument.parse("{\n" + String.join(",\n",
            getCriteria().stream().map(this::mapPairToJson).collect(Collectors.toSet())) +
            "\n}");
    }

    /**
     * Maps a pair of strings to a json format, resulting in:
     * "<code>pair.getFirst()</code>": "<code>pair.getSecond()</code>"
     *
     * @param pair The pair to convert into json
     *
     * @return The json string form of the pair
     */

    private String mapPairToJson(Pair<String, String> pair) {
        return String.format("\"%s\": \"%s\"", pair.getFirst(), pair.getSecond());
    }

}
