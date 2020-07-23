package me.tomnewton.discordbot.listeners

import me.tomnewton.discordbot.commands.Command
import me.tomnewton.discordbot.commands.CommandRegistry
import me.tomnewton.discordbot.commands.arguments.Arguments
import net.dv8tion.jda.core.entities.ChannelType
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class CommandListener(private val prefix: String, private val commandRegistry: CommandRegistry) : ListenerAdapter() {

    private val argumentPattern = Regex(" +")

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.message.contentRaw.startsWith(prefix)) return
        val parts = event.message.contentRaw.split(argumentPattern)
        val commandName = parts[0].substring(prefix.length)
        if (!commandRegistry.contains(commandName)) return
        val command: Command = commandRegistry.get(commandName)!!
        if (event.channelType == ChannelType.PRIVATE && !command.getUsableInDM()) return
        val arguments: Arguments<String> = if (parts.size == 1)
            Arguments(emptyList()) else Arguments(parts.subList(1, parts.size))

        if (command.getMinimumArguments() > arguments.size) {
            // TODO send the usage
        } else {
            command.execute(event.message, event.channel, arguments)
        }
    }

}