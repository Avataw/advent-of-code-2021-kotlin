package day10

import java.util.*

fun solveA(input: List<String>): Int {
    val result = input.map { checkLineForCorruption(it) }
    return result.sum()
}

fun checkLineForCorruption(input: String): Int {
    val stack = Stack<Char>()

    input.forEach {
        if (isOpen(it)) {
            stack.push(it)
        } else {
            val current = stack.pop()
            if (!match(current, it)) {
                return getPointForChar(it)
            }
        }
    }
    return 0
}


fun isOpen(symbol: Char): Boolean = symbol == '(' || symbol == '[' || symbol == '{' || symbol == '<'
fun match(open: Char, close: Char): Boolean {
    if (open == '(' && close == ')') return true
    if (open == '[' && close == ']') return true
    if (open == '{' && close == '}') return true
    if (open == '<' && close == '>') return true

    return false
}

fun getPointForChar(close: Char): Int {
    if (close == ')') return 3
    if (close == ']') return 57
    if (close == '}') return 1197
    if (close == '>') return 25137
    return 0
}

fun getPointForClosing(close: Char): Long {
    if (close == ')') return 1
    if (close == ']') return 2
    if (close == '}') return 3
    if (close == '>') return 4
    return 0
}

fun getClose(open: Char): Char {
    if (open == '(') return ')'
    if (open == '[') return ']'
    if (open == '{') return '}'
    if (open == '<') return '>'
    return '?'
}

fun completeUncorruptedLine(input: String): Long {
    val stack = Stack<Char>()

    input.forEach {
        if (isOpen(it)) {
            stack.push(it)
        } else {
            val current = stack.peek()
            if (match(current, it)) stack.pop()
        }
    }

    return stack
        .reversed()
        .map {
            val closingSymbol = getClose(it)
            getPointForClosing(closingSymbol)
        }.reduce { acc, i -> acc * 5 + i }
}

fun solveB(input: List<String>): Long {
    val result = input
        .filter { checkLineForCorruption(it) == 0 }
        .map { completeUncorruptedLine(it) }

    return result.sorted()[result.size / 2]
}
