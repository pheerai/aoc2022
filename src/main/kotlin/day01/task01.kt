import day01.Day01Input
import day01.rawInput01
import day01.rawTestInput01

fun Day01Input.task011() {
    this.prepare()
        .maxOfOrNull { it.sum() }
        .println()
}

fun Day01Input.task012() {
    this.prepare()
        .asSequence()
        .map { it.sum() }
        .sortedDescending()
        .windowed(3)
        .first()
        .sum()
        .println()
}


fun main() {
    rawTestInput01.task011()
    rawInput01.task011()
    rawTestInput01.task012()
    rawInput01.task012()
}
