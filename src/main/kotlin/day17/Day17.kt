package day17

fun solveA(input: List<String>): Int {
    val targetArea = TargetArea(input.first())

    val probes = mutableListOf<Probe>()
    for (x in 0..150) {
        for (y in 0..150) {
            probes.add(Probe(x, y))
        }
    }

    var totalHighestYPosition = 0

    for (i in 0..1000) {
        probes.forEach {
            it.step()
            if (targetArea.isInTargetArea(it.position)) {
                if (it.highestYPosition > totalHighestYPosition) totalHighestYPosition = it.highestYPosition
            }
        }
    }

    return totalHighestYPosition
}

class Probe(xVelocity: Int, yVelocity: Int) {
    var position: Point = Point(0, 0)
    private var xVelocity: Int
    private var yVelocity: Int
    var highestYPosition: Int = 0
    var hitsTargetArea: Boolean = false

    init {
        this.xVelocity = xVelocity
        this.yVelocity = yVelocity
    }

    fun step() {
        position = Point(position.x + xVelocity, position.y + yVelocity)

        if (position.y > highestYPosition) highestYPosition = position.y

        if (xVelocity > 0) xVelocity-- else if (xVelocity < 0) xVelocity++
        yVelocity--
    }

    override fun toString(): String {
        return "Position: $position xVelocity: $xVelocity, yVelocity: $yVelocity"
    }

}

data class Point(val x: Int, val y: Int)

class TargetArea(input: String) {
    private val xRange: IntRange
    private val yRange: IntRange

    init {
        val targetAreaInput = input.dropWhile { it != 'x' }.split(", ")
        val xRangeInput = targetAreaInput[0].drop(2).split("..")
        val yRangeInput = targetAreaInput[1].drop(2).split("..")
        xRange = xRangeInput[0].toInt()..xRangeInput[1].toInt()
        yRange = yRangeInput[0].toInt()..yRangeInput[1].toInt()
    }

    fun isInTargetArea(point: Point): Boolean =
        xRange.contains(point.x) && yRange.contains(point.y)
}


fun solveB(input: List<String>): Int {
    val targetArea = TargetArea(input.first())

    val probes = mutableListOf<Probe>()
    for (x in -150..200) {
        for (y in -150..200) {
            probes.add(Probe(x, y))
        }
    }

    for (i in 0..1000) {
        probes.filter { !it.hitsTargetArea }.forEach {
            it.step()
            if (targetArea.isInTargetArea(it.position)) {
                it.hitsTargetArea = true
            }
        }
    }

    return probes.filter { it.hitsTargetArea }.size
}
