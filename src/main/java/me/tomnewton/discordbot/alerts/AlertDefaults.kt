package me.tomnewton.discordbot.alerts

import me.tomnewton.discordbot.alerts.options.Option
import net.dv8tion.jda.core.EmbedBuilder

class AlertDefaults {

    private val defaults: MutableMap<Option<*>, Boolean> = mutableMapOf()

    fun <T> registerOption(option: Option<T>, shouldOverwriteOtherOptions: Boolean = false) {
        defaults[option] = shouldOverwriteOtherOptions
    }

    fun applyTo(builder: EmbedBuilder) {
        defaults.forEach {
            if (it.value || !it.key.wouldBeOverriding(builder)) it.key.applyTo(builder)
        }
    }

}
