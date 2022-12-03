package day02

enum class RPSChoice(val choicePoints: Int) {
    ROCK(1),
    PAPER(2),
    SCISSOR(3);

    companion object {
        fun parse(choice: String): RPSChoice {
            return when (choice) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSOR
                else -> error("Unknown choice encountered")
            }
        }
    }
}

enum class RPSResult(val resultPoints: Int) {
    OPPONENT_WINS(0),
    YOU_WIN(6),
    DRAW(3);

    companion object {
        fun parse(input: String): RPSResult {
            return when (input) {
                "X" -> OPPONENT_WINS
                "Y" -> DRAW
                "Z" -> YOU_WIN
                else -> error("Unknown result encountered")
            }
        }
    }
}

data class RPSRound(
    val opponentChoice: RPSChoice,
    val ownChoice: RPSChoice,
    val requiredResult: RPSResult
) {
    val roundOutcome by lazy { this.evalRound() }
    val requiredChoice by lazy { this.findRequiredChoice() }

    private fun evalRound(): RPSResult {
        // Alternatively: assign 0,1,2 to R,P,S, create difference, mod3: 0 = draw, 1: A wins, -1: B wins
        return when (opponentChoice) {
            RPSChoice.ROCK -> when (ownChoice) {
                RPSChoice.ROCK -> RPSResult.DRAW
                RPSChoice.PAPER -> RPSResult.YOU_WIN
                RPSChoice.SCISSOR -> RPSResult.OPPONENT_WINS
            }

            RPSChoice.PAPER -> when (ownChoice) {
                RPSChoice.ROCK -> RPSResult.OPPONENT_WINS
                RPSChoice.PAPER -> RPSResult.DRAW
                RPSChoice.SCISSOR -> RPSResult.YOU_WIN
            }

            RPSChoice.SCISSOR -> when (ownChoice) {
                RPSChoice.ROCK -> RPSResult.YOU_WIN
                RPSChoice.PAPER -> RPSResult.OPPONENT_WINS
                RPSChoice.SCISSOR -> RPSResult.DRAW
            }
        }
    }

    private fun findRequiredChoice(): RPSChoice {
        return when (requiredResult) {
            RPSResult.YOU_WIN -> when (opponentChoice) {
                RPSChoice.ROCK -> RPSChoice.PAPER
                RPSChoice.PAPER -> RPSChoice.SCISSOR
                RPSChoice.SCISSOR -> RPSChoice.ROCK
            }

            RPSResult.OPPONENT_WINS -> when (opponentChoice) {
                RPSChoice.ROCK -> RPSChoice.SCISSOR
                RPSChoice.PAPER -> RPSChoice.ROCK
                RPSChoice.SCISSOR -> RPSChoice.PAPER
            }

            RPSResult.DRAW -> opponentChoice
        }
    }
}

@JvmInline
value class Day02Input(private val value: String) {
    fun prepare(): List<RPSRound> {
        return value.lines()
            .map { it.split(" ") }
            .map {
                RPSRound(
                    RPSChoice.parse(it[0]),
                    RPSChoice.parse(it[1]),
                    RPSResult.parse(it[1]),
                )
            }
    }
}

//region rawInput
val rawTestInput02: Day02Input = Day02Input(
    """A Y
B X
C Z"""
)

