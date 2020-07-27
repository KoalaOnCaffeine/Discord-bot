package me.tomnewton.discordbot.registries

import me.tomnewton.discordbot.commands.Command

class CommandRegistry : Registry<String, Command> {

    override val map = mutableMapOf<String, Command>()

    override fun register(value: Command) {
        map[value.getName().toLowerCase()] = value
        value.getAliases().map(String::toLowerCase).forEach { map[it] = value }
    }

    override fun contains(key: String) = map.containsKey(key.toLowerCase())

    override fun get(key: String): Command? = map[key.toLowerCase()]

}