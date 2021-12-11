package day11

import java.lang.Character.getNumericValue

data class Octopus(var energyLevel: Int) {

    var adjacentOctopuses: List<Octopus> = listOf()
    var flashes: Int = 0
    var alreadyFlashed: Boolean = false

    fun setSurroundingOctopuses(surrounding: List<Octopus>) {
        adjacentOctopuses = surrounding
    }

    fun flash() {
        energyLevel++
        if (energyLevel == 10 && !alreadyFlashed) {
            flashes++
            alreadyFlashed = true
            adjacentOctopuses.forEach { it.flash() }
        }
    }

    fun resetIfFlashed() {
        if (alreadyFlashed) {
            energyLevel = 0
            alreadyFlashed = false
        }
    }


}

data class Position(val x: Int, val y: Int) {

    fun getSurroundingPositions(): List<Position> {
        val surrounding = mutableListOf<Position>()

        (x - 1..x + 1).forEach { x ->
            (y - 1..y + 1).forEach { y ->
                if (x != this.x || y != this.y) {
                    surrounding.add(Position(x, y))
                }
            }
        }
        return surrounding
    }
}

fun solveA(input: List<String>): Int {

    val startGrid = input.map { it.map(::getNumericValue) }

    val octopusesByPosition = mutableMapOf<Position, Octopus>()
    for (y in startGrid.indices) {
        for (x in startGrid[y].indices) {
            octopusesByPosition[Position(x, y)] = Octopus(startGrid[y][x])
        }
    }

    octopusesByPosition.forEach {
        val surrounding = it.key.getSurroundingPositions().mapNotNull { pos -> octopusesByPosition[pos] }
        it.value.setSurroundingOctopuses(surrounding)
    }

    for (step in 1..100) {
        octopusesByPosition.forEach { it.value.flash() }
        octopusesByPosition.forEach { it.value.resetIfFlashed() }

    }

    return octopusesByPosition.map { it.value }.sumOf { it.flashes }
}

fun solveB(input: List<String>): Int {
    val startGrid = input.map { it.map(::getNumericValue) }

    val octopusesByPosition = mutableMapOf<Position, Octopus>()
    for (y in startGrid.indices) {
        for (x in startGrid[y].indices) {
            octopusesByPosition[Position(x, y)] = Octopus(startGrid[y][x])
        }
    }

    octopusesByPosition.forEach {
        val surrounding = it.key.getSurroundingPositions().mapNotNull { pos -> octopusesByPosition[pos] }
        it.value.setSurroundingOctopuses(surrounding)
    }

    var counter = 1

    while (true) {
        octopusesByPosition.forEach { it.value.flash() }

        if (octopusesByPosition.all { it.value.alreadyFlashed }) {
            return counter
        }
        octopusesByPosition.forEach { it.value.resetIfFlashed() }
        counter++
    }
}
