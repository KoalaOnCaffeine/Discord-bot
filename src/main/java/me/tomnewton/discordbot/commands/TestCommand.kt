package me.tomnewton.discordbot.commands

import me.tomnewton.discordbot.alerts.Alert
import me.tomnewton.discordbot.alerts.options.simple.TitleOption
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.database.Model
import me.tomnewton.discordbot.placeholders.AlertPlaceholders
import me.tomnewton.discordbot.placeholders.MemberPlaceholder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import org.bson.Document

class TestCommand(private val guildDocuments: Map<Long, Model>) : Command {

    override fun getName() = "test"

    override fun getUsableInDM() = true

    override fun getPlaceholders() = AlertPlaceholders(MemberPlaceholder())

    override fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>) {
        respond(message, channel,
                Alert {
                    editEmbed {
                        setTitle("Moyai")
                        val guildInfo = guildDocuments[message.guild.idLong] ?: error("Guild was not found? Wtf")
                        val placeholders = guildInfo["Placeholders"] as Document
                        setDescription(placeholders.getString("Test-Format"))
                    }
                    editOptions {
                        registerOption(TitleOption("Yeet -> Overriding value"), true)
                    }
                }, parameters = arrayOf(message.author))
    }
}