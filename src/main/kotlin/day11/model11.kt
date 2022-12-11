package day11

data class Item(val initialWorry: Long, val worry: Long = initialWorry) {
    fun notBroken(): Item = this.copy(worry = (this.worry / 3))

    companion object {
        fun parse(line: String): List<Item> {
            return itemsRegex.matchEntire(line)
                ?.groupValues
                ?.get(1)
                ?.split(",")
                ?.map { it.trim() }
                ?.map { it.toLong() }
                ?.map { Item(it) }
                ?: error("Error parsing items for line $line")
        }

        private val itemsRegex = Regex(" *Starting items: (.+)")
    }
}

@JvmInline
value class MonkeyIndex(val index: Int) {
    companion object {
        fun parse(line: String): MonkeyIndex {
            return indexRegex.matchEntire(line)
                ?.groupValues
                ?.get(1)
                ?.toIntOrNull()
                ?.let { MonkeyIndex(it) }
                ?: error("Error parsing index for line $line")
        }

        private val indexRegex = Regex("Monkey (.+):")
    }
}

@JvmInline
value class WorryIncreaseOperation(val op: (Item) -> Item) {
    companion object {
        fun parse(line: String): WorryIncreaseOperation {
            val matches = worryRegex.matchEntire(line)
                ?.groupValues ?: error("Error matchin worry increase op for line $line")
            val operator = matches[1]
            val operand = matches[2]
            return if (operand == "old") {
                when (operator) {
                    "+" -> WorryIncreaseOperation { it.copy(worry = it.worry * 2) }
                    "*" -> WorryIncreaseOperation { it.copy(worry = it.worry * it.worry) }
                    else -> error("Error parsing 'old' op")
                }
            } else {
                val operandInt = operand.toInt()
                when (operator) {
                    "+" -> WorryIncreaseOperation { it.copy(worry = it.worry + operandInt) }
                    "*" -> WorryIncreaseOperation { it.copy(worry = it.worry * operandInt) }
                    else -> error("Error parsing op")
                }
            }
        }

        private val worryRegex = Regex(" *Operation: new = old (.) (.+)")
    }
}

class TargetMonkeyOp(
    private val test: (Item) -> Boolean,
    private val targetTrue: MonkeyIndex,
    private val targetFalse: MonkeyIndex,
    val divisor: Int
) {
    fun getTarget(item: Item): MonkeyIndex {
        return if (test(item)) targetTrue else targetFalse
    }

    companion object {
        fun parse(lines: List<String>): TargetMonkeyOp {
            val test = parseTest(lines[0])
            val targetTrue = parseTarget(lines[1])
            val targetFalse = parseTarget(lines[2])
            return TargetMonkeyOp(
                test.second, targetTrue, targetFalse, test.first
            )
        }

        private fun parseTest(line: String): Pair<Int, (Item) -> Boolean> {
            val divisor = testRegex.matchEntire(line)
                ?.groupValues
                ?.get(1)
                ?.toInt() ?: error("Error parsing test for line $line")
            return divisor to divisor.let { operand -> { item: Item -> item.worry.mod(operand) == 0 } }
        }

        private fun parseTarget(line: String): MonkeyIndex {
            return targetRegex.matchEntire(line)
                ?.groupValues
                ?.get(2)
                ?.toInt()
                ?.let { MonkeyIndex(it) }
                ?: error("Error parsing target for line $line")
        }

        private val testRegex = Regex(" *Test: divisible by (.+)")
        private val targetRegex = Regex(" *If (true|false): throw to monkey (.+)")
    }
}

class Monkey(
    val index: MonkeyIndex,
    val items: MutableList<Item>,
    val operation: WorryIncreaseOperation,
    val getTarget: TargetMonkeyOp
) {
    var itemsHandled: Long = 0
        private set

    fun inspect(item: Item): Item {
        itemsHandled++
        return this.operation.op(item)
    }

    companion object {
        fun parse(input: String): Monkey {
            val lines = input.lines()
            val index = MonkeyIndex.parse(lines[0])
            val items = Item.parse(lines[1])
            val worryOp = WorryIncreaseOperation.parse(lines[2])
            val targetOp = TargetMonkeyOp.parse(lines.slice(3..5))
            return Monkey(
                index, items.toMutableList(), worryOp, targetOp
            )
        }
    }
}

class Calculation(private val monkeys: List<Monkey>) {
    val commonDenominator = monkeys.map { it.getTarget.divisor }.reduce(Int::times)
    private fun getMonkey(index: MonkeyIndex): Monkey {
        return monkeys.first { it.index == index }
    }

    private fun performRound(decreaseWorry: Boolean = true) {
        for (m in monkeys) {
            for (i in m.items) {
                val afterInspectItem = m.inspect(i)
                val afterNotBrokenItem = if (decreaseWorry) {
                    afterInspectItem.notBroken()
                } else {
                    afterInspectItem
                }
                val reducedItem = afterNotBrokenItem.copy(worry = afterNotBrokenItem.worry % commonDenominator)
                val itemTarget = m.getTarget.getTarget(reducedItem)
                val targetMonkey = getMonkey(itemTarget)
                targetMonkey.items.add(reducedItem)
            }
            m.items.clear()
        }
    }

    private fun performRounds(amount: Int, decreaseWorry: Boolean = true) {
        repeat(amount) { performRound(decreaseWorry) }
    }

    fun task111(): Long {
        performRounds(20)
        return getResultTask111()
    }
    fun task112(): Long {
        performRounds(10000, false)
        return getResultTask111()
    }

    private fun getResultTask111(): Long {
        val mostBusyMonkeys = monkeys.sortedByDescending { it.itemsHandled }
            .take(2)
        println("Monkey 1: ${mostBusyMonkeys.first().itemsHandled}, Monkey 2: ${mostBusyMonkeys.last().itemsHandled}")
        return mostBusyMonkeys[0].itemsHandled * mostBusyMonkeys[1].itemsHandled
    }

    companion object {
        fun parse(input: InputDay11): Calculation {
            return input.value.split("\n\n")
                .map { Monkey.parse(it) }
                .let { Calculation(it) }
        }
    }
}
