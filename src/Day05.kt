fun main() {
    val day = "Day05"

    fun part1(input: List<String>): String {
        val (creates, cmds) = load(input)
        cmds.forEach { c ->
            val take = creates[c.from]!!.take(c.number)
            creates[c.from] = creates[c.from]!!.drop(c.number)
            take.forEach {
                creates[c.to] = "$it${creates[c.to]}"
            }
        }
        return creates.values.map { it.firstOrNull() ?: "" }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val (creates, cmds) = load(input)

        cmds.forEach { c ->
            val take = creates[c.from]!!.take(c.number)
            creates[c.from] = creates[c.from]!!.drop(c.number)
            creates[c.to] = "$take${creates[c.to]}"
        }
        return creates.values.map { it.firstOrNull() ?: "" }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun load(input: List<String>): Pair<MutableMap<Int, String>, List<Command>> {
    val buffer = mutableMapOf<Int, String>()
    val commands = mutableListOf<Command>()
    val start = 1
    val nbCrates = (input.first().length + 1) / 4
    (1..nbCrates).forEach { buffer[it] = "" }
    input.forEach { line ->
        if (line.trim().startsWith("[")) {
            (1..nbCrates).forEach { nb ->
                val index = start + (nb - 1) * 4
                val char = line[index]
                buffer[nb] = "${buffer[nb]}$char".trim()
            }
        }
        if (line.startsWith("move")) {
            commands.add(parseCommand(line))
        }
    }
    return buffer to commands
}

private data class Command(val number: Int, val from: Int, val to: Int)

private val regEx = """move (\d*) from (\d*) to (\d*)""".toRegex()
private fun parseCommand(line: String): Command {
    val result = regEx.find(line.trim())
    val (nb, from, to) = result!!.destructured
    return Command(nb.toInt(), from.toInt(), to.toInt())
}
