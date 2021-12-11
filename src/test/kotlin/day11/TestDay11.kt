package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay11 {

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(11)

        val solution = solveA(input)
        assertEquals(1656, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(11)

        val solution = solveA(input)
        assertEquals(1659, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(11)

        val solution = solveB(input)
        assertEquals(195, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(11)

        val solution = solveB(input)
        assertEquals(3999363569, solution)
    }
}


