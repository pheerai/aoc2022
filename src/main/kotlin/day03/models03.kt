package day03

typealias Rucksack = Day03Input

data class Day03Input(
    val firstCompartment: List<Char>,
    val secondCompartment: List<Char>
) {
    val firstCompartmentPriorities by lazy {  firstCompartment.map { it.toPriority() }.toSet() }
    val secondCompartmentPriorities by lazy { secondCompartment.map { it.toPriority() }.toSet() }
    val allPriorities by lazy { firstCompartmentPriorities.toSet().plus(secondCompartmentPriorities) }
    init {
        if (firstCompartment.size != secondCompartment.size) {
            error("Precondition: Compartments have same length not fulfilled")
        }
    }

    constructor(content: String): this(
            firstCompartment = content.let {
                val length = it.length
                val targetLength = length/2
                content.substring(0, targetLength)
                        .toList()
            },
            secondCompartment = content.let {
                val length = it.length
                val targetLength = length/2
                content.substring(targetLength, length)
                        .toList()
            }
    )

    private fun Char.toPriority() =
            when (code) {
                in 65..90 -> code - 38
                in 97..122 -> code - 96
                else -> error("Unexpected code point found.")
            }
}

val testInput03 by lazy { rawTestInput03.lines().map { Rucksack(it) } }
val input03 by lazy { rawInput03.lines().map { Rucksack(it)} }
