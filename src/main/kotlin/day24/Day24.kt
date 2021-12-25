package day24

import kotlin.math.floor

private const val firstOffset = 14
private const val lookUpTo = 1000000
private val possibleModelNumbers = 1..9

fun solveA(): Long =
    tracePossibleSolutionsBackwards()
        .reversed()
        .findHighestPossibleModelDigits()
        .joinToString("")
        .toLong()

fun solveB(): Long =
    tracePossibleSolutionsBackwards()
        .reversed()
        .findLowestPossibleModelDigits()
        .joinToString("")
        .toLong()

/**
 * Finds all possible solutions that can be found by a specific 'MONAD Formula'.
 * All Arithmetic Logic Unit (AUC) steps could be summarized in a calculation
 * with mod or without mod.
 */
fun findPossibleSolutions(
    resultOffset: Int,
    modOffset: Int?,
    targetNumbers: Set<Int>
): Map<Int, List<Solution>> {

    val modelNumberToSolution = mutableMapOf<Int, MutableList<Solution>>()

    for (input in 0..lookUpTo) {
        for (modelNumber in possibleModelNumbers) {
            var result: Int
            if (modOffset != null) {
                val x = if ((input % 26) - modOffset == modelNumber) 0 else 1
                result = floor(input / 26f).toInt()
                result *= (25 * x) + 1
                result += (modelNumber + resultOffset) * x
            } else {
                result = input * 26
                result += (modelNumber + resultOffset)
            }

            if (result in targetNumbers) modelNumberToSolution[modelNumber] =
                modelNumberToSolution.getOrDefault(modelNumber, mutableListOf())
                    .also { it.add(Solution(input, result)) }
        }
    }
    return modelNumberToSolution
}

data class Solution(val input: Int, val output: Int)

data class Offsets(val resultOffset: Int, val modOffset: Int? = null)

// looking through the input I realized that numbers were calculated with either 1 or 2 offsets
val offsets = mapOf(
    14 to Offsets(3, 14),
    13 to Offsets(13, 2),
    12 to Offsets(7, 3),
    11 to Offsets(6, 14),
    10 to Offsets(13),
    9 to Offsets(6),
    8 to Offsets(5, 9),
    7 to Offsets(16),
    6 to Offsets(13, 13),
    5 to Offsets(10),
    4 to Offsets(4, 0),
    3 to Offsets(5),
    2 to Offsets(8),
)

/**
 * We are looking backwards - in the end it has to be zero, therefore the first
 * set of targetNumbers is just 0
 */
fun tracePossibleSolutionsBackwards(): List<Map<Int, List<Solution>>> {
    var targetNumbers = setOf(0)

    return offsets.values
        .map { offset ->
            findPossibleSolutions(offset.resultOffset, offset.modOffset, targetNumbers)
                .also { targetNumbers = it.values.flatten().map { solutions -> solutions.input }.toSet() }
        }
}


private fun List<Map<Int, List<Solution>>>.findLowestPossibleModelDigits(): List<Int> =
    findLinkedDigits(
        this,
        possibleModelNumbers.first,
        possibleModelNumbers.first + firstOffset,
        false
    )

private fun List<Map<Int, List<Solution>>>.findHighestPossibleModelDigits(): List<Int> =
    findLinkedDigits(
        this,
        possibleModelNumbers.last,
        possibleModelNumbers.last + firstOffset,
        true
    )

private fun findLinkedDigits(
    numbers: List<Map<Int, List<Solution>>>,
    startDigit: Int,
    startSearch: Int,
    highest: Boolean
): List<Int> {

    val resultDigits = mutableListOf(startDigit)
    var searchValue = startSearch

    numbers.forEach { number ->
        val found = number.mapNotNull {
            val list = it.value.mapNotNull { inner -> if (inner.input == searchValue) inner to it.key else null }
            list.ifEmpty { null }
        }
        val result = if (highest) found.last() else found.first()

        resultDigits.add(result.first().second)
        searchValue = result.first().first.output
    }
    return resultDigits
}

