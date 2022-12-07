fun main() {
    val day = "Day07"
    fun dirTree(input: List<String>): Dir {
        val rootDir = Dir("/")
        var currentDir: Dir =  rootDir
        input.forEach { line ->
            // println("line: $line - $currentDir")
            when {
                line.startsWith("$ cd") -> currentDir = chDir(line.substringAfter("$ cd "), currentDir)
                line.startsWith("$ ls") -> Unit
                line.startsWith("dir ") -> with(Dir(line.substringAfter("dir "), currentDir)) {
                    currentDir.subDirs.add(this)
                }
                else -> currentDir.files.add(File(line))
            }
        }
        return rootDir
    }

    fun part1(input:List<String>): Long {
        val root = dirTree(input)
        val maxSize = 100000
        val validDirs = getSubDirs(listOf(root), emptyList()) { it.size() <=  maxSize } // getSubDirs(emptyList(), root) { it.size() <=  maxSize }
        // println("Valid dirs: $validDirs")
        return validDirs.sumOf { it.size() }
    }

    fun part2(input: List<String>): Long {
        val root = dirTree(input)
        val diskSize = 70000000
        val updateSize = 30000000
        val freeSpace = diskSize - root.size()
        // println("Free space: $freeSpace")
        val needSpace = updateSize - freeSpace
        // println("Need space: $needSpace")
        val validDirs = getSubDirs(listOf(root), emptyList()) { it.size() >=  needSpace }
        // println("Valid dirs: $validDirs")
        return validDirs.minOf { it.size() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("${day}_test")
    // println(part1(testInput))
    // println(part2(testInput))
    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = readInput(day)
    println(part1(input))
    println(part2(input))
}

private tailrec fun getSubDirs(check: List<Dir>, result: List<Dir>, fn: (d: Dir) -> Boolean): List<Dir> {
    return when {
        check.isEmpty() -> result
        else -> {
            val head = check.first()
            val newResult = if (fn(head)) result + head else result
            getSubDirs(check.drop(1) + head.subDirs, newResult, fn)
        }
    }
}

private tailrec fun chDir(name: String, dir: Dir): Dir {
    return when(name) {
        "/" -> if (dir.name == "/" || dir.parentDir == null) dir else chDir(name, dir.parentDir)
        ".." -> dir.parentDir!!
        else -> dir.subDirs.firstOrNull { it.name == name } ?: dir
    }
}

private data class File(val name: String, val size: Int) {
    constructor(line: String): this(line.split(" ").last(), line.split(" ").first().toInt())
}
private data class  Dir(val name: String, val parentDir: Dir? = null) {
    val subDirs: MutableList<Dir> = mutableListOf()
    val files: MutableList<File> = mutableListOf()
    fun  size(): Long = files.sumOf { it.size } + subDirs.sumOf { it.size() }
}
