package me.tomnewton.discordbot.commands.arguments.searchers

import me.tomnewton.discordbot.commands.arguments.ArgumentSearcher
import me.tomnewton.discordbot.commands.arguments.Arguments

class MentionSearcher : ArgumentSearcher<String> {

    private val mentionMatcher = "<@!\\d{18}>".toRegex()

    /**
     * Returns the found element, else null
     */

    override fun findNext(args: Arguments<String>): String? {
        while (args.hasNext()) {
            val next = args.next()
            if (next.matches(mentionMatcher))
                return next
        }
        return null
    }

    /**
     * Gets the next argument, returns the argument if it is of the right type, or else null
     */
    override fun getNext(args: Arguments<String>): String? =
            args.next().run { return if (toString().matches(mentionMatcher)) this else null }
}