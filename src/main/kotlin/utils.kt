@file:Suppress("unused")

fun <T> T.println() = println(this)

fun <T,U,V> Pair<T,U>.mapFirst(action: (T) -> V): Pair<V,U> = action(this.first) to this.second
fun <T,U,V> Pair<T,U>.mapSecond(action: U.() -> V): Pair<T,V> = this.first to action(this.second)
