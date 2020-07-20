package me.tomnewton.discordbot.commands

class CommandRegister {

    private val commands = mutableMapOf<String, Command>()

    fun register(command: Command) {
        commands[command.name.toLowerCase()] = command
        command.aliases.map(String::toLowerCase).forEach { commands[it] = command }
    }

    fun hasCommand(name: String) = commands.containsKey(name.toLowerCase())

    fun getCommand(name: String) : Command? = commands[name.toLowerCase()]

}