package com.growse.adventofcode

fun main() {
    val arcadeCabinet = ArcadeCabinet()
    arcadeCabinet.loadFromResource("/day13.input.txt")
    println(arcadeCabinet.getOutputs.chunked(3).count { it[2].toInt() == 2 })
}

class ArcadeCabinet {
    private val intCodeComputer = IntCodeComputer()
    fun loadFromResource(resourceName: String) {
        intCodeComputer.loadIntoMemory(intCodeComputer.getInputProgram(resourceName))
        intCodeComputer.resume()
    }

    val getOutputs: List<Number> = intCodeComputer.outputs()
}