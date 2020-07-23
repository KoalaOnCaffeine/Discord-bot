package me.tomnewton.discordbot.placeholders

interface Placeholders<T> : Iterable<Placeholder<*>> {

    companion object {
        fun <T> empty(): Placeholders<T> = object : Placeholders<T> {
            override fun applyAllPlaceholders(type: T, parameters: Array<*>) {
                // Intentionally empty implementation
            }

            override fun getPlaceholders() = emptyArray<Placeholder<*>>()
        }
    }

    fun applyAllPlaceholders(type: T, parameters: Array<*>)

    fun getPlaceholders(): Array<out Placeholder<*>>


    /**
     * Returns an iterator over the elements of this object.
     */

    override fun iterator() = getPlaceholders().iterator()

}