package day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines

class TestDay20 {

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(20)

        val solution = solveA(input)
        assertEquals(5231, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(20)

        val solution = solveB(input)
        assertEquals(14279, solution)
    }
}


