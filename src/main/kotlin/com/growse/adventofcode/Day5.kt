package com.growse.adventofcode

fun main() {
    Day5().executeProgram(1).forEach { println(it) }
    Day5().executeProgram(5).forEach { println(it) }
}

class Day5 {
    fun executeProgram(input: Number): List<Number> {
        val intCodeComputer =
            IntCodeComputer(listOf(input))
        intCodeComputer.executeNamedResourceProgram("/day5.input.txt")
        return intCodeComputer.outputs()
    }

}