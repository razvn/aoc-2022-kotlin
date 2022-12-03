
fun main() {
    val day = "Day03"

    fun part1(input: List<String>): Int = input.fold(emptyList<Char>()) { acc, line ->
        val commun = communChars(emptySet(), line.chunked(line.length/2))
        acc + commun
    }.let { value(it)}

    fun part2(input: List<String>): Int = input.chunked(3).fold(0) { acc, group ->
        val commun = communChars(emptySet(), group)
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

private tailrec fun communChars(commun: Set<Char>, remain: List<String>): Set<Char> = when {
    commun.isEmpty() -> when {
        remain.size > 1 -> {
            val newCommun = remain[0].toSet().intersect(remain[1].toSet())
            communChars(newCommun, remain.drop(2))
        }
        else -> emptySet()
    }
    remain.isEmpty() -> commun
    else -> {
        val newCommon = commun.intersect(remain.first().toSet())
        communChars(newCommon, remain.drop(1))
    }
}
private fun value(items: Collection<Char>): Int = items.fold(0) { acc, char ->
    val baseCharCode = if (char in 'a'..'z') 'a'.code - 1 else 'A'.code - 1 - 26
    val charValue = char.code - baseCharCode
    acc + charValue
}
