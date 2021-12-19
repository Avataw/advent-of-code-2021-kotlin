package day18

import kotlin.math.ceil
import kotlin.math.exp
import kotlin.math.floor

fun solveA(input: List<String>): Int {
    val addition = calculateSnailAdds(input)
    return calculateMagnitude(addition).toInt()
}

fun calculateMagnitude(input: String): String {
    var result = input
    val bracketContent = mutableListOf<Int>()
    for (i in input.indices) {
        val next = input[i]
        if (next != '[') {
            if (bracketContent.isNotEmpty() && next == ']') {
                break
            }
            bracketContent.add(i)
        } else {
            bracketContent.clear()
        }
    }

    return if (bracketContent.isEmpty()) result
    else {
        val replace = bracketContent.map { input[it] }.joinToString("").split(",")
        val magnitude = replace[0].toInt() * 3 + replace[1].toInt() * 2

        result = result.substring(0 until bracketContent.first() - 1) +
                magnitude +
                result.substring(bracketContent.last() + 2 until result.length)

        result.toIntOrNull() ?: return calculateMagnitude(result)
        return result
    }


}

fun calculateSnailAdds(input: List<String>): String {

    var result = input.first()

    for (i in 1 until input.size) {
        result = "[$result,${input[i]}]"
        var previousResult = ""
        while (previousResult != result) {
            previousResult = result
            result = explode(result)
            if (previousResult == result) {
                result = split(result)
            }
        }
    }

    return result
}

fun split(input: String): String {
    var result = input
    val numberIndicesToReplace = mutableListOf<Int>()
    for (i in result.indices) {
        val next = result[i]
        if (next != '[' && next != ']' && next != ',') {
            numberIndicesToReplace.add(i)
        } else if (numberIndicesToReplace.count() > 1) {
            break
        } else {
            numberIndicesToReplace.clear()
        }
    }

    if (numberIndicesToReplace.isNotEmpty()) {
        val numberToSplit = numberIndicesToReplace.map { result[it] }.joinToString("").toFloat()
        val replace = "[${floor(numberToSplit / 2).toInt()},${ceil(numberToSplit / 2).toInt()}]"

        result = result.substring(0 until numberIndicesToReplace.first()) +
                replace +
                result.substring(numberIndicesToReplace.last() + 1 until result.length)
    }

    return result
}

fun explode(input: String): String {
    var result = input

    var indexOfExplosion = -1
    var bracketCounter = 0
    for (i in result.indices) {
        if (result[i] == '[') bracketCounter++
        else if (result[i] == ']') bracketCounter--

        if (bracketCounter == 5) {
            indexOfExplosion = i + 1
            break
        }
    }

    if (indexOfExplosion != -1) {
        val explosionStart = indexOfExplosion
        val explosionEnd = result.drop(explosionStart).indexOfFirst { it == ']' } + explosionStart
        val explosion = result.subSequence(explosionStart, explosionEnd).split(",").map { it.toInt() }

        val resultLeft = explodeLeft(result.substring(0, explosionStart - 1), explosion[0])
        val resultRight = explodeRight(result.substring(explosionEnd + 1, result.length), explosion[1])
        result = resultLeft + "0" + resultRight
    }
    return result
}

fun explodeLeft(input: String, leftExplosionValue: Int): String {
    var result = input
    val previousValue = mutableListOf<Int>()
    for (backwardsIndex in result.length - 1 downTo 0) {
        val previous = result[backwardsIndex]
        if (previous != '[' && previous != ']' && previous != ',') {
            previousValue.add(backwardsIndex)
        } else if (previousValue.isNotEmpty()) break
    }
    if (previousValue.isNotEmpty()) {
        val leftExplosionNumber =
            previousValue.map { result[it] }.reversed().joinToString("").toInt() + leftExplosionValue
        result = result.substring(0 until previousValue.last()) +
                leftExplosionNumber.toString() +
                result.substring(previousValue.first() + 1 until result.length)
    }
    return result
}

fun explodeRight(input: String, rightExplosionValue: Int): String {
    var result = input

    val nextValue = mutableListOf<Int>()
    for (nextIndex in result.indices) {
        val next = result[nextIndex]
        if (next != '[' && next != ']' && next != ',') {
            nextValue.add(nextIndex)
        } else if (nextValue.isNotEmpty()) break
    }

    if (nextValue.isNotEmpty()) {
        val rightExplosionNumber = nextValue.map { result[it] }.joinToString("").toInt() + rightExplosionValue
        result = result.substring(0 until nextValue.first()) +
                rightExplosionNumber.toString() +
                result.substring(nextValue.last() + 1 until result.length)
    }

    return result
}

fun solveB(input: List<String>): Int {

    var highestMagnitude = 0

    for (i in input.indices) {
        for (j in input.indices) {
            if (i == j) continue
            val addition = calculateSnailAdds(listOf(input[i], input[j]))
            val magnitude = calculateMagnitude(addition).toInt()
            if (magnitude > highestMagnitude) highestMagnitude = magnitude
        }
    }

    return highestMagnitude
}
