package me.tomnewton.discordbot.alerts.options

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

interface Option<T> {
    val value: T?
    fun wouldBeOverriding(builder: EmbedBuilder): Boolean
    fun applyTo(builder: EmbedBuilder)
    fun <E> EmbedBuilder.ifNotEmpty(block: MessageEmbed.() -> E): E? {
        if (isEmpty) return null
        return block(build())
    }
}