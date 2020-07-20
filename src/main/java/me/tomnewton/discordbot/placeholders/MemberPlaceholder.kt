package me.tomnewton.discordbot.placeholders

import net.dv8tion.jda.core.entities.Member

class MemberPlaceholder : Placeholder<Member> {

    override val baseName = "member"

    override val placeholders: Map<String, Member.() -> String> = mutableMapOf(
            "name" to Member::getEffectiveName,
            "nickname" to closure { nickname ?: effectiveName },
            "mention" to Member::getAsMention,
            "color" to closure { "#${colorRaw}" },
            "game" to closure { game?.name ?: "None" },
            "joindate" to closure { joinDate.toLocalDate().toString() },
            "status" to closure { onlineStatus.key.capitalize() })
}

