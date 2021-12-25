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

        val solution = solveB(input)
        assertEquals(39, solution)
    }

    @Test
    fun `should work for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(22)

        val solution = solveA(input)
        assertEquals(474140, solution)
    }

    @Test
    fun `should work for the actual input`() {
        val input = readFileAsLinesUsingUseLines(22)

        val solution = solveA(input)
        assertEquals(545118, solution)
    }

    @Test
    fun `should do add stuff`() {
        val input = listOf(
            "on x=-14..32,y=-22..28,z=-25..26",
            "on x=-20..32,y=-3..46,z=-33..18"
        )
        //actual result = 196268
        //both volumes  = 262444
        //difference =     66176

        //first volume
        val resultA = (-14..32).count() * (-22..28).count() * (-25..26).count()
        //second volume
        val resultB = (-20..32).count() * (-3..46).count() * (-33..18).count()

        //overlap = maxOf(firstNumber) .. minOf(secondNumber) * ...
        val resultC = (-14..32).count() *
                (-3..28).count() *
                (-25..18).count()

        //A+B = A+B-C
        val solution = solveB(input)
        assertEquals(resultA + resultB - resultC, solution)
    }

    @Test
    fun `should do minus stuff`() {
        val input = listOf(
            "on x=-14..32,y=-22..28,z=-25..26",
            "off x=-20..32,y=-3..46,z=-33..18"
        )
        //actual result = 196268
        //both volumes  = 262444
        //difference =     66176

        //first volume
        val resultA = (-14..32).count() * (-22..28).count() * (-25..26).count()

        //overlap = maxOf(firstNumber) .. minOf(secondNumber) * ...
        val resultC = (-14..32).count() *
                (-3..28).count() *
                (-25..18).count()

        //A-B = A-C
        val solution = solveB(input)
        assertEquals(resultA - resultC, solution)
    }

//    @Test
//    fun `should do both when no overlap stuff`() {
//        val input = listOf(
//            "on x=1..10,y=1..10,z=1..10",
//            "on x=5..15,y=5..15,z=5..15",
//            "off x=8..13,y=8..13,z=8..13",
//            "on x=9..10,y=9..10,z=9..10",
//
//            )
//        val a = Cuboid((1..10), (1..10), (1..10))
//        val b = Cuboid((5..15), (5..15), (5..15))
//        val c = Cuboid((8..13), (8..13), (8..13))
//        val d = Cuboid((9..10), (9..10), (9..10))
//
//        val solutionB =
//            a.pointsInsideCount + b.pointsInsideCount - a.overlap(b).pointsInsideCount -
//                    a.overlap(c).pointsInsideCount - b.overlap(c).pointsInsideCount + a.overlap(c)
//                .overlap(b.overlap(c)).pointsInsideCount +
//                    d.pointsInsideCount
//        val solution = solveA(input)
//        assertEquals(solutionB, solution)
//    }

    @Test
    fun `should do same stuff`() {
        val input = listOf(
            "on x=1..10,y=1..10,z=1..10",
            "on x=1..10,y=1..10,z=1..10",
            "on x=1..10,y=1..10,z=1..10",
            "on x=1..10,y=1..10,z=1..10",
//            "on x=5..15,y=5..15,z=5..15",
//            "off x=8..13,y=8..13,z=8..13",
//            "off x=9..15,y=2..3,z=-4..3",
//            "on x=5..15,y=5..15,z=5..15",
        )
        assertEquals(solveA(input).toLong(), solveB(input))
    }


    @Test
    fun `should work with b for the given test input`() {
        val input = readTestFileAsLinesUsingUseLines(22)

        val solution = solveB(input)
        assertEquals(2758514936282235, solution)
    }

    @Test
    fun `should work with b for the actual input`() {
        val input = readFileAsLinesUsingUseLines(22)

        val solution = solveB(input)
        assertEquals(227, solution)
    }
}


