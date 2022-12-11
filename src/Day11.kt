import java.lang.Exception

fun main() {
    val day = "Day10"
    fun part1(input: List<String>): Int {
        val values = loadValues(input)
        val keep = valuesToKeep(values, 20, 60, 100, 140, 180, 220)
        // println(keep)
        return keep.map { (k, v) -> k * v }.sum()
    }

    fun part2(input: List<String>): Int {
        val nbChars = 40
        val fillChar = "."

        val mapValues = loadValues(input)
        var x: Int
        var sprite: String
        var line = ""
        val lines = (1..240).mapNotNull { cycle ->
            x = mapValues.xValue(cycle)
            val spritePrefix = if (x < 1) "" else fillChar.repeat(x - 1)
            val fillPostfix = fillChar.repeat(nbChars)

            sprite = ("$spritePrefix###$fillPostfix").take(nbChars)

            val idx = (cycle - 1) % nbChars
            val draw = sprite[idx]
            line += draw
            // println("Cycle: \t$cycle \t| \tx: \t$x \t| \tidx: \t$idx \t| \tdraw: \t'$draw' \t| \tsprite: \t'$sprite' \t| \tcurrent: \t'$s'")
            if (cycle % nbChars == 0) {
                val fullLine = line
                line = ""
                fullLine
            } else null
        }
        lines.forEach { println(it.replace(fillChar, " ")) }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 13140)
    // check(part2(testInput) == 36)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun loadValues(input: List<String>): Map<Int, Int> {
    var x = 1
    var cycle = 1
    return mapOf(1 to 1) + input.map {
        when {
            it == "noop" -> cycle++
            it.startsWith("add") -> {
                val cmd = it.split(" ")
                x += cmd[1].toInt()
                cycle += 2
            }
        }
        cycle to x
    }.toMap()
}

private fun valuesToKeep(map: Map<Int, Int>, vararg cycle: Int): Map<Int, Int> {
    return cycle.associateWith {
        // println("Getting: $it - ${map[it]} - ${map[it - 1]} - ${map[it - 2]}")
        val response = map[it] ?: map[it - 1]
        response ?: throw Exception("Both $it and $it - 1 are null - can't be true")
    }
}

private fun Map<Int, Int>.xValue(idx: Int) = this[idx] ?: this[idx - 1] ?: throw Exception("There should be a value for '$idx'")
