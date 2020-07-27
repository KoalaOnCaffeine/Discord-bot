package me.tomnewton.discordbot.listeners

import me.tomnewton.discordbot.commands.Command
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.registries.CommandRegistry
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

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
            // TODO send the usage still gotta do this
        } else {
            command.execute(event.message, event.channel, arguments)
        }
    }

}