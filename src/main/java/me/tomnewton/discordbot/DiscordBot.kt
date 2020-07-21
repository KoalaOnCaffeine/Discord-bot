package me.tomnewton.discordbot

import me.tomnewton.discordbot.commands.CommandRegistry
import me.tomnewton.discordbot.commands.TestCommand
import me.tomnewton.discordbot.database.DatabaseConnector
import me.tomnewton.discordbot.database.MongooseConnector
import me.tomnewton.discordbot.listeners.CommandListener
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import org.bson.Document
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

private lateinit var bot: JDA
private val commandRegister = CommandRegistry()
val parser: JSONParser = JSONParser()

private val guilds = mutableMapOf<Long, Document>()

fun main() {
    val json = String(ClassLoader.getSystemResource("options.json").readBytes())
    val values = parser.parse(json) as JSONObject
    val connector: DatabaseConnector = MongooseConnector(
            values.getValue("database.username")!!,
            values.getValue("database.password")!!,
            values.getValue("database.database_name")!!)

    connector.connect()

    val collection = (connector as MongooseConnector).getCollection("Database.Collection")

    collection.find().forEach {
        guilds[it["Guild ID"].toString().toLong()] = it
    }

    commandRegister.register(TestCommand(guilds))

    bot = JDABuilder(values["token"] as String).build()
    bot.addEventListener(CommandListener(values.getValue("prefix")!!, commandRegister))
    bot.awaitReady()
}

inline fun <reified T> JSONObject.getValue(path: String): T? {
    if (path.contains('.')) {
        val paths = path.split(".")
        var current: JSONObject = get(paths[0]) as JSONObject
        paths.forEachIndexed { index, value ->
            if (index == 0) return@forEachIndexed
            if (index == paths.size - 1) return current[value] as T?
            current = (current[value] as JSONObject? ?: return null)
        }
        return null
    } else return getOrDefault(path, null) as T?
}
