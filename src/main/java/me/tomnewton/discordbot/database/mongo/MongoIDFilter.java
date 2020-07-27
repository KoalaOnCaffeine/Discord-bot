package me.tomnewton.discordbot.database.mongo;

import kotlin.Pair;
import me.tomnewton.discordbot.database.Filter;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a filter for the mongo "_id" field
 */

public class MongoIDFilter implements Filter {

    private final Collection<Pair<String, String>> criteria;

    /**
     * A constructor to create MongoIDFilter instances
     *
     * @param id The ID to apply for the filter
     */

    public MongoIDFilter(String id) {
        criteria = Collections.singletonList(new Pair<>("_id", id));
    }

    /**
     * A getter for the criteria field
     *
     * @return The {@link #criteria} field
     */

    @Override
    public Collection<Pair<String, String>> getCriteria() {
        return criteria;
    }
}
