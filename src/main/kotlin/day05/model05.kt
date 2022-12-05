package day05

import Stack
import mapSeconds

abstract class CraneOperation(val amount: Int, val from: Int, val to: Int) {
    constructor(stepTriple: Triple<Int, Int, Int>) : this(stepTriple.first, stepTriple.second, stepTriple.third)
    constructor(taskDescription: String) : this(
        taskRegexp.matchEntire(taskDescription)
            ?.groups
            ?.let {
                Triple(
                    it[1]?.value?.toInt() ?: -1,
                    it[2]?.value?.toInt() ?: -1,
                    it[3]?.value?.toInt() ?: -1
                )
            }
            ?: error("Parser error")
    )

    abstract fun performOperation(stacks: ContainerStacks): ContainerStacks

    companion object {
        val taskRegexp = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".toRegex()
    }
}

class CraneOperationTask051(taskDescription: String) : CraneOperation(taskDescription) {
    override fun performOperation(stacks: ContainerStacks): ContainerStacks {
        for (i in 1..(this.amount)) {
            stacks[this.from]?.pop()
                ?.also { stacks[this.to]?.push(it) }
                ?: error("Error moving items")
        }
        return stacks
    }
}

class CraneOperationTask052(taskDescription: String) : CraneOperation(taskDescription) {
    override fun performOperation(stacks: ContainerStacks): ContainerStacks {
        val tempStack = ContainerStack(listOf())
        for (i in 1..(this.amount)) {
            stacks[this.from]?.pop()
                ?.also { tempStack.push(it) }
                ?: error("Error moving crates")
        }
        for (i in 1..(this.amount)) {
            tempStack.pop()
                .also { stacks[this.to]?.push(it) }
        }
        return stacks
    }
}

typealias ContainerStack = Stack<Char>

class ContainerStacks(
    private val stacks: MutableMap<Int, ContainerStack>
) : MutableMap<Int, ContainerStack> by stacks {
    constructor(input: List<Pair<Int, List<Char>>>) : this(
        input.mapSeconds { ContainerStack(it) }
            .toMap()
            .toMutableMap()
    )

    fun getResult(): String {
        return stacks.toSortedMap()
            .values
            .map { it.last() }
            .joinToString("")
    }

}

class Task05(private val stacks: ContainerStacks, private val ops: List<CraneOperation>) {
    constructor(
        initialStacks: Task05StacksState,
        operations: Task05OpString,
        opsPerformerGenerator: (String) -> CraneOperation
    )
            : this(
        ContainerStacks(initialStacks.stacks),
        operations.opString.lines().map { opsPerformerGenerator(it) })

    fun performTask(): String {
        return ops.fold(stacks) { accStacks, op ->
            op.performOperation(accStacks)
        }.getResult()
    }
}
