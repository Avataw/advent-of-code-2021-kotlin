package day15

data class Position(val x: Int, val y: Int) {
    fun getAdjacentPositions(): List<Position> = listOf(
        Position(x, y + 1),
        Position(x + 1, y),
        Position(x, y - 1),
        Position(x - 1, y),
    )
}

data class Chiton(val position: Position, val riskLevel: Int) {
    val adjacentChitons: MutableList<Chiton> = mutableListOf()
    var visited = false
}

data class QueueChiton(val chiton: Chiton, val distanceToStart: Int)

fun solveA(input: List<String>): Int {

    val gridSize = input.size - 1

    val chitons = input.flatMapIndexed { y: Int, row: String ->
        row.mapIndexed { x: Int, column: Char ->
            val riskLevel = Character.getNumericValue(column)
            val pos = Position(x, y)
            Chiton(pos, riskLevel)
        }
    }.associateBy { it.position }

    chitons.map { it.value }
        .forEach { chiton ->
            val adjacentChitons = chiton.position.getAdjacentPositions().mapNotNull {
                chitons[it]
            }
            chiton.adjacentChitons.addAll(adjacentChitons)
        }

    val queue = mutableListOf(QueueChiton(chitons[Position(0, 0)]!!, 0))

    while (queue.size > 0) {

        val nextInQueue = queue.minByOrNull { it.distanceToStart } ?: return -1
        queue.remove(nextInQueue)
        nextInQueue.chiton.visited = true

        if (nextInQueue.chiton.position == Position(gridSize, gridSize)) return nextInQueue.distanceToStart

        queue.addAll(nextInQueue.chiton.adjacentChitons.filter { !it.visited }.map {
            QueueChiton(it, nextInQueue.distanceToStart + it.riskLevel)
        }.filter { !queue.contains(it) })
    }

    return -1
}


fun solveB(input: List<String>): Int {

    val inputPlusOne = input.map { it.addNumbersBy(1) }
    val inputPlusTwo = input.map { it.addNumbersBy(2) }
    val inputPlusThird = input.map { it.addNumbersBy(3) }
    val inputPlusFour = input.map { it.addNumbersBy(4) }

    val list = input.toMutableList()

    list.addAll(listOf(inputPlusOne, inputPlusTwo, inputPlusThird, inputPlusFour).flatten())

    return solveA(list.map {
        it + it.addNumbersBy(1) + it.addNumbersBy(2) + it.addNumbersBy(3) + it.addNumbersBy(4)
    })
}



fun String.addNumbersBy(add: Int): String = this.map {
    val number = Character.getNumericValue(it) + add
    if (number > 9) number - 9 else number
}.joinToString("")


