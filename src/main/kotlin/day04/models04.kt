package day04

fun IntRange.contains(other: IntRange): Boolean {
    return this.first <= other.first && other.last <= this.last
}

fun IntRange.overlaps(other: IntRange): Boolean {
    return (other.contains(first) || other.contains(last)
            || contains(other.first) || contains(other.last))
}

fun List<String>.toRanges(): Pair<IntRange, IntRange> {
    if(this.size != 2) {
        error("Invalid data for range conversion")
    } else {
        return this[0].split("-").toRange() to this[1].split("-").toRange()
    }
}

fun List<String>.toRange(): IntRange {
    if(this.size != 2) {
        error("Invalid data for range conversion")
    } else {
        return this[0].toInt() .. this[1].toInt()
    }
}
