package day03

import println


fun Rucksack.getTask031Priority(): Int {
    val commonItems = this.firstCompartmentPriorities.intersect(this.secondCompartmentPriorities)
    // alternative: order, e.g. [1,4,5] and [2,5,7], take first items
    // then tak the next item for the list with smaller value until both are equal, like:
    // (1,2) -> (4,2) -> (4,5) -> (5,5) -> 5
    return when (commonItems.size) {
        1 -> commonItems.first()
        0 -> error("No Common items found for Rucksack with items ${this.firstCompartment}${this.secondCompartment}")
        else -> error("Multiple common items found for Rucksack with items ${this.firstCompartment}${this.secondCompartment}")
    }
}

fun Triple<Rucksack, Rucksack, Rucksack>.getTask032Priority(): Int {
    val r1Priorities = this.first.allPriorities
    val r2Priorities = this.second.allPriorities
    val r3Priorities = this.third.allPriorities

    val commonPriorities = r1Priorities.intersect(r2Priorities).intersect(r3Priorities)

    return when (commonPriorities.size) {
        1 -> commonPriorities.first()
        0 -> error("Encountered triple without common item")
        else -> error("Multiple or negative common items found for triple.")
    }
}

fun List<Rucksack>.task031(): Int =
        this.sumOf { it.getTask031Priority() }

fun List<Rucksack>.task032(): Int =
        this.windowed(3, 3)
                .map { Triple(it[0], it[1], it[2]) }
                .sumOf { it.getTask032Priority() }

fun main() {
    testInput03.task031().println()
    input03.task031().println()
    testInput03.task032().println()
    input03.task032().println()
}
