package day21

fun solveA(first: Player, second: Player): Int {

    val dieRolls = (1..1000).chunked(3)
    var dieRollCount = 0
    var firstTurn = true

    for (roll in dieRolls) {
        firstTurn = if (firstTurn) {
            first.move(roll.sum())
            false
        } else {
            second.move(roll.sum())
            true
        }
        dieRollCount += 3
        if (first.score >= 1000 || second.score >= 1000) break
    }

    return if (first.score >= 1000) second.score * dieRollCount
    else first.score * dieRollCount
}

data class Player(val startingPosition: Int, val startingScore: Int = 0) {
    var space = startingPosition
    var score = startingScore

    fun move(amount: Int): Int {
        val nextSpace = (space + amount) % 10
        score += if (nextSpace == 0) 10 else nextSpace
        space = nextSpace
        return space
    }

    override fun equals(other: Any?): Boolean = other is Player && other.score == score && other.space == space
}

fun solveB(first: Player, second: Player): Long {

    val mapResult = mutableMapOf(Pair(first, second) to 1L)
    var firstPlayerWon = 0L
    var secondPlayerWon = 0L
    var firstPlayerTurn = false

    while (mapResult.isNotEmpty()) {
        val input = mapResult.toMutableMap()
        firstPlayerTurn = !firstPlayerTurn

        mapResult.clear()
        input.forEach {

            val firstPlayerCombinations = mapOf(
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(3) } to 1,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(4) } to 3,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(5) } to 6,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(6) } to 7,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(7) } to 6,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(8) } to 3,
                Player(it.key.first.space, it.key.first.score).also { p -> if (firstPlayerTurn) p.move(9) } to 1,
            )
            val secondPlayerCombinations = mapOf(
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(3) } to 1,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(4) } to 3,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(5) } to 6,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(6) } to 7,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(7) } to 6,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(8) } to 3,
                Player(it.key.second.space, it.key.second.score).also { p -> if (!firstPlayerTurn) p.move(9) } to 1,
            )

            firstPlayerCombinations.forEach { p1 ->
                secondPlayerCombinations.forEach { p2 ->
                    val count = 1 * it.value * p1.value * p2.value
                    mapResult[Pair(p1.key, p2.key)] = mapResult.getOrDefault(Pair(p1.key, p2.key), 0) + count
                }
            }
        }

        mapResult.filter { it.key.first.score >= 21 }.forEach {
            mapResult.remove(it.key)
            firstPlayerWon += it.value
        }
        mapResult.filter { it.key.second.score >= 21 }.forEach {
            mapResult.remove(it.key)
            secondPlayerWon += it.value
        }
    }

    return maxOf(firstPlayerWon, secondPlayerWon)
}