package day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay12 {

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(12)

        val solution = solveA(input)
        assertEquals(226, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(12)

        val solution = solveA(input)
        assertEquals(4104, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(12)

        val solution = solveB(input)
        assertEquals(3509, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(12)

        val solution = solveB(input)
        assertEquals(119760, solution)
    }
}


