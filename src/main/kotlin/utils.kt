@file:Suppress("unused")

fun <T> T.println() = println(this)

fun <T,U,V> Pair<T,U>.mapFirst(action: (T) -> V): Pair<V,U> = action(this.first) to this.second
fun <T,U,V> Pair<T,U>.mapSecond(action: (U) -> V): Pair<T,V> = this.first to action(this.second)

fun <T,U,V,Z: Collection<Pair<T,U>>> Z.mapFirsts(action: (T) -> V): List<Pair<V,U>> = this.map { it.mapFirst(action) }
fun <T,U,V,Z: Collection<Pair<T,U>>> Z.mapSeconds(action: (U) -> V): List<Pair<T,V>> = this.map{ it.mapSecond(action) }


class Stack<T>(
    private val stack: ArrayDeque<T>
) {
    constructor(inputList: Collection<T>) : this(ArrayDeque(inputList))

    fun push(data: T) = stack.addLast(data)
    fun pop(): T = stack.removeLast()

    fun last(): T? = stack.lastOrNull()
    fun isEmpty(): Boolean = stack.isEmpty()
}

open class Queue<T>(
    private val internalQueue: ArrayDeque<T>
): Collection<T> by internalQueue {
    constructor(input: Collection<T>) : this(ArrayDeque(input))

    open fun enqueue(data: T) = internalQueue.addLast(data)
    fun dequeue(): T = internalQueue.removeFirst()
}

class CapacityQueue<T>(
    internalQueue: ArrayDeque<T>,
    private val capacity: Int
) : Queue<T>(internalQueue) {
    constructor(input: Collection<T>, capacity: Int) : this(ArrayDeque(input), capacity)
    constructor(capacity: Int) : this(ArrayDeque(), capacity)

    override fun enqueue(data: T) {
        shove(data)
    }

    fun shove(data: T): T? {
        super.enqueue(data)
        return if (super.size > capacity) {
            super.dequeue()
        } else {
            null
        }
    }
}

sealed interface Result {
    object FAILURE: Result
    data class SUCCESS<T>(val payload: T)
}
