package me.tomnewton.discordbot.registries

import me.tomnewton.discordbot.database.Model

class GuildInformationRegistry(private val guildTemplate: Map<String, Any>, private val saveModel: Model.() -> Unit) : Registry<Long, Model> {

    override val map = mutableMapOf<Long, Model>()

    override fun register(value: Model) {
        if (value.updateIfNeeded(guildTemplate.toMap()))
            saveModel(value)
        map[value.getString("Guild ID").toLong()] = value
    }
}