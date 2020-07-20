package me.tomnewton.discordbot.alerts.options.simple

import me.tomnewton.discordbot.alerts.options.SimpleOption
import net.dv8tion.jda.core.EmbedBuilder

class ContentOption(value: String) : SimpleOption<String>(value) {

    override fun wouldBeOverriding(builder: EmbedBuilder): Boolean {
        return builder.ifNotEmpty {
            return@ifNotEmpty this.description != null
        } ?: false
    }

    override fun applyTo(builder: EmbedBuilder) {
        builder.setDescription(value)
    }
}