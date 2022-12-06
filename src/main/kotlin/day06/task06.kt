package day06

import println

fun main() {
    val taskTest061 = Comm(rawTestInput061.value)
    val taskTest062 = Comm(rawTestInput062.value)
    val taskTest063 = Comm(rawTestInput063.value)
    val taskTest064 = Comm(rawTestInput064.value)
    val taskTest065 = Comm(rawTestInput065.value)
    val task06 = Comm(rawInput06.value)

    taskTest061.startPosition(4).println()
    taskTest062.startPosition(4).println()
    taskTest063.startPosition(4).println()
    taskTest064.startPosition(4).println()
    taskTest065.startPosition(4).println()

    task06.startPosition(4).println()

    taskTest061.startPosition(14).println()
    taskTest061.startPosition(14).println()
    taskTest063.startPosition(14).println()
    taskTest064.startPosition(14).println()
    taskTest065.startPosition(14).println()

    task06.startPosition(14).println()
}
