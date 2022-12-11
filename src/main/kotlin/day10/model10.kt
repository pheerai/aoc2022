package day10

import mapSeconds
import println

@JvmInline
value class CPURegister(val value: Int)

data class CPU(val x: CPURegister = CPURegister(1))
data class CPUTick(val cpuAtStart: CPU, val cpuAtEnd: CPU) {
    fun identity() = CPUTick(this.cpuAtEnd, this.cpuAtEnd)
    fun map(op: (CPU) -> CPU) = CPUTick(cpuAtEnd, op(cpuAtEnd))

    companion object {
        fun initial() = CPUTick(CPU(), CPU())
    }
}

typealias CPUAction = (CPUTick) -> CPUTick

sealed interface Instruction {
    fun ticks(): Sequence<CPUAction> {
        return sequenceOf(CPUTick::identity)
    }

    object NOOP : Instruction
    class ADDX(val arg: Int) : Instruction {
        override fun ticks(): Sequence<CPUAction> = sequenceOf(
            CPUTick::identity,
            { v -> v.map { c -> c.copy(x = CPURegister(c.x.value + arg)) } }
        )
    }

    companion object {
        fun parse(line: String): Instruction {
            val argv = line.split(" ")
            return when (argv[0]) {
                "noop" -> NOOP
                "addx" -> ADDX(argv[1].toInt())
                else -> error("Parser error")
            }
        }
    }
}

fun InputDay10.testTask101(): Int {
    this.value
        .lines()
        .asSequence()
        .map { Instruction.parse(it) }
        .flatMap { it.ticks() }
        .runningFold(CPUTick.initial()) { acc, function ->
            function(acc)
        }
        .drop(1)
        .withIndex()
        .forEach { println("Tick ${it.index}: ${it.value.cpuAtStart} -> ${it.value.cpuAtEnd}") }
    return 0
}

fun InputDay10.task101(): Int {
    return this.value
        .lines()
        .asSequence()
        .map { Instruction.parse(it) }
        .flatMap { it.ticks() }
        .runningFold(CPUTick.initial()) { acc, function ->
            function(acc)
        }
        .withIndex()
        .drop(20)
        .windowed(1, 40)
        .flatten()
//        .onEach { println("Tick ${it.index}: ${it.value.cpuAtStart} -> ${it.value.cpuAtEnd}") }
        .map { it.index * it.value.cpuAtStart.x.value }
        .sum()
}

fun InputDay10.task102() {
    val spritePositions = this.value
        .lines()
        .asSequence()
        .map { Instruction.parse(it) }
        .flatMap { it.ticks() }
        .runningFold(CPUTick.initial()) { acc, function ->
            function(acc)
        }
        .withIndex()
        .drop(1)
        .map { it.value.cpuAtStart.x }
        .map { it.value.mod(40) }
    spritePositions.count().println()
    val crtPosition = (0 until 240).asSequence()

    crtPosition.zip(spritePositions)
        .mapSeconds { (it - 1)..(it + 1) }
        .mapSeconds { it.map { i -> i.mod(40) } }
        .map { it.first.mod(40) in it.second }
        .map {
            if (it) {
                "█"
            } else {
                " "
            }
        }
        .windowed(40, 40)
        .map { it.joinToString("") }
        .joinToString(System.lineSeparator())
        .println()
}
