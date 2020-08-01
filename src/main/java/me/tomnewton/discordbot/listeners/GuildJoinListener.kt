package me.tomnewton.discordbot.listeners

import me.tomnewton.discordbot.database.Database
import me.tomnewton.discordbot.database.Model
import me.tomnewton.discordbot.database.models.DefaultGuildModel
import me.tomnewton.discordbot.registries.Registry
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildJoinListener(private val database: Database, private val guilds: Registry<Long, Model>) : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        val id = event.guild.idLong
        if (!guilds.contains(id)) {
            val newGuildModel = DefaultGuildModel(id)
            guilds.register(newGuildModel)
            database.save(newGuildModel)
        }
    }
}