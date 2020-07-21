package me.tomnewton.discordbot.commands.information

import me.tomnewton.discordbot.commands.Command
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.utils.Registry
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel

class PlaceholderListCommand(private val commands: Registry<String, Command>) : Command {

    override val name = "placeholders"
    override val usableInDM = true
    override val minimumArguments = 1
    override val usage = ""

    override fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>) {
        TODO("Not yet implemented")
    }
}