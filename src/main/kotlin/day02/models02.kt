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