val rawInput02: Day02Input = Day02Input(
    """B Z
C Z
B X
A Y
B X
B X
A X
B Z
C Z
B Y
A Z
C X
B X
C X
B Z
B Z
C Y
B Z
B Z
C Z
B Z
B Y
B X
B Y
C Z
C Y
C Z
A X
C Z
B X
C Z
B Y
B X
A Y
A X
A Y
B Y
B X
B X
A Z
B Z
B Y
C Z
B X
C Y
B Z
B Y
C Y
A X
A Y
C Y
C Z
B Z
B X
C Z
A X
B X
A Y
B Z
C Y
A Y
C Z
C Z
A X
B X
C Z
A Z
A Z
B X
B X
B X
A Y
B X
B X
C Y
B X
C Z
C Y
B Z
A X
B X
B X
A X
C Y
C Y
A X
A X
B Z
B X
C Z
B X
B Z
A Z
B Z
A X
A X
B Z
A X
B X
B X
B X
A Y
A Y
A Y
B X
C Y
B Z
A Y
B X
A Z
C X
A Z
B Y
B Z
C Z
B Z
A Y
B X
B Z
B Z
B Z
C Y
B X
A Y
B Z
B Y
B Z
B X
A X
A X
B Y
B X
C Y
A Y
A Z
B Z
B Z
B Y
B X
B Z
B X
B Z
B Z
B X
B Z
B Z
B Z
B X
A Y
B X
B Z
A X
B Z
B Z
B X
C Y
A Z
B Z
C Z
B X
A Z
B X
A Z
C Y
C Y
A Y
A Y
B Z
A Y
A Y
C Z
A X
B X
B X
C Y
A Z
B Y
C Y
B Z
B Y
B Z
A X
B Z
C Z
B X
B Y
A X
C Z
B Y
B Z
B Z
A Z
B X
A Y
C Y
C Y
B Z
B Z
B X
B Z
B Z
B Y
B Z
B Z
B Z
B X
B X
B Z
B Y
B Z
C Y
A Z
A Y
B X
A X
B Z
B Z
A Z
B Z
B Z
B X
A Y
C Z
C Y
B Z
B Z
C Z
A X
B Z
B Z
B X
A Y
A Z
B Z
C Y
C Z
A Y
B Z
B X
C Z
A X
C Z
B Z
C Z
B X
C Y
B X
B Z
B X
A Y
A Z
B Z
B X
B Z
C Z
C Z
C Y
B X
B Y
B Z
C Z
C Z
B Z
B X
B Z
C Y
A Y
C Y
B X
C Y
B Y
C X
B X
A Y
C Z
B X
B Z
B Y
B X
B X
A Z
B Z
C Z
B X
B X
A X
B X
B X
C X
C Y
A X
B X
B Z
B X
A Y
B X
B Y
B X
B X
B Y
B X
A Z
B X
B Z
B X
C Z
A Z
C X
B Z
A Y
B X
B X
A Z
A Y
B Z
B Z
A Y
C Z
B X
B X
C Z
B Z
B Z
B Z
A Y
A Y
A X
B X
C X
B X
B Z
B X
A X
B X
B Z
B X
A Y
B X
C Y
B X
B Z
B Z
C Z
C Z
C Y
B Z
B X
B Z
A Y
C Y
B X
C Y
C Z
A X
B Z
A X
B X
C Y
A X
A Y
B X
A Y
A Z
C X
B X
B Y
B Z
B X
A Y
B Z
B X
A X
B X
B X
B Y
B Z
B Z
B X
B X
A X
B Z
B Z
B Z
A Y
C Z
A X
A Y
B Z
B X
C Y
A X
B X
B Z
B Y
A Y
B Z
B Z
B X
A Y
A Y
A Y
C Y
B X
B X
B Z
B Z
A Z
A Y
B Z
B X
B X
B X
A X
B X
C Y
B Z
A Y
B X
A Y
A X
C Y
B Z
C Z
B Z
B X
B Z
A Y
B Z
C Z
B Z
B Z
A X
B Z
B Z
B X
B Z
B Z
B Z
C X
B X
B X
A Y
A Y
B X
B X
C Z
B X
B X
C Y
C Y
C Z
B X
B Z
B Z
B Z
A Y
A Y
A X
C Z
A Z
A X
B Z
C Z
A X
B X
B Y
C Z
B Y
B Z
C Y
C Y
C Y
B Z
B Z
B X
B Z
B Y
B X
B Z
A X
B X
A Y
A X
B Z
A Y
C Z
C X
B X
B X
C Y
B Z
A Y
B Z
B X
C X
B Z
A X
A Y
A Y
C Y
C Z
C Z
B X
A X
B X
B X
B Z
C Y
B X
C Z
C Y
B X
B Z
B X
B X
C Z
A Y
B Z
B X
A X
B Z
B Z
B X
B X
C Z
C Z
B Y
C Z
B Z
C Y
B X
B X
C Y
B X
B X
A Z
B X
B X
A Y
B X
B X
B X
B Z
B Z
C Z
A Y
B X
B Z
B X
B Z
B Z
B X
B Y
C Z
A Z
A Y
C Y
B X
A X
B Y
A X
B Y
A Y
C X
B X
A Y
B Z
B Z
B X
B Z
B Z
B X
B X
A Y
B Z
A Y
B Y
B Y
B Y
B X
B Z
B Z
A Y
A X
C Y
B X
B X
A Y
B X
A Y
B Z
B Z
B Y
B Z
C Z
C Z
C Y
B Z
C Y
A X
B X
C Y
C Z
B X
C Y
A Y
B Z
B Z
A X
C Y
B X
A Y
C Z
B Z
B X
A Y
C Z
A X
A Y
C X
C Y
A Y
B Z
B X
A Z
B Z
B Z
B X
C Z
B X
B Z
B X
B X
B Z
B X
A X
A Y
B Y
A Y
A Y
B Z
C Z
B X
B Z
A Y
A Y
A Z
B X
A Y
A Y
B X
A X
B Y
B Z
B Y
A Z
C Y
B Z
A X
A Z
C Z
B X
B Z
B X
C Y
A X
B Z
A Y
B X
B Z
B X
C Y
B Z
B X
A Y
B X
C Y
C X
A X
B Z
B X
A X
A Z
B X
B Y
A Z
B Z
C Y
A Y
C X
B Z
A Y
C Y
C Z
B Y
C Y
A Z
B Z
B Z
A Y
B X
C X
A Y
A Y
A Z
B X
B X
B Y
A X
B X
B Z
B Z
B X
B Y
C Z
C Y
A Y
A Y
C X
C Z
C Y
C Y
A Y
A Y
B Z
C Y
C Y
A Y
A Y
C Z
B Z
A X
B Y
B Z
B Z
B Z
B Z
B X
A Y
A Y
B Z
A Y
C X
A X
C Z
B Z
B Z
A X
C Y
B Z
B X
B X
B X
B Z
C Y
B Z
B X
B X
B Z
C Y
B X
A Z
B X
B Z
A X
C X
A X
B Z
B Z
B Z
B Z
A Y
B Z
B Y
B Z
B Z
B X
B Z
B Z
A Y
A X
B X
A Z
B Z
A Z
B X
B X
B Z
A Y
B Y
A X
B X
B X
A Y
B X
B X
B Z
B Y
B Z
C Z
B X
C Y
B Z
B Z
C Y
B Z
B Z
B Z
B Z
B Z
C Y
A Y
A X
B Z
C Y
B Y
C Z
B Z
B Z
C Z
B Z
B Z
B X
A Y
B Z
B Z
C Y
A Y
B Z
A Y
B Z
C Z
C Y
A Y
A Z
B X
A X
B Z
B Y
A X
A X
A Y
B X
C Y
B X
B Z
A Y
B X
B X
A Y
A Z
C Z
C Y
A Y
B X
B Z
B X
C Z
A Y
B X
A Y
B Z
B X
C Y
A Y
B Z
C Y
B Y
B X
C Y
A Y
B Z
C Y
B X
A X
B Z
B Z
C Y
A Y
B Z
C Y
B X
A X
A Z
C X
B Z
B Z
C Y
B Y
C Z
C Y
A X
A Y
A X
A Y
C Z
C Y
C Z
C Z
C Y
A X
C Z
B X
C X
B X
A Z
B X
C Z
A Y
A X
A Z
C Z
B X
C Y
A Y
C Y
C Z
C Y
C Y
C X
B Z
B X
B Y
A X
B Z
B Y
C Y
C Y
C Z
A Z
A X
A Y
C Z
B Z
B X
B Z
B Z
B X
B Z
C Y
A X
B X
A Z
B X
C Y
B Z
B X
B Z
C Z
C Z
A X
B Z
B X
A Y
B Z
A Y
B Y
C Z
C Y
A X
A Y
C Y
C Z
B X
C Y
B Z
B Z
B Z
C Z
B X
C Y
B Z
C Z
B X
A Y
A X
B Z
B Z
C Y
B X
B Z
C Y
A Y
C Y
A Y
B X
C Z
A X
A Y
C Y
C Z
B Z
B Z
B Z
A Y
A Y
C Z
A Z
B X
A X
B Z
C Z
B X
C Y
B Z
B X
B Z
B Z
B X
C Z
B X
B Z
B X
A X
B X
A X
B Z
B Z
A Y
B X
B Z
B Z
C Z
C Y
B X
B X
B Y
C Z
C Y
A X
B Z
C Y
A Y
B X
B X
A X
A Y
C X
B Z
B Z
A Y
A X
C Y
B Z
B Z
C X
C Y
A Y
B Z
C Y
B Z
B X
B Z
C Y
B X
B Z
B X
B X
B X
B Z
B Z
C Y
B X
B Z
A Z
A Y
A Z
A Y
B X
C Z
A Y
B X
B X
C Z
B Z
A X
B X
C Y
A Y
A X
B Z
A X
A Y
B Z
B X
B Z
C Y
A X
A X
B X
B Y
C X
A X
B X
B X
A Y
C Y
B Z
B Z
C Z
B X
B Z
B X
B Y
B Z
B Z
B X
B Z
A X
B X
A Y
A Z
B Z
B X
A Y
A X
B Z
B X
C Z
A Y
A Y
C Z
B X
A X
C Y
B X
B Z
B X
B Z
B X
C Y
B Z
C Y
C Z
A Z
C Z
A X
C Z
B Y
B X
B Z
C Z
A Y
A Z
A X
B Z
A X
B Z
B X
A Z
C Z
C Y
B Z
C X
A X
A X
B X
A Y
A X
B Z
B Z
B Z
B Z
A X
A X
A Y
B Z
B Y
B Z
A Y
B Y
A X
A Z
B Z
A Y
B X
A X
A X
B Z
A Z
B Y
B Z
C Z
C Y
B X
B Y
A X
A Z
B Z
A Z
B Z
A X
B Z
A Z
B Y
A Z
C Y
B Z
C Z
B Z
B X
B Z
C X
A X
B X
C Z
B X
B Y
A X
B X
B X
A Z
B X
B Z
C Z
B X
B X
B X
A Y
A Z
C Y
A Y
B X
A Z
A Z
B Y
B Y
C Z
C Z
B Z
C Z
B Z
A Y
A X
C Y
B X
B Z
B Z
B Z
B X
B Z
B Z
A X
A Y
B X
B X
B X
C X
C Y
C X
B X
B Z
B Y
C X
A Y
A Y
B Z
C Y
C Z
C Z
C Z
A Y
B Y
B Z
B X
B Z
B Y
A X
C Y
C Z
A Y
B Z
A X
A X
A X
B Z
B X
C Y
B Y
C Z
B Z
B Z
C Y
B Z
C Z
B X
B Y
A Y
C Z
A Y
B Z
B Z
B X
B X
B Z
B Z
B X
B X
C Z
B Y
B Z
B X
C Y
C Z
A Y
C Z
B Z
C Y
B X
C Z
A X
B Z
B X
C X
C Z
B Z
C Z
A X
B Z
C X
B Z
C Z
A Y
B Z
B Z
C Z
B Y
B Z
B X
B X
A X
A Y
A Y
C Y
C Y
C Y
B Z
B Z
A Y
B X
A Z
C Y
C Z
B X
A Y
A Y
C Z
C Z
C Y
A Z
B Z
B Z
B Z
A Y
A Y
C Z
B Z
B X
C Z
B Z
C Y
A Z
B X
B Z
A Z
B X
A X
B X
A X
B X
B Z
B Z
B Z
C Y
C Z
A Y
B X
A X
C Z
C Y
C Z
B Z
B X
A Y
A X
C Z
B X
C Z
C Y
A X
B X
C Z
B X
B Z
C Y
B X
A X
A Y
A X
B Z
B Z
C Z
B X
A Y
B X
B X
A Z
B Y
B Z
B X
B Z
B X
B Y
B X
B X
A Y
A Y
A X
C Y
A Y
B X
C Y
B Z
B Z
A Y
B X
C Y
C Z
C Y
B Z
C Z
C Y
A Y
A Y
B Z
B X
A X
A Y
B X
B Z
B X
C Z
C Z
A Y
B X
B Z
B Y
C X
C Y
B Z
A X
B Z
A Y
A X
A Y
B X
B Z
B Z
B X
B Z
C Z
B Z
A Y
B Z
C Z
B X
B X
B X
B X
B X
B X
B Y
B Z
B X
B Z
A Z
B Z
C Y
A X
B Z
B Z
C Z
B X
A Z
C Y
B Z
B X
A X
A Y
C Y
B Y
A X
B Y
B X
B Z
C Y
B Z
C Y
A Z
B Z
C Y
C Z
A Y
C X
C Y
B Z
B X
B Z
B X
B X
A Y
B Y
B X
B X
C Y
B X
C X
B Y
A Y
C Y
B X
B X
A X
B X
A X
A X
B X
B X
A Z
C Z
C Y
B X
B X
C Z
B X
C Y
C Z
A Y
B Z
C Y
B X
B Y
B X
B X
C X
A X
B X
B Z
B Z
C Y
C Y
B Y
A Y
B Z
B X
B X
A Z
B Z
B X
B X
A Y
B X
B X
B X
A X
B X
B X
B X
B Z
B X
A Z
B Y
B X
B Z
B Z
B Z
A X
B Z
B Z
B X
B Z
C Z
C Y
A Z
C X
C Y
A Y
B X
B Z
C Z
B X
C Z
B Z
A Z
A Y
B Y
B Z
B X
A X
B Z
C Z
C Y
B Z
A X
A Y
A Z
B Z
C Y
A Y
B X
C Z
A Y
B X
B Z
B X
C Y
B X
B X
B X
A Z
B Z
C Z
B Z
C Y
B Z
C Z
B Z
B X
C Y
C Z
A X
C Z
C Y
C Y
B X
A Y
A Z
B X
B Z
B Z
B Z
A X
A Z
B Z
A Z
A Y
C Z
B Y
B Z
B X
B X
C Z
B Z
B Z
B Z
B Z
B X
B X
A X
A X
A Z
B Z
B X
B Z
B Z
C X
A Y
B Y
B X
B X
B Z
B X
B X
B X
C Y
B Z
B X
C Y
B Z
A Y
B Y
B Z
A Y
A X
B X
B X
B Z
A X
B Z
A Y
B Z
B X
A X
A X
A X
A Y
B Z
A Y
A X
B X
B Z
A Y
B Z
B X
B X
A Z
B Z
B Z
B Z
B Z
A X
B Z
B Z
B X
B Z
C Y
B Z
B X
B Z
B X
C Y
B X
B Y
B Z
B X
A X
C Y
B Z
B Z
B X
A Y
B X
B Z
C X
C Y
A Y
B X
B X
A Y
B Z
C Y
B Z
A Y
C Y
B Z
A X
A X
A Y
C Y
C Z
B Z
C Z
B X
A X
B X
A Y
A Y
C Z
C Y
A Z
B Z
A Y
B X
B X
B Z
C Z
B X
B X
B Y
C Y
C Z
A Y
A Z
A X
A Y
A Y
A Z
B Z
B Z
C Z
B Z
B X
C Y
A Y
B X
C Z
A X
B Z
B Y
A Y
B X
B X
A X
C Z
C Z
C Y
C Y
A X
B X
B X
B Y
A Z
C Z
A Y
C Z
C Y
B X
C Y
B Z
A Z
B Y
B X
C Y
B Y
B Z
A Z
A X
B X
C Z
C Z
B Z
B Z
C Z
B X
B X
C Y
A Y
C Y
C Z
B Z
B X
A Y
B Y
B Z
C X
B X
B Z
B Z
B X
B Z
B Z
C Y
A Y
B Z
B X
A Y
A Y
B Z
B Y
C Y
B Z
B Y
B Z
A Y
B X
C Z
A X
B X
C Y
B Z
B Y
B Y
B Z
A X
A X
B Z
B X
A X
B Z
B Z
A Y
B X
A X
B X
B X
A Y
C Z
C Y
B Z
B X
B X
B X
A X
B X
B Z
B Z
B Z
B X
B Z
B Z
A Z
C Y
B X
B X
B X
A X
C Z
A Y
A Y
B Z
B X
C Z
B Y
C Z
B X
A Y
C X
B Z
B X
B X
C Y
B X
B X
B Z
A X
B X
A Z
B X
B X
B Z
B X
B X
B Z
A Y
B X
B Z
B X
B X
C Y
B X
B Z
B Z
B X
B Z
C Z
B Y
A Y
B Z
B X
B X
A X
B Z
B X
B X
B Z
C Z
B Z
B X
C Y
B Z
B Y
B Z
B Z
A Z
B X
B X
B X
B X
C Y
A Y
B X
B Z
B X
B X
C Z
C X
B Z
B Z
B X
B Z
A Y
A Z
C Z
A X
A X
A Y
A Y
A Y
C Z
B Z
A Y
C Z
B X
A Z
C Y
A X
A X
A Y
B Z
A X
B X
B X
C Z
B Z
C Z
B Z
A X
C Y
C Z
A X
A Z
B X
C Y
A X
B X
B Z
B X
A Y
A Z
C Z
B Z
A X
B X
C Y
B X
B X
C Y
B X
A Z
A X
C X
B Z
B Y
C Z
C Z
A Y
A Y
A Y
B X
A Z
B X
A X
C Y
B X
A X
B X
B X
B Z
B Z
B Y
A Y
C Z
C Z
B X
B Z
C Z
B X
C Z
B Z
A Y
A X
B X
C Z
C Y
A Y
B Z
C Y
B X
C X
A X
B Z
C Z
B Z
B X
A Y
B Z
A Y
A Y
A Y
C Y
C Y
C Z
A Y
C Y
C Y
B Z
B X
C Y
C Y
B X
B Z
B X
C Z
C Y
C Y
B X
B X
A Y
B Z
B Z
B X
B X
C Z
A X
B Y
B X
A Z
B Z
B X
B Z
C Y
B Z
C Y
B Z
A Z
C Y
A Z
C Z
B X
B X
C Y
B X
C Y
A Z
B X
B Z
B Z
B X
B Z
B Y
A Y
B Z
B X
B Z
A Y
A Y
C Z
A Y
C X
B Z
A Y
A Y
B X
B Z
A Y
C Z
B Z
B Z
C Y
A X
B Z
C Z
B X
B X
B Z
A Y
B Y
C Z
A Y
B X
C Z
B X
B Z
B Z
B Z
B X
B Z
B X
A Z
B X
B X
B X
B Z
A Z
A Y
B Z
B X
B X
C X
A Y
A X
C Y
A Z
A X
C Y
A Y
C Y
A Z
B X
B Z
B Z
C Y
A Y
B X
B X
A Y
C Z
B Z
B Z
A X
A Y
B Z
A Z
A Y
C Z
B X
B Z
B Z
C Y
A Y
C Z
B Z
A Y
C Y
C Y
B Z
B Z
B Z
A Y
B Y
A Y
B Z
C Z
A Y
B X
C Y
A Z
A X
B X
A Z
B Z
A Z
C Z
B Z
A Y
A X
A X
C Y
A Y
B Z
A Y
B Z
B X
A Y
A X
A Y
A X
C Z
A Y
B X
C Z
B X
B Z
B Z
C Y
B Z
B X
A X
B Y
A Y
B X
C Z
A Y
B Z
B X
A Y
C Z
C Y
B Z
B Z
B Z
A X
B X
A Y
B Z
C Y
A X
A Z
B Y
B X
C Y
B Z
C Y
A Z
B X
A Y
A Y
C Y
A Z
B X
A Z
B X
B X
A X
B X
B Z
B X
B Y
B Z
B Y
B X
A Y
A X
C Z
B Y
C Y
B X
C Y
B X
B Z
B X
B X
B Z
B Z
B Z
C Z
B Z
B Z
A X
A Y
B X
B X
B X
C Z
B X
B Z
C Y
A X
A Z
B X
C Z
A X
A X
B Z
A Y
B Z
A X
C X
C Z
B X
B X
C Y
A X
B X
A X
C Y
C Y
A X
A X
B X
B Y
B Z
A X
B X
B X
B X
B Y
A Z
B Z
C Z
B X
B X
B Z
A Y
C Y
B X
B X
A X
C Y
C X
C Z
B X
B Y
A Z
C Z
A X
C Y
B Z
B X
A X
B X
B X
B Z
C Y
A Y
A X
C Z
B Z
A Y
B X
B X
B X
A Y
B X
C Y
B Y
A X
A X
B Y
B X
B X
B Z
B Z
A X
C Z
C Z
A X
C Y
B X
C Z
B X
B Z
B X
B Z
A Y
C Y
B X
B X
B X
B X
B Y
C Y
B Y
B Y
A Y
B Z
C Y
A X
C Z
B X
B Z
C Y
A Y
B X
C Z
B Z
A Z
A Z
A Z
A Y
C Z
A Z
B X
C Z
B Z
B X
C X
A Z
B Y
A Y
B Y
C Y
B X
A X
A X
A Y
A Y
A X
B Y
B Z
B X
A X
C Y
B X
B Y
A Y
C Y
A Y
B Z
B X
B Z
B Z
B X
B X
B Z
B Z
C Z
C Y
A Z
B X
B X
A Y
C Y
B X
B Z
B X
C Z
A Z
B X
C Z
B X
B X
B X
B Z
C Z
C Z
B X
C Y
B Z
C Z
B Z
C Z
B Y
B X
C Z
A X
B X
B X
C Y
B Z"""
)
//endregion rawInput
