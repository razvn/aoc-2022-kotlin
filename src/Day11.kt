fun main() {
    val day = "Day11"
    fun part1(input: List<String>): Int {
        val monkeys = loadValues(input)
        // println("Start --->")
        // monkeys.forEach(::println)
        (1..20).forEach {
            monkeys.forEach { (_, m) ->
                m.items.forEach { item ->
                    m.inspectedItems ++
                    val newWorry = m.operation.applyOperation(item) / 3
                    if (newWorry % m.test == 0L) {
                        monkeys[m.testTrue]!!.items.add(newWorry)
                    } else {
                        monkeys[m.testFalse]!!.items.add(newWorry)
                    }
                }
                m.items.clear()
            }
            // println("After $it --->")
            // monkeys.forEach(::println)
        }
        return monkeys.values.sortedByDescending { it.inspectedItems }.take(2).let {
            it.first().inspectedItems * it.last().inspectedItems
        }
    }

    fun part2(input: List<String>): Long {
        val monkeys = loadValues(input)
        // println("Start --->")
        // monkeys.forEach(::println)
        val div = monkeys.values.fold(1) { acc, value -> acc * value.test }
        (1..10000).forEach {
            monkeys.forEach { (_, m) ->
                m.items.forEach { item ->
                    m.inspectedItems ++
                    val newWorry = m.operation.applyOperation(item) % div
                    if (newWorry % m.test == 0L) {
                        monkeys[m.testTrue]!!.items.add(newWorry)
                    } else {
                        monkeys[m.testFalse]!!.items.add(newWorry)
                    }
                }
                m.items.clear()
            }
            // println("After $it --->")
            // monkeys.forEach(::println)
        }
        return monkeys.values.sortedByDescending { it.inspectedItems }.take(2).let {
            it.first().inspectedItems.toLong() * it.last().inspectedItems.toLong()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private enum class Op { PLUS, TIMES }
private data class Operation(val op: Op, val arg2: Long?) {
    constructor(params: List<String>) : this(if (params[0] == "+") Op.PLUS else Op.TIMES, params[1].toLongOrNull())

    fun applyOperation(value: Long): Long = when(op) {
        Op.PLUS -> value + (arg2 ?: value)
        Op.TIMES -> value * (arg2 ?: value)
    }
}

private data class Monkey(
    val id: Int,
    val items: MutableList<Long>,
    val operation: Operation,
    val test: Int,
    val testTrue: Int,
    val testFalse: Int,
    var inspectedItems: Int = 0
)

private fun loadValues(input: List<String>): MutableMap<Int, Monkey> {
    return input.chunked(7).map { monkey ->
        val id = monkey[0].substringAfter("Monkey ").dropLast(1).toInt()
        val items = monkey[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }
        val operation = monkey[2].substringAfter("Operation: new = old ").split(" ")
        val test = monkey[3].substringAfter("Test: divisible by ").toInt()
        val testTrue = monkey[4].substringAfter("If true: throw to monkey ").toInt()
        val testFalse = monkey[5].substringAfter("If false: throw to monkey ").toInt()

        id to Monkey(id, items.toMutableList(), Operation(operation), test, testTrue, testFalse)
    }.toMap().toMutableMap()
}
