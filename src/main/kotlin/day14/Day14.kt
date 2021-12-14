package day14

fun solveA(input: List<String>): Int {

    val insertionRules = input.drop(2).associate {
        val startAndTarget = it.split(" -> ")

        startAndTarget[0] to startAndTarget[1]
    }

    var polymerTemplate = input[0]

    for (i in 1..10) {

        val zipped = polymerTemplate.zipWithNext()

        polymerTemplate = zipped
            .mapIndexed { index, it ->
                val pair = it.first + "" + it.second

                val insertion = insertionRules.getOrDefault(pair, "")

                if (index == zipped.size - 1) {
                    it.first + insertion + it.second
                } else {
                    it.first + insertion
                }
            }
            .joinToString("")
    }

    val countedElements = polymerTemplate.groupBy { it }.map { it.key to it.value.count() }

    println(countedElements)

    val mostCommon = countedElements.maxByOrNull { it.second }?.second ?: 0
    val leastCommon = countedElements.minByOrNull { it.second }?.second ?: 0

    return mostCommon - leastCommon
}

fun solveB(input: List<String>): Long {

    val insertionRules = input
        .drop(2)
        .associate {
            val pairAndInsertion = it.split(" -> ")
            pairAndInsertion[0] to pairAndInsertion[1]
        }

    val counts = input[0]
        .toSet()
        .associateWith { input[0].filter { i -> i == it }.sumOf{1L} }
        .toMutableMap()


    val zipped = input[0].zipWithNext()

    var rules = zipped
        .associate { (it.first + "" + it.second) to zipped.filter { i -> i == it }.sumOf { 1L } }
        .toMap()

    for (i in 1..40) {
        val nextRules = mutableMapOf<String, Long>()

        rules.forEach { rule ->
            val insertion = insertionRules[rule.key]!!

            counts[insertion[0]] = counts.getOrDefault(insertion[0], 0L) + rule.value
            getNextInsertions(rule.key, insertion).forEach {
                nextRules[it] = nextRules.getOrDefault(it, 0L) + rule.value
            }
        }
        rules = nextRules
    }

    return counts.maxOf { it.value } - counts.minOf { it.value }
}

fun getNextInsertions(pair: String, insertion: String) = listOf(pair[0] + insertion, insertion + pair[1])