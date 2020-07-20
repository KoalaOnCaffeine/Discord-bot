package me.tomnewton.discordbot.alerts.options.simple

import me.tomnewton.discordbot.alerts.options.SimpleOption
import net.dv8tion.jda.core.EmbedBuilder

class TitleOption(value: String) : SimpleOption<String>(value) {
    override fun wouldBeOverriding(builder: EmbedBuilder): Boolean {
        return (builder.ifNotEmpty {
            title != null
        } ?: false)
    }

    override fun applyTo(builder: EmbedBuilder) {
        builder.setTitle(value)
    }
}