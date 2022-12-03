
fun main() {
    val day = "Day03"
    fun part1(input: List<String>): Int = input.fold(emptyList<Char>()) { acc, line ->
        val first = line.substring(0, line.length / 2)
        val second = line.substring(line.length / 2)
        val commun = first.toSet().intersect(second.toSet())
        acc + commun
    }.let { value(it)}

    fun part2(input: List<String>): Int = input.chunked(3).fold(0) { acc, group ->
        val commun = group[0].toSet().intersect(group[1].toSet()).intersect(group[2].toSet())
        acc + value(commun)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun value(items: Collection<Char>): Int = items.fold(0) { acc, char ->
    val baseCharCode = if (char in 'a'..'z') 'a'.code - 1 else 'A'.code - 1 - 26
    val charValue = char.code - baseCharCode
    acc + charValue
}
