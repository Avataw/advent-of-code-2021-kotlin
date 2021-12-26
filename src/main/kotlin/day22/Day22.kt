package day22

import kotlin.math.max
import kotlin.math.min

fun solveA(input: List<String>): Int {

    val cuboidToIsOn = mutableMapOf<Cube, Boolean>()

    input.forEach {
        val turnOnAndCoords = it.split(" ")

        val turnOn = turnOnAndCoords[0] == "on"

        val xyzCoords = turnOnAndCoords[1].split(",")
        val xFromTo = xyzCoords[0].drop(2).split("..").map { x -> x.toInt() }
        val xRange = max(xFromTo[0], -50)..min(xFromTo[1], 50)
        val yFromTo = xyzCoords[1].drop(2).split("..").map { y -> y.toInt() }
        val yRange = max(yFromTo[0], -50)..min(yFromTo[1], 50)
        val zFromTo = xyzCoords[2].drop(2).split("..").map { z -> z.toInt() }
        val zRange = max(zFromTo[0], -50)..min(zFromTo[1], 50)

        xRange.forEach { x ->
            yRange.forEach { y ->
                zRange.forEach { z ->
                    cuboidToIsOn[Cube(x, y, z)] = turnOn
                }
            }
        }
    }

    return cuboidToIsOn
        .filter { it.value }
        .filter { it.key.x in -50..50 && it.key.y in -50..50 && it.key.z in -50..50 }
        .count()

}

data class Cube(val x: Int, val y: Int, val z: Int)

data class Cuboid(val x: IntRange, val y: IntRange, val z: IntRange) {
    val pointsInsideCount = x.count().toLong() * y.count().toLong() * z.count().toLong()
    var isOn = false

    fun overlap(other: Cuboid): Cuboid? {
        val overlap = Cuboid(
            max(other.x.first, x.first)..min(other.x.last, x.last),
            max(other.y.first, y.first)..min(other.y.last, y.last),
            max(other.z.first, z.first)..min(other.z.last, z.last)
        )
        return if (overlap.pointsInsideCount > 0) overlap else null
    }
}

fun List<String>.toCuboids(): List<Cuboid> =
    this.map {
        val turnOnAndCoords = it.split(" ")

        val turnOn = turnOnAndCoords[0] == "on"

        val xyzCoords = turnOnAndCoords[1].split(",")
        val xFromTo = xyzCoords[0].drop(2).split("..").map { x -> x.toInt() }
        val xRange = xFromTo[0]..xFromTo[1]
        val yFromTo = xyzCoords[1].drop(2).split("..").map { y -> y.toInt() }
        val yRange = yFromTo[0]..yFromTo[1]
        val zFromTo = xyzCoords[2].drop(2).split("..").map { z -> z.toInt() }
        val zRange = zFromTo[0]..zFromTo[1]

        Cuboid(xRange, yRange, zRange).also { c -> c.isOn = turnOn }
    }


fun solveB(input: List<String>): Long {

    val result = mutableListOf<Cuboid>()

    input.toCuboids()
        .forEach { cuboid ->
            result.addAll(
                result.mapNotNull {
                    cuboid.overlap(it)?.also { overlap -> overlap.isOn = !it.isOn }
                })

            if (cuboid.isOn) result.add(cuboid)
        }

    return result.sumOf { if (it.isOn) it.pointsInsideCount else -it.pointsInsideCount }
}

