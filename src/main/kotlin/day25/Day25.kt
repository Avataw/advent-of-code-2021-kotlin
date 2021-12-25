package day25

fun solveA(input: List<String>): Int {

    var seaMap = mapOf<Position, Direction>()
    val highestXIndex = input.first().indices.last()
    val highestYIndex = input.indices.last()

    var nextSeaMap = input.toSeaMap()
    var steps = 0
    while (nextSeaMap != seaMap) {
        steps++
        seaMap = nextSeaMap
        nextSeaMap = nextSeaMap.moveCucumbersEast(highestXIndex).moveCucumbersSouth(highestYIndex)
    }
    return steps
}

enum class Direction { SOUTH, EAST }

data class Position(val x: Int, val y: Int) {

    fun getRight(edge: Boolean): Position = if (edge) Position(0, y) else Position(x + 1, y)

    fun getDown(edge: Boolean): Position = if (edge) Position(x, 0) else Position(x, y + 1)
}

fun Char.toDirection(): Direction? = when (this) {
    'v' -> Direction.SOUTH
    '>' -> Direction.EAST
    else -> null
}

fun List<String>.toSeaMap(): Map<Position, Direction> =
    this.flatMapIndexed { y, line ->
        line.mapIndexedNotNull { x, character ->
            val direction = character.toDirection()
            if (direction != null) Position(x, y) to direction else null
        }
    }.toMap()

fun Map<Position, Direction>.moveCucumbersEast(xBound: Int): Map<Position, Direction> =
    this.map {
        val nextPosition = it.key.getRight(it.key.x == xBound)
        if (it.value == Direction.EAST && !this.containsKey(nextPosition)) nextPosition to it.value
        else it.key to it.value
    }.toMap()

fun Map<Position, Direction>.moveCucumbersSouth(yBound: Int): Map<Position, Direction> =
    this.map {
        val nextPosition = it.key.getDown(it.key.y == yBound)
        if (it.value == Direction.SOUTH && !this.containsKey(nextPosition)) nextPosition to it.value
        else it.key to it.value
    }.toMap()
