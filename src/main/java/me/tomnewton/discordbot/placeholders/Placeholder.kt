package me.tomnewton.discordbot.placeholders

interface Placeholder<T> {

    val baseName: String

    val placeholders: Map<String, T.() -> String>

    fun canApplyUsing(any: Any?): Boolean

    fun convert(any: Any): T?

    fun applyTo(string: String, using: T): String {
        var output = string
        placeholders.forEach {
            output = output.replace("{{$baseName.${it.key}}}", it.value(using))
        }
        return output
    }

    fun applyToCasted(string: String, using: Any?) = if (canApplyUsing(using)) applyTo(string, using?.let { convert(it) }!!) else null

    fun closure(type: T.() -> String): T.() -> String {
        return type
    }

}