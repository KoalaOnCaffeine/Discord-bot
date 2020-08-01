package me.tomnewton.discordbot

import me.tomnewton.discordbot.database.Database
import me.tomnewton.discordbot.database.mongo.MongoDatabase
import me.tomnewton.discordbot.listeners.CommandListener
import me.tomnewton.discordbot.listeners.GuildJoinListener
import me.tomnewton.discordbot.registries.CommandRegistry
import me.tomnewton.discordbot.registries.GuildInformationRegistry
import me.tomnewton.discordbot.registries.ListenerRegistry
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.requests.GatewayIntent
import org.bson.Document
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

private lateinit var bot: JDA
val parser: JSONParser = JSONParser()

private val optionsJson = String(ClassLoader.getSystemResource("options.json").readBytes())
private val guildJson = String(ClassLoader.getSystemResource("guild.json").readBytes())
private val options = parser.parse(optionsJson) as JSONObject

val database: Database = MongoDatabase.create(
        options.getValue("database.username"),
        options.getValue("database.password"),
        options.getValue("database.database_name"),
        "Collection")

private lateinit var prefix: String

/**
 * Gets a member from a user, requires the GUILD_MEMBERS intent (enable on discord developer portal)
 */

fun getMember(using: User): Member? = bot.getMutualGuilds(using).getOrNull(0)?.getMember(using)

fun main() {

    bot = JDABuilder.create(GatewayIntent.getIntents(GatewayIntent.DEFAULT.or(GatewayIntent.GUILD_MEMBERS.rawValue))).setToken(options["token"] as String).build()
    val commandRegister = CommandRegistry()
    val listenerRegistry = ListenerRegistry(bot)
    prefix = options.getValue("prefix")!!

    database.connect()

    val guilds = GuildInformationRegistry(Document.parse(guildJson), database::save)

    database.find().forEach(guilds::register)

    listenerRegistry.register(setOf(
            CommandListener(prefix, commandRegister),
            GuildJoinListener(database, guilds)
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
