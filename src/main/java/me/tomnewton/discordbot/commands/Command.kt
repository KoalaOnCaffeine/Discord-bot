package me.tomnewton.discordbot.commands

import me.tomnewton.discordbot.alerts.AlertV2
import me.tomnewton.discordbot.alerts.options.simple.ContentOption
import me.tomnewton.discordbot.alerts.options.simple.TitleOption
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.utils.DelayContext
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.User

interface Command {

    companion object {
        private val DEFAULT_TITLE = TitleOption("No title")
        private val DEFAULT_CONTENT = ContentOption("No content")

        private fun AlertV2.registerDefaults() {
            editOptions {
                registerOption(DEFAULT_TITLE, false)
                registerOption(DEFAULT_CONTENT, false)
            }
        }

    }

    val name: String

    val usableInDM: Boolean
        get() = false

    val minimumArguments: Int
        get() = 0

    val usage: String
        get() = "$name has an undocumented usage"

    val aliases: List<String>
        get() = emptyList()

    fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>)

    fun respond(message: Message, channel: MessageChannel, alert: AlertV2, delayContext: DelayContext = DelayContext()) {
        alert.registerDefaults()
        alert.send(channel).apply {
            if (delayContext.delay > 0) {
                message.deleteAfter(delayContext)
                deleteAfter(delayContext)
            }
        }
    }

    private fun Message.deleteAfter(context: DelayContext) {
        delete().queueAfter(context.delay, context.unit)
    }

}