import java.lang.Exception

fun main() {
    val day = "Day02"
    fun part1(input: List<String>): Int = input.fold(0) { acc, line ->
        val player1 = calcPlayer(line[0])
        val player2 = calcPlayer(line[2])
        val result = calcResult(player1, player2)
        acc + result.value + player2.value
    }

    fun part2(input: List<String>): Int = input.fold(0) { acc, line ->
        val player1 = calcPlayer(line[0])
        val result = calcResult(line[2])
        val player2 = calcPlayerFromResult(player1, result)
        acc + result.value + player2.value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

enum class RPS(val value: Int) {
    Rock(1),
    Paper(2),
    Scissors(3),
}

enum class Result(val value: Int) {
    DRAW(3),
    WIN(6),
    LOSE(0)
}

fun calcResult(p1: RPS, p2: RPS): Result {
    return when (p2) {
        RPS.Paper -> when (p1) {
            RPS.Paper -> Result.DRAW
            RPS.Rock -> Result.WIN
            RPS.Scissors -> Result.LOSE
        }

        RPS.Scissors -> when (p1) {
            RPS.Scissors -> Result.DRAW
            RPS.Rock -> Result.LOSE
            RPS.Paper -> Result.WIN
        }

        RPS.Rock -> when (p1) {
            RPS.Rock -> Result.DRAW
            RPS.Paper -> Result.LOSE
            RPS.Scissors -> Result.WIN
        }
    }
}

fun calcPlayer(char: Char): RPS = when (char) {
    'A', 'X' -> RPS.Rock
    'B', 'Y' -> RPS.Paper
    'C', 'Z' -> RPS.Scissors
    else -> throw Exception("Unknown value $char")
}

fun calcPlayerFromResult(p1: RPS, result: Result): RPS {
    return when (p1) {
        RPS.Rock -> when (result) {
            Result.DRAW -> RPS.Rock
            Result.WIN -> RPS.Paper
            Result.LOSE -> RPS.Scissors
        }

        RPS.Paper -> when (result) {
            Result.DRAW -> RPS.Paper
            Result.WIN -> RPS.Scissors
            Result.LOSE -> RPS.Rock
        }

        RPS.Scissors -> when (result) {
            Result.DRAW -> RPS.Scissors
            Result.WIN -> RPS.Rock
            Result.LOSE -> RPS.Paper
        }
    }
}

fun calcResult(char: Char): Result = when (char) {
    'X' -> Result.LOSE
    'Y' -> Result.DRAW
    'Z' -> Result.WIN
    else -> throw Exception("Unknown value $char")
}
