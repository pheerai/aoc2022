package day09

import isign
import kotlin.math.abs
import kotlin.math.max

// As always in AoC, it's easier to parse things with the _first_ coordinate `x` going _vertically_.
// Terms here are: UP -> x.inc(), RIGHT -> y.inc()

sealed interface PosComponent { val coord: Int }
data class PosX(override val coord: Int) : PosComponent {
    operator fun plus(change: PosChangeX): PosX = PosX(this.coord + change.amount)
    fun distance(other: PosX): Int = abs(this.coord - other.coord)
    fun directionTo(other: PosX): PosChangeX = PosChangeX(isign(other.coord - coord))
}

data class PosY(override val coord: Int) : PosComponent {
    operator fun plus(change: PosChangeY): PosY = PosY(this.coord + change.amount)
    fun distance(other: PosY): Int = abs(this.coord - other.coord)
    fun directionTo(other: PosY): PosChangeY = PosChangeY(isign(other.coord - coord))
}

sealed interface PosChange { val amount: Int }

data class PosChangeX(override val amount: Int) : PosChange {
    init {
        if (abs(amount) > 1) error("Invalid amount for pos change.")
    }
}
data class PosChangeY(override val amount: Int) : PosChange {
    init {
        if (abs(amount) > 1) error("Invalid amount for pos change.")
    }
}

data class Position(val x: PosX, val y: PosY) {
    operator fun plus(movementStep: MovementStep) = Position(x + movementStep.x, y + movementStep.y)
    fun distance(other: Position): Int = max(abs(this.x.distance(other.x)), abs(this.y.distance(other.y)))
    fun directionTo(other: Position): MovementStep {
        val directionX = x.directionTo(other.x)
        val directionY = y.directionTo(other.y)
        return MovementStep.getFor(directionX, directionY)
    }
}

enum class MovementStep(val x: PosChangeX, val y: PosChangeY, val descriptor: String) {
    UP(1, 0, "U"),
    DOWN(-1, 0, "D"),
    LEFT(0, -1, "L"),
    RIGHT(0, 1, "R"),
    UP_LEFT(1, -1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(-1, 1),
    NONE(0, 0);

    constructor(ix: Int, iy: Int, descriptor: String = "X") : this(PosChangeX(ix), PosChangeY(iy), descriptor)

    companion object {
        fun getFor(directionX: PosChangeX, directionY: PosChangeY): MovementStep {
            return values().filter { it.x == directionX }.firstOrNull { it.y == directionY }
                ?: error("Invalid getFor call: ${directionX.amount}, ${directionY.amount}")
        }

        fun getFromInput(descriptor: String): MovementStep {
            return values().firstOrNull { it.descriptor == descriptor }
                ?: error("Invalid op descripter encountered: $descriptor")
        }
    }
}

class Rope(private val movements: List<Pair<MovementStep, Int>>, numberOfKnots: Int = 2) {
    private val knots: List<RopeKnot> = (1..numberOfKnots).map {
        when (it) {
            1 -> RopeHead()
            numberOfKnots -> RopeTail()
            else -> RopeMiddle()
        }
    }

    fun task09(): Int {
        return movements.map { performMovements(it) }
            .flatten()
            .toSet()
            .size
    }

    private fun performMovements(movementOperation: Pair<MovementStep, Int>): Set<Position> {
        val (movementStep, amount) = movementOperation
        return (1..amount)
            .map { performMovement(movementStep) }
            .toSet()
    }

    private fun performMovement(movementStep: MovementStep): Position {
        for ((i, knot) in knots.withIndex()) {
            when (knot) {
                is RopeHead -> knot.performMovementStep(movementStep)
                is RopeMiddle -> knot.performMovementStep(knots[i - 1])
                is RopeTail -> {
                    knot.performMovementStep(knots[i - 1])
                    return knot.position
                }
            }
        }
        error("No Tail found")
    }
}

sealed class RopeKnot {
    var position = Position(PosX(0), PosY(0))
        internal set
}

class RopeHead : RopeKnot() {
    fun performMovementStep(movementStep: MovementStep) {
        position += movementStep
    }
}

sealed class TrailingRopeSegment() : RopeKnot() {
    fun performMovementStep(head: RopeKnot) {
        val hPos = head.position
        if (position.distance(hPos) <= 1) return
        else {
            position += position.directionTo(hPos)
        }
    }
}

class RopeTail : TrailingRopeSegment()
class RopeMiddle: TrailingRopeSegment()

fun InputDay09.parseMovementOps(): List<Pair<MovementStep, Int>> {
    return this.input.lines().parseMovementOps()
}
fun List<String>.parseMovementOps(): List<Pair<MovementStep, Int>> {
    return this.map { it.parseMovementOp() }
}

fun String.parseMovementOp(): Pair<MovementStep, Int> {
    return this.split(" ")
        .let { MovementStep.getFromInput(it[0]) to it[1].toInt()  }
}
