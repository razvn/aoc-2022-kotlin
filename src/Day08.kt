private data class Tree(val x: Int, val y: Int, val value: Int)

fun main() {
    val day = "Day08"

    fun part1(input: List<String>): Int {
        val trees = input.mapIndexed { lIdx, line ->
            line.mapIndexed { cIdx, col ->
                Tree(lIdx, cIdx, col.digitToInt())
            }
        }.flatten()

        val maxX = trees.maxOf { it.x }
        val maxY = trees.maxOf { it.y }
        val visibleTrees = trees.filter { t ->
            when {
                t.x == 0 || t.y == 0 || t.x == maxX || t.y == maxY -> true
                else -> {
                    val leftTreesBigger = trees.filter { it.x < t.x && it.y  == t.y}.filter { it.value >= t.value }
                    if (leftTreesBigger.isEmpty()) true
                    else {
                        val rightTreeBigger = trees.filter { it.x > t.x && it.y  == t.y}.filter { it.value >= t.value }
                        if (rightTreeBigger.isEmpty()) true
                        else {
                            val topTreeBigger = trees.filter { it.y < t.y && it.x  == t.x}.filter { it.value >= t.value }
                            if (topTreeBigger.isEmpty()) true
                            else {
                                val bottomTreeBigger = trees.filter { it.y > t.y && it.x  == t.x}.filter { it.value >= t.value }
                                bottomTreeBigger.isEmpty()
                            }
                        }
                    }
                }
            }
        }.toSet()

        val totalVisible = visibleTrees.size

        return totalVisible
    }

    fun part2(input: List<String>): Int {
        val trees = input.mapIndexed { lIdx, line ->
            line.mapIndexed { cIdx, col ->
                Tree(cIdx, lIdx, col.digitToInt())
            }
        }.flatten()
        // println(trees)
        val scenics = trees.map { tree ->
            val maxX = trees.maxOf { it.x }
            val maxY = trees.maxOf { it.y }
            if (tree.x == 0 || tree.y == 0 || tree.x == maxX || tree.y == maxY) 0
            else {
                val leftValue = calcMove(tree, trees) {a, b -> (b.x == (a.x - 1)) && (a.y == b.y) }
                val rightValue = calcMove(tree, trees) {a, b -> (b.x == (a.x + 1)) && (a.y == b.y) }
                val topValue = calcMove(tree, trees) {a, b -> (b.y == (a.y - 1)) && (a.x == b.x) }
                val bottomValue = calcMove(tree, trees) {a, b -> (b.y == (a.y + 1)) && (a.x == b.x) }
                val rep = leftValue * rightValue * topValue * bottomValue
                //println("____ TREE ___ $tree = $rep (l: $leftValue | r: $rightValue | t: $topValue | b: $bottomValue")
                rep
            }

        }
        return scenics.max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    //println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private fun calcMove(t: Tree, trees: List<Tree>, fn: (a: Tree, b: Tree) -> Boolean): Int {
    var exit = false
    var i = 0
    var current = t
    while (!exit) {
        // println("currentTree: $current" )
        val next = trees.firstOrNull { fn(current, it)}
        // println("Next: $next" )
        if (next == null) exit = true
        else {
            i++
            when  {
                next.value >= t.value -> exit = true
                else -> current = next
            }
        }
    }
    return i
}
