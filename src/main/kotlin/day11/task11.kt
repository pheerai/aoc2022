package day11

import println

fun main() {
    val testCalculation111 = Calculation.parse(rawTestInput11)
    val calculation111 = Calculation.parse(rawInput11)
    testCalculation111.task111().println()
    calculation111.task111().println()

    val testCalculation112 = Calculation.parse(rawTestInput11)
    val calculation112 = Calculation.parse(rawInput11)
    testCalculation112.task112().println()
    calculation112.task112().println()
}
