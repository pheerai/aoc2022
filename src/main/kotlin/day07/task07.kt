package day07

import println

fun main() {
    val testInputShell07 = Shell()
    Command.parseFullOutput(rawTestInput07).forEach { testInputShell07.perform(it) }
    testInputShell07.getTask071().println()

    val inputShell07 = Shell()
    Command.parseFullOutput(rawInput07).forEach { inputShell07.perform(it) }
    inputShell07.getTask071().println()

    testInputShell07.getTask072().println()
    inputShell07.getTask072().println()
}
