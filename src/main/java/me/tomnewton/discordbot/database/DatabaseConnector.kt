package me.tomnewton.discordbot.database

interface DatabaseConnector {

    fun connect()

    fun getDatabase(name: String): Any?

}