@file:Suppress("unused")

fun <T> T.println() = println(this)

fun <T,U,V> Pair<T,U>.mapFirst(action: (T) -> V): Pair<V,U> = action(this.first) to this.second
fun <T,U,V> Pair<T,U>.mapSecond(action: (U) -> V): Pair<T,V> = this.first to action(this.second)

fun <T,U,V,Z: Collection<Pair<T,U>>> Z.mapFirsts(action: (T) -> V): List<Pair<V,U>> = this.map { it.mapFirst(action) }
fun <T,U,V,Z: Collection<Pair<T,U>>> Z.mapSeconds(action: (U) -> V): List<Pair<T,V>> = this.map{ it.mapSecond(action) }


class Stack<T>(
    private val stack: ArrayDeque<T>
) {
    constructor(inputList: List<T>) : this(ArrayDeque(inputList))

    fun push(id: T) = stack.addLast(id)
    fun pop(): T = stack.removeLast()

    fun last(): T? = stack.lastOrNull()
    fun isEmpty(): Boolean = stack.isEmpty()
}
