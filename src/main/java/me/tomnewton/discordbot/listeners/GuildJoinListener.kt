package me.tomnewton.discordbot.listeners

import me.tomnewton.discordbot.database.Database
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildJoinListener(private val database: Database) : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        // TODO add to database
    }
}