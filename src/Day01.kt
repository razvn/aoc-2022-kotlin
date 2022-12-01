fun main() {
    val day = "Day01"

    tailrec fun calcMax(acc: Int, max: Int, input: List<String>): Int {
        val newMax = if (acc > max) acc else max
        return when {
            input.isEmpty() -> newMax
            input.first().isBlank() -> calcMax(0, newMax, input.drop(1))
            else -> calcMax(acc + input.first().toInt(), max, input.drop(1))
        }
    }

    tailrec fun calc3Max(acc: Int, max: List<Int>, input: List<String>): Int {
        val newMax = (max + acc).sortedDescending().take(3)
        return when {
            input.isEmpty() -> newMax.sum()
            input.first().isBlank() -> calc3Max(0, newMax, input.drop(1))
            else -> calc3Max(acc + input.first().toInt(), max, input.drop(1))
        }
    }

    fun part1(input: List<String>): Int {
        return calcMax(0, 0, input)
    }

    fun part2(input: List<String>): Int {
        return calc3Max(0, emptyList(), input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}
