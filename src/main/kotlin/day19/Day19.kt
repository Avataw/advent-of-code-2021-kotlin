package day19

import kotlin.math.abs

fun solveA(input: List<String>): Int {
    val map = parseToMap(input)
    val scanners = map.entries.map { Scanner(it.value) }.also { calculateAllScanners(it) }
    return scanners.flatMap { it.relativeBeaconPositions }.toSet().count()
}

private fun calculateAllScanners(scanners: List<Scanner>): List<Scanner> {

    val scannerZero = scanners.first()
    scannerZero.completed = true

    while (scanners.any { !it.completed }) {
        for (i in scanners.indices) {
            val a = scanners[i]

            for (j in scanners.indices) {
                val b = scanners[j]
                if (a == b || b.completed) continue
                if (a.completed) b.matchesOtherScanner(a)
            }
        }
    }
    return scanners
}

fun parseToMap(input: List<String>): Map<Int, List<String>> {
    val map = mutableMapOf<Int, MutableList<String>>()
    var mapCounter = 0
    input.forEach { line ->
        if (line.isBlank()) mapCounter++
        else map[mapCounter] = map.getOrDefault(mapCounter, mutableListOf()).also { it.add(line) }
    }
    return map
}

data class Position(val x: Int, val y: Int, val z: Int) {
    override fun equals(other: Any?): Boolean = (other is Position)
            && x == other.x
            && y == other.y
            && z == other.z

    override fun toString(): String = "$x,$y,$z"

    fun calculateManhattanDistance(other: Position): Int {
        val xDifference = abs(x - other.x)
        val yifference = abs(y - other.y)
        val zifference = abs(z - other.z)
        return xDifference + yifference + zifference
    }
}

data class PositionMapping(val axis: Char, val offset: Int, val negated: Boolean)

class Scanner(input: List<String>) {
    private val name: String
    private val positionMappings = mutableMapOf(
        'x' to PositionMapping('x', 0, false),
        'y' to PositionMapping('y', 0, false),
        'z' to PositionMapping('z', 0, false)
    )
    var relativeBeaconPositions: List<Position>
    var completed = false

    init {
        name = input.first()
        relativeBeaconPositions = input.drop(1).map {
            val xyz = it.split(",").map { number -> number.toInt() }
            Position(xyz[0], xyz[1], xyz[2])
        }
    }

    fun getRelativePosition(): Position = Position(
        positionMappings['x']!!.offset,
        positionMappings['y']!!.offset,
        positionMappings['z']!!.offset,
    )

    fun matchesOtherScanner(scanner: Scanner): Boolean {

        val mappingForX = findPositionalMapping(scanner.relativeBeaconPositions.map { it.x }) ?: return false
        positionMappings['x'] = mappingForX
        val mappingForY = findPositionalMapping(scanner.relativeBeaconPositions.map { it.y }) ?: return false
        positionMappings['y'] = mappingForY
        val mappingForZ = findPositionalMapping(scanner.relativeBeaconPositions.map { it.z }) ?: return false
        positionMappings['z'] = mappingForZ

        calculateAbsoluteBeacons()
        return true
    }

    private fun findPositionalMapping(other: List<Int>): PositionMapping? {
        var offset = findHighestMatch(other, relativeBeaconPositions.map { it.x })
        if (offset != null) return PositionMapping('x', offset, false)

        offset = findHighestMatch(other, relativeBeaconPositions.map { -it.x })
        if (offset != null) return PositionMapping('x', offset, true)

        offset = findHighestMatch(other, relativeBeaconPositions.map { it.y })
        if (offset != null) return PositionMapping('y', offset, false)

        offset = findHighestMatch(other, relativeBeaconPositions.map { -it.y })
        if (offset != null) return PositionMapping('y', offset, true)

        offset = findHighestMatch(other, relativeBeaconPositions.map { it.z })
        if (offset != null) return PositionMapping('z', offset, false)

        offset = findHighestMatch(other, relativeBeaconPositions.map { -it.z })
        if (offset != null) return PositionMapping('z', offset, true)

        return null
    }

    private fun calculateAbsoluteBeacons() {

        relativeBeaconPositions = relativeBeaconPositions.map {
            val xMapping = positionMappings['x']!!
            val yMapping = positionMappings['y']!!
            val zMapping = positionMappings['z']!!
            var x = 0
            var y = 0
            var z = 0

            if (xMapping.axis == 'x') {
                x = if (xMapping.negated) -it.x else it.x
            }
            if (xMapping.axis == 'y') {
                x = if (xMapping.negated) -it.y else it.y
            }
            if (xMapping.axis == 'z') {
                x = if (xMapping.negated) -it.z else it.z
            }

            if (yMapping.axis == 'x') {
                y = if (yMapping.negated) -it.x else it.x
            }
            if (yMapping.axis == 'y') {
                y = if (yMapping.negated) -it.y else it.y
            }
            if (yMapping.axis == 'z') {
                y = if (yMapping.negated) -it.z else it.z
            }

            if (zMapping.axis == 'x') {
                z = if (zMapping.negated) -it.x else it.x
            }
            if (zMapping.axis == 'y') {
                z = if (zMapping.negated) -it.y else it.y
            }
            if (zMapping.axis == 'z') {
                z = if (zMapping.negated) -it.z else it.z
            }

            Position(x + xMapping.offset, y + yMapping.offset, z + zMapping.offset)
        }
        completed = true
    }

    private fun findHighestMatch(other: List<Int>, all: List<Int>): Int? {
        val sameDifferences = mutableMapOf<Int, Int>()
        for (otherElement in other) {
            for (thisElement in all) {
                val difference = otherElement - thisElement
                sameDifferences[difference] = sameDifferences.getOrDefault(difference, 0) + 1
            }
        }
        val highestOccurence = sameDifferences.entries.maxByOrNull { it.value } ?: return null
        return if (highestOccurence.value >= 12) highestOccurence.key else null
    }

    override fun toString(): String {
        return "$name \n" + relativeBeaconPositions.joinToString("\n") + "\n"
    }
}


fun solveB(input: List<String>): Int {
    val map = parseToMap(input)
    val scanners = map.entries.map { Scanner(it.value) }.also { calculateAllScanners(it) }

    val scannerPositions = scanners.map {
        it.getRelativePosition()
    }
    var highestDistance = 0

    for (i in scannerPositions.indices) {
        val a = scannerPositions[i]
        for (j in scannerPositions.indices) {
            val b = scannerPositions[j]
            val distance = a.calculateManhattanDistance(b)
            if (distance > highestDistance) highestDistance = distance
        }
    }

    return highestDistance
}