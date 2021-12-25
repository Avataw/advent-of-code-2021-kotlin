package day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay23 {

    @Test
    fun `should work for the given test input`() {
        val armadillos = mapOf(
            Position(2, -1) to Armadillo(ArmadilloType.B),
            Position(2, -2) to Armadillo(ArmadilloType.A),

            Position(4, -1) to Armadillo(ArmadilloType.C),
            Position(4, -2) to Armadillo(ArmadilloType.D),

            Position(6, -1) to Armadillo(ArmadilloType.B),
            Position(6, -2) to Armadillo(ArmadilloType.C),

            Position(8, -1) to Armadillo(ArmadilloType.D),
            Position(8, -2) to Armadillo(ArmadilloType.A),
        )

        val solution = solveA(armadillos)
        assertEquals(12521, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(23)

        val solution = solveA(emptyMap())
        assertEquals(1659, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(23)

        val solution = solveB(input)
        assertEquals(195, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(23)

        val solution = solveB(input)
        assertEquals(227, solution)
    }
}


