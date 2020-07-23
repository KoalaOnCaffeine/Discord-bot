package me.tomnewton.discordbot.commands.information

import me.tomnewton.discordbot.alerts.Alert
import me.tomnewton.discordbot.commands.Command
import me.tomnewton.discordbot.commands.arguments.Arguments
import me.tomnewton.discordbot.utils.Registry
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel

class PlaceholderListCommand(private val prefix: String, private val commands: Registry<String, Command>) : Command {

    override fun getName() = "placeholders"
    override fun getUsableInDM() = true
    override fun getMinimumArguments() = 1
    override fun getUsage() = "$prefix <command>"

    override fun execute(message: Message, channel: MessageChannel, arguments: Arguments<String>) {
        val commandName = arguments.next()
        val command = commands.get(commandName)
        if (command == null) {
            respond(message, channel, Alert {
                editEmbed {
                    setTitle("Command not found!")
                    appendDescription("**$commandName** is not a command.")
                }
            })
        } else {
            respond(message, channel, Alert {
                editEmbed {
                    setTitle("Placeholders for $commandName")
                    val description = if (command.getPlaceholders().getPlaceholders().isEmpty()) "There are no placeholders available for this command."
                    else command.getPlaceholders().joinToString { placeholder ->
                        "${placeholder.baseName}: ${placeholder.placeholders.keys.joinToString()}".let {
                            val random = command.getPlaceholders().getPlaceholders().random()
                            "$it\nFor example: {{${random.baseName}.${random.placeholders.keys.random()}}}"
                        }
                    }
                    setDescription(description)
                }
            })
        }
    }
}