package day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay22 {

    @Test
    fun `should work for small test input`() {
        val input = listOf(
            "on x=10..12,y=10..12,z=10..12",
            "on x=11..13,y=11..13,z=11..13",
            "off x=9..11,y=9..11,z=9..11",
            "on x=10..10,y=10..10,z=10..10"
        )

        val solution = solveA(input)
        assertEquals(39, solution)
    }

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(22)

        val solution = solveA(input)
        assertEquals(590784, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(22)

        val solution = solveA(input)
        assertEquals(1659, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(22)

        val solution = solveB(input)
        assertEquals(195, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(22)

        val solution = solveB(input)
        assertEquals(227, solution)
    }
}


