package day12

data class Cave(val name: String, var connections: List<String>)


val caves = mutableMapOf<String, Cave>()
val solution = mutableListOf<String>()

fun solveA(input: List<String>): Int {

    val connections = mutableMapOf<String, List<String>>()

    input.forEach { inputLine ->
        val connection = inputLine.split("-")
        val connectionForwardList = connections.getOrDefault(connection[0], emptyList() ).toMutableList().also { it.add(connection[1]) }
        connections[connection[0]] = connectionForwardList

        val connectionBackwardList = connections.getOrDefault(connection[1], emptyList() ).toMutableList().also { it.add(connection[0]) }
        connections[connection[1]] = connectionBackwardList
    }

    connections.forEach {
        caves[it.key] = (Cave(it.key, it.value))
    }

    findPathToEndFrom("start", "New path:", mutableListOf())

    return solution.size
}

fun findPathToEndFrom(start: String, acc: String, visited: MutableList<String>)  {
    if(start == "end") {
        solution.add("$acc,end")
        return
    }
    val cave = caves[start] ?: return

    if(cave.name[0].isLowerCase()) {
        visited.add(start)
    }

    val availableConnections = cave.connections.subtract(visited.toSet())

    if(availableConnections.isEmpty()) return

    cave.connections.forEach {
        if(!visited.contains(it)) {
            findPathToEndFrom(it,"$acc,$start", visited.toMutableList())
        }
    }

}


fun findPathToEndFromWithDouble(start: String, acc: String, visited: MutableList<String>, smallCaveDouble: String)  {
    if(start == "end") {
        solution.add("$acc,end")
        return
    }
    val cave = caves[start] ?: return

    var caveException = false

    if(cave.name[0].isLowerCase()) {
        if(smallCaveDouble != "start" && smallCaveDouble != "end" && cave.name == smallCaveDouble) {
            caveException = true
        } else {
            visited.add(start)
        }
    }

    val availableConnections = cave.connections.subtract(visited.toSet())

    if(availableConnections.isEmpty()) return

    cave.connections.forEach {
        if(!visited.contains(it)) {
            if(caveException) {
                findPathToEndFromWithDouble(it,"$acc,$start", visited.toMutableList(), "")
            } else {
                findPathToEndFromWithDouble(it,"$acc,$start", visited.toMutableList(), smallCaveDouble)
            }
        }
    }

}

fun solveB(input: List<String>): Int {
    val connections = mutableMapOf<String, List<String>>()

    input.forEach { inputLine ->
        val connection = inputLine.split("-")
        val connectionForwardList = connections.getOrDefault(connection[0], emptyList() ).toMutableList().also { it.add(connection[1]) }
        connections[connection[0]] = connectionForwardList

        val connectionBackwardList = connections.getOrDefault(connection[1], emptyList() ).toMutableList().also { it.add(connection[0]) }
        connections[connection[1]] = connectionBackwardList
    }

    connections.forEach {
        caves[it.key] = (Cave(it.key, it.value))
    }

    val smallCaves = caves.map{it.value}.filter{it.name[0].isLowerCase()}

    smallCaves.forEach {
        findPathToEndFromWithDouble("start", "New path:", mutableListOf(), it.name)
    }

    return solution.toSet().size
}
