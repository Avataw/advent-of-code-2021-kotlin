package day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay21 {

    @Test
    fun `should work for the given test input`() {
        val player1 = Player(4)
        val player2 = Player(8)
        val solution = solveA(player1, player2)
        assertEquals(739785, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val player1 = Player(7)
        val player2 = Player( 5)
        val solution = solveA(player1, player2)
        assertEquals(798147, solution)
    }

    //
    @Test
    fun `should work with b for the given test input`() {
        val player1 = Player(4)
        val player2 = Player( 8)
        val solution = solveB(player1, player2)
        assertEquals(444356092776315, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val player1 = Player(7)
        val player2 = Player( 5)
        val solution = solveB(player1, player2)
        assertEquals(809953813657517, solution)
    }
}


