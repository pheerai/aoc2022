@file:Suppress("unused")

fun <T> T.println() = println(this)

fun <T,U,V> Pair<T,U>.mapFirst(action: (T) -> V): Pair<V,U> = action(this.first) to this.second
fun <T,U,V> Pair<T,U>.mapSecond(action: U.() -> V): Pair<T,V> = this.first to action(this.second)

class Stack<T>(
    private val stack: ArrayDeque<T>
) {
    constructor(inputList: List<T>) : this(ArrayDeque(inputList))

    fun push(id: T) = stack.addLast(id)
    fun pop(): T = stack.removeLast()

    fun last(): T? = stack.lastOrNull()
    fun isEmpty(): Boolean = stack.isEmpty()
}
