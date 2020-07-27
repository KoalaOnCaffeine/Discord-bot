package me.tomnewton.discordbot.registries

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.hooks.ListenerAdapter
import kotlin.reflect.KClass

class ListenerRegistry(private val jda: JDA) : Registry<KClass<out ListenerAdapter>, Set<ListenerAdapter>> {

    override val map: MutableMap<KClass<out ListenerAdapter>, Set<ListenerAdapter>> = mutableMapOf()

    override fun register(value: Set<ListenerAdapter>) {
        value.forEach {
            if (!map.getOrDefault(it::class, setOf()).contains(it)) {
                map[it::class] = (map.getOrDefault(it::class, mutableSetOf()) + it)
                jda.addEventListener(it)
            }
        }
    }
}