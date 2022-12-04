
fun main() {
    val day = "Day04"

    fun part1(input: List<String>): Int {
        val pairs = parse(input)
        val including = pairs.filter { rangeContains(it.first, it.second)}
        return including.size
    }

    fun part2(input: List<String>): Int {
        val pairs = parse(input)
        val including = pairs.filter { rangeOverlap(it.first, it.second)}
        return including.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun parse(input: List<String>): List<Pair<IntRange, IntRange>> = input.map { line ->
    val (elf1String, elf2String) = line.split(",")
    val elf1 = elf1String.split("-").map { it.toInt() }.let { IntRange(it.first(), it.last())}
    val elf2 = elf2String.split("-").map { it.toInt() }.let { IntRange(it.first(), it.last())}
    elf1 to elf2
}

private fun rangeContains(one: IntRange, other: IntRange): Boolean {
    return (one.first in other && one.last in other)
        || (other.first in one && other.last in one)
}

private fun rangeOverlap(one: IntRange, other: IntRange): Boolean {
    return (one.first in other || one.last in other)
        || (other.first in one || other.last in one)
}

