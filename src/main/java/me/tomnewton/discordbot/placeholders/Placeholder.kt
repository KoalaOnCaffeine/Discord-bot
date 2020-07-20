package me.tomnewton.discordbot.placeholders

interface Placeholder<T> {

    val baseName: String

    val placeholders: Map<String, T.() -> String>

    fun applyTo(string: String, using: T): String {
        var output = string
        placeholders.forEach {
            output = output.replace("{{$baseName.${it.key}}}", it.value(using))
        }
        return output
    }

    fun closure(type: T.() -> String): T.() -> String {
        return type
    }

}