package me.tomnewton.discordbot.placeholders

import me.tomnewton.discordbot.getMember
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User

class MemberPlaceholder : Placeholder<Member> {

    override val baseName = "member"

    override val placeholders: Map<String, Member.() -> String> = mutableMapOf(
            "name" to Member::getEffectiveName,
            "nickname" to closure { nickname ?: effectiveName },
            "mention" to Member::getAsMention,
            "color" to closure { "#${colorRaw}" },
            "game" to closure { onlineStatus.key ?: "None" },
            "joindate" to closure { timeJoined.toLocalDate().toString() },
            "status" to closure { onlineStatus.key.capitalize() })

    override fun canApplyUsing(any: Any?) = any != null && (any is User || any is Member)

    override fun convert(any: Any): Member? {
        return when (any) {
            is Member -> any
            is User -> getMember(any)
            else -> null
        }
    }
}

