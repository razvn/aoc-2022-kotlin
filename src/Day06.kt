fun main() {
    val day = "Day06"

    tailrec fun findMarker(len: Int, buffer: String, index: Int, string: String): Int {
        return when {
            buffer.length == len -> index
            index >= string.length -> 0
            else -> {
                val char  = string[index]
                val idxOf = buffer.indexOf(char)
                val newBuffer = "${if (idxOf >= 0) buffer.substring(idxOf + 1) else buffer}$char"
                findMarker(len, newBuffer, index +1, string)
            }
        }
    }

    fun part1(input: List<String>): List<Int> {
        return input.map {
            findMarker(4, "", 0, it)
        }
    }

    fun part2(input: List<String>): List<Int> {
        return input.map {
            findMarker(14, "", 0, it)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    println(part1(testInput))
    println(part2(testInput))
    check(part1(testInput) == listOf(7,5,6,10,11))
    check(part2(testInput) == listOf(19,23,23,29,26))

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}
