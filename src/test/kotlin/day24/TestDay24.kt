package day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay24 {

    @Test
    fun `should work for the actual input`() {
        assertEquals(93499629698999, solveA())
    }

    @Test
    fun `should work with b for the actual input`() {
        assertEquals(11164118121471, solveB())
    }
}


