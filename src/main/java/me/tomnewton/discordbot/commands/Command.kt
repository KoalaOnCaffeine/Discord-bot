package me.tomnewton.discordbot.commands

import me.tomnewton.discordbot.alerts.Alert
import me.tomnewton.discordbot.alerts.options.simple.ContentOption
import me.tomnewton.discordbot.alerts.options.simple.TitleOption
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.utils.DelayContext
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel

private val DEFAULT_TITLE = TitleOption("No title")
private val DEFAULT_CONTENT = ContentOption("No content")

interface Command {

    fun getName(): String

    fun getUsableInDM(): Boolean = false

    fun getMinimumArguments(): Int = 0

    fun getUsage(): String = "${getName()} has an undocumented usage"

    fun getAliases(): Array<String> = emptyArray()

    fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>)

    fun respond(message: Message, channel: MessageChannel, alert: Alert, delayContext: DelayContext = DelayContext(), parameters: Array<Any> = emptyArray()) {
        alert.registerDefaults()
        // TODO: Use placeholders
        alert.send(channel).apply {
            if (delayContext.delay > 0) {
                message.deleteAfter(delayContext)
                deleteAfter(delayContext)
            }
        }
    }

    private fun Alert.registerDefaults() {
        editOptions {
            registerOption(DEFAULT_TITLE, false)
            registerOption(DEFAULT_CONTENT, false)
        }
    }

    private fun Message.deleteAfter(context: DelayContext) {
        delete().queueAfter(context.delay, context.unit)
    }

}