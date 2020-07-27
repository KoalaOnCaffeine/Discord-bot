package me.tomnewton.discordbot

import me.tomnewton.discordbot.commands.TestCommand
import me.tomnewton.discordbot.commands.information.PlaceholderListCommand
import me.tomnewton.discordbot.database.Database
import me.tomnewton.discordbot.database.Model
import me.tomnewton.discordbot.database.mongo.MongoDatabase
import me.tomnewton.discordbot.listeners.CommandListener
import me.tomnewton.discordbot.listeners.GuildJoinListener
import me.tomnewton.discordbot.registries.CommandRegistry
import me.tomnewton.discordbot.registries.ListenerRegistry
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.requests.GatewayIntent
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

private lateinit var bot: JDA
val parser: JSONParser = JSONParser()

private val guilds = mutableMapOf<Long, Model>()
private val json = String(ClassLoader.getSystemResource("options.json").readBytes())
private val values = parser.parse(json) as JSONObject

private lateinit var prefix: String

fun getMember(using: User): Member? = bot.getMutualGuilds(using).getOrNull(0)?.getMember(using)

fun main() {
    bot = JDABuilder.create(GatewayIntent.getIntents(GatewayIntent.DEFAULT)).setToken(values["token"] as String).build()
    val commandRegister = CommandRegistry()
    val listenerRegistry = ListenerRegistry(bot)
    prefix = values.getValue("prefix")!!

    val database: Database = MongoDatabase.create(
            values.getValue("database.username")!!,
            values.getValue("database.password")!!,
            values.getValue("database.database_name")!!, "Collection")

    database.connect()

    database.find().forEach {
        guilds[it.get("Guild ID").toString().toLong()] = it
    }

    database.find().forEach {
        println(it.values)
    }

    commandRegister.register(TestCommand(guilds))
    commandRegister.register(PlaceholderListCommand(prefix, commandRegister))

    listenerRegistry.register(setOf(
            CommandListener(prefix, commandRegister),
            GuildJoinListener(database)
    ))

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
