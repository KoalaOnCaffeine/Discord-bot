package me.tomnewton.discordbot.commands

import me.tomnewton.discordbot.alerts.AlertV2
import me.tomnewton.discordbot.alerts.options.simple.TitleOption
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.placeholders.MemberPlaceholder
import me.tomnewton.discordbot.utils.DelayContext
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import org.bson.Document
import java.util.concurrent.TimeUnit

class TestCommand(private val guildDocuments: Map<Long, Document>) : Command {

    override val name = "test"

    override val usableInDM = true

    override fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>) {
        respond(message, channel,
                AlertV2 {
                    editEmbed {
                        setTitle("Moyai")
                        val guildInfo = guildDocuments[message.guild.idLong] ?: error("Guild was not found? Wtf")
                        val placeholders = guildInfo["Placeholders"] as Document
                        setDescription(MemberPlaceholder().applyTo(placeholders.getString("Test-Format"), message.member))
                    }
                    editOptions {
                        registerOption(TitleOption("Yeet -> Overriding value"), true)
                    }
                }/*,
                DelayContext(5, TimeUnit.SECONDS)*/)
    }
}