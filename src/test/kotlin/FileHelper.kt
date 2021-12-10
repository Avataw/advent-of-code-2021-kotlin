import java.io.File

fun readFileAsLinesUsingUseLines(day: Int): List<String>
        = File("src/test/resources/Day${day}/input.txt").useLines { it.toList() }

fun readTestFileAsLinesUsingUseLines(day: Int): List<String>
        = File("src/test/resources/Day${day}/test.txt").useLines { it.toList() }