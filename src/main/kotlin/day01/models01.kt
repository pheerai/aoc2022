package day01

@JvmInline
value class Day01Input(private val value: String) {
    fun prepare(): List<List<Long>> = value.split("\n\n")
        .map { it.lines().map { s -> s.toLong() } }
}
