package day02

import println

fun Day02Input.task021() {
    this.prepare()
        .sumOf {
            it.ownChoice.choicePoints + it.roundOutcome.resultPoints
        }
        .println()
}

fun Day02Input.task022() {
    this.prepare()
        .sumOf {
            it.requiredChoice.choicePoints + it.requiredResult.resultPoints
        }
        .println()
}

fun main() {
    rawTestInput02.task021()
    rawInput02.task021()
    rawTestInput02.task022()
    rawInput02.task022()
}
