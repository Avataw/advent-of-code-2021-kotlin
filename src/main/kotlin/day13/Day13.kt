package day13

import kotlin.math.abs

data class Point(val columnPosition: Int, val rowPosition: Int) {

    fun mirrorVertically(fold: Int): Point {
        if (rowPosition <= fold) return this

        val difference = abs(rowPosition - fold)
        return Point(columnPosition, fold - difference)
    }

    fun mirrorHorizontal(fold: Int): Point {
        if (columnPosition <= fold) return this

        val difference = abs(columnPosition - fold)
        return Point(fold - difference, rowPosition)
    }
}

fun solveA(input: List<String>): Int {

    val numbersAndFolds = input.joinToString("\n").split("\n\n")

    var points = numbersAndFolds[0].split("\n").map { line ->
        val position = line.split(",").map { it.toInt() }
        Point(position[0], position[1])
    }

    val fold = numbersAndFolds[1].split("\n").first()

    val foldInstruction = fold.split(" ").last().split("=")

    points = if (foldInstruction[0] == "y") {
        points.map { point -> point.mirrorVertically(foldInstruction[1].toInt()) }
    } else {
        points.map { point -> point.mirrorHorizontal(foldInstruction[1].toInt()) }
    }

    return points.toSet().size
}

fun printPoints(points: List<Point>) {
    for (y in 0..points.maxOf { it.rowPosition }) {
        var line = ""

        for (x in 0..points.maxOf { it.columnPosition }) {
            val point = points.find { it.rowPosition == y && it.columnPosition == x }
            line += if (point != null) "#" else "."
        }
        println(line)
    }

    println("")
}

fun solveB(input: List<String>): String {
    val numbersAndFolds = input.joinToString("\n").split("\n\n")

    var points = numbersAndFolds[0].split("\n").map { line ->
        val position = line.split(",").map { it.toInt() }
        Point(position[0], position[1])
    }

    numbersAndFolds[1].split("\n").forEach{ fold ->
        val foldInstruction = fold.split(" ").last().split("=")

        points = if (foldInstruction[0] == "y") {
            points.map { point -> point.mirrorVertically(foldInstruction[1].toInt()) }
        } else {
            points.map { point -> point.mirrorHorizontal(foldInstruction[1].toInt()) }
        }
    }

    //should show JZGUAPRB
    printPoints(points);

    return "JZGUAPRB"
}
