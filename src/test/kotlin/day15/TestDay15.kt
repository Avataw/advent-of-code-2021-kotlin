package day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay15 {

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(15)

        val solution = solveA(input)
        assertEquals(40, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(15)

        val solution = solveA(input)
        assertEquals(714, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(15)

        val solution = solveB(input)
        assertEquals(315, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(15)

        val solution = solveB(input)
        assertEquals(2948, solution)
    }
}


