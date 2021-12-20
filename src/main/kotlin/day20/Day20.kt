package day20

fun solveA(input: List<String>): Int = solve(input, 2)
fun solveB(input: List<String>): Int = solve(input, 50)

private fun solve(input: List<String>, times: Int): Int {
    val imageEnhancementAlgorithm = input.first().mapIndexed { index, c -> index to (c == '#') }.toMap()

    var grid = input.drop(2)
        .flatMapIndexed { y: Int, line: String -> line.mapIndexed { x, c -> Position(x, y) to (c == '#') } }
        .toMap()

    for (i in 1..times) {
        grid = padWithPixels(grid, i % 2 == 0)
        grid = padWithPixels(grid, i % 2 == 0)

        grid = grid.enhance(imageEnhancementAlgorithm, i % 2 == 0)
    }

    return grid.entries.count { it.value }
}

private fun Map<Position, Boolean>.enhance(nextValueMap: Map<Int, Boolean>, on: Boolean) =
    entries.associate { entry ->
        val decimal = entry.key.getSurroundingPositions()
            .map {
                if (this[it] == null) if (on) 1 else 0
                else if (this[it]!!) 1 else 0
            }
            .joinToString("")
            .toInt(2)

        entry.key to nextValueMap[decimal]!!
    }

private fun padWithPixels(grid: Map<Position, Boolean>, on: Boolean) = grid.keys
    .flatMap { position ->
        position.getSurroundingPositions().map { if (!grid.containsKey(it)) it to on else it to grid[it]!! }
    }.toMap()


data class Position(val x: Int, val y: Int, val padding: Boolean = false) {
    fun getSurroundingPositions() = listOf(
        Position(x - 1, y - 1),
        Position(x, y - 1),
        Position(x + 1, y - 1),
        Position(x - 1, y),
        this,
        Position(x + 1, y),
        Position(x - 1, y + 1),
        Position(x, y + 1),
        Position(x + 1, y + 1),
    )
}
