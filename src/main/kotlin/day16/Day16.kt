package day16

fun solveA(input: List<String>): Int {
    val encodedInput = input.first().map { it.hexToBinary() }.joinToString("")
    val packets = decodePackets(encodedInput)
    return packets.sumOf { it.getVersion() }
}

fun solveB(input: List<String>): Long {
    println("TEST ${input.first()}")
    val encodedInput = input.first().map { it.hexToBinary() }.joinToString("")
    val packets = decodePackets(encodedInput)
    return packets.first().getResult()
}

fun Char.hexToBinary(): String = when (this) {
    '0' -> "0000"
    '1' -> "0001"
    '2' -> "0010"
    '3' -> "0011"
    '4' -> "0100"
    '5' -> "0101"
    '6' -> "0110"
    '7' -> "0111"
    '8' -> "1000"
    '9' -> "1001"
    'A' -> "1010"
    'B' -> "1011"
    'C' -> "1100"
    'D' -> "1101"
    'E' -> "1110"
    'F' -> "1111"
    else -> ""
}

fun decodePackets(input: String, count: Int? = null): List<Packet> {

    var encodedPackets = input
    val packets = mutableListOf<Packet>()

    while (encodedPackets.length > 7) {
        if (count != null) {
            if (packets.size == count) return packets
        }
        val typeId = encodedPackets.drop(3).take(3).toInt(2)
        if (typeId == 4) {
            val packetLength = lengthOfLiteralPacket(encodedPackets)
            packets.add(LiteralValuePacket(encodedPackets.take(packetLength)))
            encodedPackets = encodedPackets.drop(packetLength)
        } else {
            val lengthTypeId = encodedPackets[6].digitToInt()
            val packetVersion = encodedPackets.take(3).toInt(2)

            if (lengthTypeId == 0) {
                val packetLength = encodedPackets.drop(7).take(15).toInt(2)
                val subPackets = decodePackets(encodedPackets.drop(22).take(packetLength))
                val bitLength = 22 + packetLength

                packets.add(OperatorPacket(packetVersion, typeId, bitLength, subPackets))
                encodedPackets = encodedPackets.drop(bitLength)
            } else {
                val subPacketCount = encodedPackets.drop(7).take(11).toInt(2)
                val subPackets = decodePackets(encodedPackets.drop(18), subPacketCount).take(subPacketCount)
                val bitLength = 18 + subPackets.sumOf { it.bitLength }

                packets.add(OperatorPacket(packetVersion, typeId, bitLength, subPackets))
                encodedPackets = encodedPackets.drop(bitLength)
            }
        }
    }

    return packets
}

fun lengthOfLiteralPacket(input: String): Int {
    var toDrop = 0
    val literalInput = input.drop(6)

    var i = 0
    while (i in literalInput.indices) {
        toDrop += 5
        if (literalInput[i] == '0') {
            val nextChunk = listOfNotNull(
                literalInput.getOrNull(i + 5),
                literalInput.getOrNull(i + 6),
                literalInput.getOrNull(i + 7),
                literalInput.getOrNull(i + 8),
                literalInput.getOrNull(i + 9)
            )
            if (nextChunk.size < 5 && nextChunk.all { it == '0' }) toDrop += nextChunk.size
            break
        }
        i += 5
    }
    return 6 + toDrop
}

interface Packet {
    fun getVersion(): Int
    val bitLength: Int
    fun getResult(): Long
}

class OperatorPacket(packetVersion: Int, typeId: Int, bitLength: Int, subPackets: List<Packet>) : Packet {
    private val packetVersion: Int
    private val typeId: Int
    private val subPackets: List<Packet>
    override val bitLength: Int


    init {
        this.packetVersion = packetVersion
        this.typeId = typeId
        this.bitLength = bitLength
        this.subPackets = subPackets
    }

    override fun getResult(): Long {
        return when(typeId) {
            0 -> subPackets.sumOf { it.getResult() }
            1 -> subPackets.map{it.getResult()}.reduce { acc, result -> acc * result }
            2 -> subPackets.minOf{it.getResult()}
            3 -> subPackets.maxOf{it.getResult()}
            5 -> {
                val first = subPackets[0].getResult()
                val second = subPackets[1].getResult()
                if(first > second) 1 else 0
            }
            6 -> {
                val first = subPackets[0].getResult()
                val second = subPackets[1].getResult()
                if(first < second) 1 else 0
            }
            7 -> {
                val first = subPackets[0].getResult()
                val second = subPackets[1].getResult()
                if(first == second) 1 else 0
            }
            else -> -1
        }
    }

    override fun getVersion() = packetVersion + subPackets.sumOf { it.getVersion() }

    override fun toString(): String {
        return "PacketVersion: $packetVersion TypeId: $typeId and ${subPackets.count()} subpackets: $subPackets"
    }
}

class LiteralValuePacket(input: String) : Packet {
    private val packetVersion: Int
    private val typeId: Int
    private val literalValue: Long
    override val bitLength: Int

    init {
        packetVersion = input.take(3).toInt(2)
        typeId = input.drop(3).take(3).toInt(2)
//        println("Creating LVP with $input, version: $packetVersion and typeId: $typeId")
        literalValue = parse(input.drop(6))
        bitLength = input.length

    }

    private fun parse(parseInput: String): Long {

        var result = ""
        for (chunk in parseInput.chunked(5)) {
            result += chunk.drop(1)

            if (chunk.first() == '0') break
        }
        return result.toLong(2)
    }

    override fun getResult() = literalValue

    override fun getVersion(): Int = packetVersion

    override fun toString(): String {
        return "PacketVersion: $packetVersion TypeId: $typeId Literal value: $literalValue"
    }
}