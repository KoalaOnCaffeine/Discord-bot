package me.tomnewton.discordbot.commands.arguments

/**
 *
 */

interface ArgumentSearcher<T> {

    /**
     * Returns the found element, else null
     */

    fun findNext(args: Arguments<T>): T?

    /**
     * Gets the next argument, returns the argument if it is of the right type, or else null
     */

    fun getNext(args: Arguments<T>): T?

}