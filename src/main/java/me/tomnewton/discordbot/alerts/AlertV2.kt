package me.tomnewton.discordbot.alerts

import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageChannel

class AlertV2(apply: AlertV2.() -> Unit = {}) {

    private val builder = EmbedBuilder()
    private val options = AlertDefaults()
    private var sent = false
    private lateinit var message: Message

    init {
        apply(this)
    }

    /**
     * A higher-order way of editing the [AlertDefaults]
     */

    fun editOptions(block: AlertDefaults.() -> Unit) {
        block(options)
    }

    /**
     * A higher-order way of editing the [EmbedBuilder]
     */

    fun editEmbed(block: EmbedBuilder.() -> Unit) {
        block(builder)
    }

    /**
     * Sends the message to the specified [channel] parameter and returns the results
     * If the message has already been sent, the [message] field will be returned instead, leading to the initially send message
     * (Avoid api spam if something goes wrong)
     *
     * @return The sent message, or the original [message] if it has already been sent
     */

    fun send(channel: MessageChannel): Message {
        if (sent) return message
        options.applyTo(builder)
        return channel.sendMessage(builder.build()).complete().apply {
            message = this
            sent = true
        }
    }


}