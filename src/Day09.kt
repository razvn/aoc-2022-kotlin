import kotlin.math.abs

fun main() {
    val day = "Day09"

    fun part1(input: List<String>): Int {
        val head = createRope(2)
        input.map {
            it.split(" ").let { (d, n) -> Direction.valueOf(d) to n.toInt() }
        }.forEach {
            head.move(it.first, it.second)
        }
        return getTail(head).positions.toSet().size
    }

    fun part2(input: List<String>): Int {
        val head = createRope(10)
        // println(head)
        input.map {
            it.split(" ").let { (d, n) -> Direction.valueOf(d) to n.toInt() }
        }.forEach {
            head.move(it.first, it.second)
        }

        val tail = getTail(head)
        // println(tail.positions)
        return tail.positions.toSet().size
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("${day}_test")
    // println(part1(testInput1))
    check(part1(testInput1) == 13)
    val testInput2 = readInput("${day}_test2")
    // println(part2(testInput2))
    check(part2(testInput2) == 36)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}


private fun createRope(elements: Int) = createRope(0, elements)
private fun createRope(current: Int, elements: Int): Head2 {
    return  when {
        current >= elements - 1 -> Head2("$current", null)
        else -> Head2("$current", createRope(current + 1, elements))
    }
}

private tailrec fun getTail(head: Head2): Head2 {
    return when (head.tail) {
        null -> head
        else -> getTail(head.tail)
    }
}
private data class Head2(val name: String, val tail: Head2?  = null) {
    private var currentPosition: Point = Point(0, 0)
    val positions = mutableListOf(currentPosition)

    fun move(dir: Direction, value: Int) {
        // println("MOVE -> $dir: $value")
        repeat(value) { move(dir) }
    }

    private fun move(dir: Direction) {
        val newX = when (dir) {
            Direction.R -> currentPosition.x + 1
            Direction.L -> currentPosition.x - 1
            else -> currentPosition.x
        }
        val newY = when (dir) {
            Direction.U -> currentPosition.y + 1
            Direction.D -> currentPosition.y - 1
            else -> currentPosition.y
        }
        currentPosition = Point(newX, newY)
        // println("\tcurrentPosition: $currentPosition")
        positions.add(currentPosition)
        tail?.moveTail(this)
    }

    private fun moveTail(head: Head2) {
        val moveX =  head.currentPosition.x - currentPosition.x
        val moveY =  head.currentPosition.y - currentPosition.y
        if (abs(moveX) > 1 || abs(moveY) > 1) {
            val newX = when {
                moveX > 0 -> currentPosition.x + 1
                moveX < 0 -> currentPosition.x - 1
                else -> head.currentPosition.x
            }
            val newY = when {
                moveY > 0 -> currentPosition.y + 1
                moveY < 0 -> currentPosition.y - 1
                else -> head.currentPosition.y
            }
            currentPosition = Point(newX, newY)
            // println("\tnewTail: $currentTail")
            positions.add(currentPosition)
            tail?.moveTail(this)
        }
    }
}

private enum class Direction { R, L, U, D}
private data class Point(val x: Int, val y: Int)
