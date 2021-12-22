package day22

import kotlin.math.max
import kotlin.math.min

data class Cuboid(val x: Int, val y: Int, val z: Int)

fun solveA(input: List<String>): Int {

    val cuboidToIsOn = mutableMapOf<Cuboid, Boolean>()

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
                    cuboidToIsOn[Cuboid(x, y, z)] = turnOn
                }
            }
        }
    }

    return cuboidToIsOn
        .filter { it.value }
        .filter { it.key.x in -50..50 && it.key.y in -50..50 && it.key.z in -50..50 }
        .count()

}

fun solveB(input: List<String>): Int {
    return TODO()
}
