package day23

import day23.ArmadilloType.*
import kotlin.math.abs

enum class ArmadilloType {
    A, B, C, D,
}

val floor = (0..10).map { Position(it, 0) }

val rooms = mapOf(
    A to Room(Position(2, -1), Position(2, -2)),
    B to Room(Position(4, -1), Position(4, -2)),
    C to Room(Position(6, -1), Position(6, -2)),
    D to Room(Position(8, -1), Position(8, -2))
)

fun solveA(armadillos: Map<Position, Armadillo>): Int? {

    val movableArmadillos = armadillos.filter { !it.value.finished }

    if (movableArmadillos.isEmpty()) {
        println("ALL FINISHED")
        return armadillos.values.sumOf { a -> a.movement }
    }

    val allPossibleMoves = movableArmadillos.map {
        it to findPossiblePositionsForArmadillo(it.key, it.value, armadillos)
    }.toMap()

    if (allPossibleMoves.values.flatten().isEmpty()) {
        println("NO NEXT MOVES: $movableArmadillos")
        return null
    }

    val result = allPossibleMoves.mapNotNull {
        val results = it.value.mapNotNull { move ->
            val newArmadillos = armadillos.toMutableMap()
            val armadillo = newArmadillos[it.key.key]!!
            armadillo.move(it.key.key, move)
            newArmadillos.remove(it.key.key)
            newArmadillos[move] = armadillo
            solveA(newArmadillos)
        }
        if (results.isEmpty()) {
            println("INNER RESULT IS EMPTY?")
            null
        } else results.minOf { result -> result }
    }
    return if (result.isEmpty()) {
        println("OUTER RESULT IS EMPTY?")
        null
    } else result.minOf { it }
}

fun findPossiblePositionsForArmadillo(
    currentPosition: Position,
    armadillo: Armadillo,
    armadillos: Map<Position, Armadillo>
): List<Position> {

    val possibleMovements = mutableListOf<Position>()
    val targetRooms = rooms[armadillo.type]!!

    if (currentPosition.y == -2) {
        val moveUp = Position(currentPosition.x, -1)
        if (!armadillos.containsKey(moveUp)) possibleMovements.add(moveUp)
        else return possibleMovements

        val moveTwoUp = Position(currentPosition.x, 0)
        if (!armadillos.containsKey(moveTwoUp)) possibleMovements.add(moveTwoUp)
        else return possibleMovements
    }

    if (currentPosition.y == -1) {
        if (currentPosition != targetRooms.upperRoom) {
            val moveOut = Position(currentPosition.x, 0)
            if (!armadillos.containsKey(moveOut)) return possibleMovements

            val moveOutLeft = Position(currentPosition.x - 1, 0)
            val moveOutRight = Position(currentPosition.x + 1, 0)
            if (armadillos.containsKey(moveOutLeft) && armadillos.containsKey(moveOutRight)) return possibleMovements
        } else {
            val moveDown = Position(currentPosition.x, -2)
            if (!armadillos.containsKey(moveDown)) possibleMovements.add(moveDown)
        }
    }

    val armadillosByDistance = armadillos.entries
        .filter { it.key.y == 0 }
        .map { it.key to abs(it.key.x - currentPosition.x) }

    val closestLeft = armadillosByDistance
        .filter { it.first.x < currentPosition.x }
        .minByOrNull { it.second }?.first?.x
    val closestRight = armadillosByDistance
        .filter { it.first.x > currentPosition.x }
        .minByOrNull { it.second }?.first?.x

    val floorsInRange = floor
        .filter { if (closestLeft != null) it.x > closestLeft else true }
        .filter { if (closestRight != null) it.x < closestRight else true }

    if (!armadillo.waiting) {
        val floorsInRangeWithoutDoors = floorsInRange.filter { it.x in listOf(0, 1, 3, 5, 7, 9, 10) }
        possibleMovements.addAll(floorsInRangeWithoutDoors)
    }

    if (targetRooms.upperRoom.x in floorsInRange.map { it.x }) {
        if (armadillos.filter { it.key in listOf(targetRooms.upperRoom, targetRooms.bottomRoom) }
                .all { it.value.type == armadillo.type }) {
            if (!armadillos.containsKey(targetRooms.upperRoom)) possibleMovements.add(targetRooms.upperRoom)
            if (!armadillos.containsKey(targetRooms.bottomRoom)) possibleMovements.add(targetRooms.bottomRoom)
        }
    }
    return possibleMovements
}

data class Position(val x: Int, val y: Int)

data class Room(val upperRoom: Position, val bottomRoom: Position)

data class Armadillo(val type: ArmadilloType) {
    var finished = false
    var waiting = false
    var movement = 0

    private val movementCosts = mapOf(A to 1, B to 10, C to 100, D to 1000)


    fun move(from: Position, target: Position) {
        if (target.y == -2) finished = true
        if (target.y == 0) waiting = true

        val distance = abs(target.x - from.x) + abs(target.y - from.y)
        movement = distance * movementCosts[type]!!

    }

    override fun toString(): String {
        return "$type: -> finished: $finished waiting: $waiting movement: $movement"
    }
}

fun solveB(input: List<String>): Int {
    return TODO()
}
