package day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay17 {

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(17)

        val solution = solveA(input)
        assertEquals(45, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(17)

        val solution = solveA(input)
        assertEquals(10011, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(17)

        val solution = solveB(input)
        assertEquals(112, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(17)

        val solution = solveB(input)
        assertEquals(2994, solution)
    }
}


