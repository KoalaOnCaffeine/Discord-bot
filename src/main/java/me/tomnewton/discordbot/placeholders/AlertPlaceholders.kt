package me.tomnewton.discordbot.placeholders

import me.tomnewton.discordbot.alerts.Alert

class AlertPlaceholders(private vararg val placeholders: Placeholder<*>) : Placeholders<Alert> {

    override fun applyAllPlaceholders(type: Alert, parameters: Array<*>) {
        var description = type.builder.descriptionBuilder.toString()
        type.editEmbed {
            parameters.forEach { param ->
                getPlaceholders().forEach {
                    if (it.canApplyUsing(param)) description = it.applyToCasted(description, param)
                            ?: "Error while applying placeholders."
                }
            }
        }
        type.builder.setDescription(description)
    }

    override fun getPlaceholders(): Array<out Placeholder<*>> = placeholders
}