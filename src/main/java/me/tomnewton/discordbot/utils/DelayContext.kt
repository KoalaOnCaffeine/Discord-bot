package me.tomnewton.discordbot.utils

import java.util.concurrent.TimeUnit

data class DelayContext(val delay: Long = 0, val unit: TimeUnit = TimeUnit.SECONDS)