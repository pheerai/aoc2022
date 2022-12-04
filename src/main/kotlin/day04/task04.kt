package day04

import println

fun List<Pair<IntRange, IntRange>>.task041(): Int {
    return this.abstractTask { it.first.contains(it.second) || it.second.contains(it.first) }
}

fun List<Pair<IntRange, IntRange>>.task042(): Int {
    return this.abstractTask { it.first.overlaps(it.second) }
}

fun List<Pair<IntRange, IntRange>>.abstractTask(action: (Pair<IntRange, IntRange>) -> Boolean): Int {
    return this.count { action(it) }
}

fun Day04Input.parse(): List<Pair<IntRange, IntRange>> {
    return this.value
        .lines()
        .map{ it.split(",") }
        .map { it.toRanges()  }
}
fun main() {
    val testInput04 = rawTestInput04.parse()
    val input04 = rawInput04.parse()
    testInput04.task041().println()
    input04.task041().println()
    testInput04.task042().println()
    input04.task042().println()
}
