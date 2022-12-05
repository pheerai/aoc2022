package day05

import Stack

// _Maybe_ moving the actual code for the operation here instead of the stacks, and using inheritance or lambda reference to get the correct implementation, would have been a cleaner approach.
// It would only be fun with dependency injection, though, and that's too much overhead for a project this size.
class CraneOperation(val amount: Int, val from: Int, val to: Int) {
    constructor(stepTriple: Triple<Int, Int, Int>) : this(stepTriple.first, stepTriple.second, stepTriple.third)
    constructor(taskDescription: String) : this(
        taskRegexp.matchEntire(taskDescription)
            ?.groups
            ?.let { Triple(
                it[1]?.value?.toInt() ?: -1,
                it[2]?.value?.toInt() ?: -1,
                it[3]?.value?.toInt() ?: -1
            )}
            ?: error("Parser error")
    )

    companion object {
        val taskRegexp = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".toRegex()
    }
}

typealias ContainerStack = Stack<Char>

class ContainerStacks(input: Map<Int, List<Char>>) {
    private val stacks: MutableMap<Int, ContainerStack> =
        input.mapValues { (_, v) -> ContainerStack(v) }
            .toMutableMap()

    fun performCraneOperation051(op: CraneOperation): ContainerStacks {
        for( i in 1..(op.amount)) {
            stacks[op.from]?.pop()
                ?.also { stacks[op.to]?.push(it) }
                ?: error("Error moving items")
        }
        return this
    }

    fun getResult(): String {
        return stacks.toSortedMap()
            .values
            .map { it.last()  }
            .joinToString("")
    }

    fun performCraneOperation052(op: CraneOperation): ContainerStacks {
        val tempStack = ContainerStack(listOf())
        for (i in 1..(op.amount)) {
            stacks[op.from]?.pop()
                ?.also { tempStack.push(it) }
                ?: error("Error moving crates")
        }
        for (i in 1..(op.amount)) {
            tempStack.pop()
                .also { stacks[op.to]?.push(it) }
        }
        return this
    }
}

class Task05(private val stacks: ContainerStacks, private val ops: List<CraneOperation>) {
    constructor(initialStacks: Task05StacksState, operations: Task05OpString)
            : this(
        ContainerStacks(initialStacks.stacks),
        operations.opString.lines().map { CraneOperation(it) })

    fun performTasksMover051(): String {
        ops.forEach {
            stacks.performCraneOperation051(it)
        }
        return stacks.getResult()
    }

    fun performTasksMover052(): String {
        ops.forEach {
            stacks.performCraneOperation052(it)
        }
        return stacks.getResult()
    }
}
