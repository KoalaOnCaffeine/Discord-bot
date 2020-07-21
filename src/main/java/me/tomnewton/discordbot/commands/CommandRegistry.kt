package me.tomnewton.discordbot.commands

import me.tomnewton.discordbot.utils.Registry

class CommandRegistry : Registry<String, Command> {

    override val map = mutableMapOf<String, Command>()

    override fun register(value: Command) {
        map[value.name.toLowerCase()] = value
        value.aliases.map(String::toLowerCase).forEach { map[it] = value }
    }

    override fun contains(key: String) = map.containsKey(key.toLowerCase())

    override fun get(key: String) : Command? = map[key.toLowerCase()]

}