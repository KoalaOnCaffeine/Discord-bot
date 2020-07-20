package me.tomnewton.discordbot.commands.arguments

/**
 * Arguments are an array of [T]s which can be iterated
 */

open class Arguments<T>(private val args: List<T>) : Iterable<T>, Iterator<T>, Collection<T> {

    private var index = 0

    /**
     * Gets the next element of the arguments, incrementing the [index] value by one
     *
    `
     * if(arguments.hasNext()) {
     *    val next = arguments.next()
     *    ...
     * }
    `
     * @return The element if present
     * @throws IndexOutOfBoundsException if the reader has no more elements
     * @see hasNext
     *
     */

    override fun next(): T {
        return args[index++]
    }

    /**
     * Acts the same as the [next] method, but will perform the provided [ifNotPresent] function if the next element is not present
     *
     * @return The element if present, else the result of the provided function
     * @see hasNext
     * @see next
     */

    fun next(ifNotPresent: () -> T): T {
        return if (hasNext()) next() else ifNotPresent()
    }

    /**
     * Reads the next element of the arguments, without incrementing the [index]
     *
     * @return The element if present
     * @throws IndexOutOfBoundsException if the reader has no more elements
     * @see readNext
     *
     */

    fun readNext(): T {
        if (!hasNext()) throw IllegalStateException("There are no more arguments to read!")
        return args[index + 1]
    }

    /**
     * Reads
     */

    fun readNext(ifNotPresent: () -> T): T {
        return if (hasNext()) readNext() else ifNotPresent()
    }

    /**
     * Reads ahead [n] elements without incrementing the [index]
     *
     * Negative values will use the same behaviour as the [readBehind] method, which just calls this method with a
     * negative [n] value
     *
     * @param n The amount of elements to read ahead
     *
     * @return The element if present
     * @throws IndexOutOfBoundsException if the reader does not contain enough elements
     * @throws IllegalArgumentException if the value [n] would cause the reader to read a sub-zero index
     * @see readBehind
     */

    fun readAhead(n: Int): T {
        if (index + n < 0) throw IllegalArgumentException("Passing '$n' to readAhead caused the reader to go negative!")
        return args[index + n]
    }

    /**
     * Reads behind [n] elements without decrementing the [index]
     *
     * Negative values will use the same behaviour as the [readAhead] method
     *
     * @param n The amount of elements to read behind
     *
     * @return The element if present
     * @throws IllegalArgumentException if the value [n] would cause the reader to read a sub-zero index
     * @see readAhead
     */

    fun readBehind(n: Int): T {
        return readAhead(-n)
    }

    /**
     * Gets the next argument as the specified type, else null
     *
     * @param searcher The [ArgumentSearcher] to use for searching
     * @param default The optional default [T] value, can be, and is by default null
     *
     * @return The element if present, else null
     */

    fun get(searcher: ArgumentSearcher<T>, default: T? = null): T? {
        return searcher.getNext(this) ?: default
    }

    fun hasNMoreElements(n: Int) = (0 <= index + n) && (index + n < size)

    /**
     * Returns the size of the collection.
     */

    override val size: Int = args.size

    /**
     * Checks if the specified element is contained in this collection.
     */
    override fun contains(element: T): Boolean = args.contains(element)

    /**
     * Checks if all elements in the specified collection are contained in this collection.
     */
    override fun containsAll(elements: Collection<T>): Boolean = args.toList().containsAll(elements)

    /**
     * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
     */
    override fun isEmpty(): Boolean = size == 1

    /**
     * Returns `true` if the iteration has more elements.
     */

    override fun hasNext(): Boolean = index < size - 1

    override fun iterator(): Iterator<T> = args.iterator()
}
