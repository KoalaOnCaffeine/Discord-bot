package me.tomnewton.discordbot.registries

interface Registry<K, V> {

    val map: Map<K, V>

    fun register(value: V)

    fun contains(key: K) = map.containsKey(key)

    operator fun get(key: K): V? = map[key]

}