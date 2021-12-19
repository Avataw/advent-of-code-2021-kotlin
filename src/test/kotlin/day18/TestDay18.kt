package day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay18 {

    @Test
    fun `should work for the super small test input`() {
        val input = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]")

        val solution = calculateSnailAdds(input)
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", solution)
    }

    @Test
    fun `should work for the smaller test input`() {
        val input = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]")

        val solution = calculateSnailAdds(input)
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", solution)
    }

    @Test
    fun `should work for the small test input`() {
        val input = listOf("[1,1]", "[2,2]", "[3,3]", "[4,4]", "[5,5]", "[6,6]")

        val solution = calculateSnailAdds(input)
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", solution)
    }

    @Test
    fun `should calculate magnitudes properly`() {
        assertEquals("29", calculateMagnitude("[9,1]"))
        assertEquals("143", calculateMagnitude("[[1,2],[[3,4],5]]"))
        assertEquals("1384", calculateMagnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"))
        assertEquals("445", calculateMagnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]"))
        assertEquals("791", calculateMagnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]"))
        assertEquals("1137", calculateMagnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]"))
        assertEquals("3488", calculateMagnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"))
    }

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(18)

        val solution = solveA(input)
        assertEquals(4140, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(18)

        val solution = solveA(input)
        assertEquals(4202, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(18)

        val solution = solveB(input)
        assertEquals(3993, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(18)

        val solution = solveB(input)
        assertEquals(4779, solution)
    }
}

