package day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readFileAsLinesUsingUseLines
import readTestFileAsLinesUsingUseLines

class TestDay16 {

    @Test
    fun `should work for the given test input`() {
        val input1 = listOf("8A004A801A8002F478")
        val input2 = listOf("620080001611562C8802118E34")
        val input3 = listOf("C0015000016115A2E0802F182340")
        val input4 = listOf("A0016C880162017C3686B18A3D4780")

        assertEquals(6, solveA(listOf("D2FE28")))
        assertEquals(9, solveA(listOf("38006F45291200")))
        assertEquals(14, solveA(listOf("EE00D40C823060")))
        assertEquals(16, solveA(input1))
        assertEquals(12, solveA(input2))
        assertEquals(23, solveA(input3))
        assertEquals(31, solveA(input4))
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(16)

        val solution = solveA(input)
        assertEquals(893, solution)
    }

    @Test
    fun `should work with b for the given test input`() {
        assertEquals(3, solveB(listOf("C200B40A82")))
        assertEquals(54, solveB(listOf("04005AC33890")))
        assertEquals(7, solveB(listOf("880086C3E88112")))
        assertEquals(9, solveB(listOf("CE00C43D881120")))
        assertEquals(1, solveB(listOf("D8005AC2A8F0")))
        assertEquals(0, solveB(listOf("F600BC2D8F")))
        assertEquals(0, solveB(listOf("9C005AC2F8F0")))
        assertEquals(1, solveB(listOf("9C0141080250320F1802104A08")))
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(16)

        val solution = solveB(input)
        assertEquals(227, solution)
    }
}


