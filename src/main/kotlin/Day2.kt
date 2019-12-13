package com.growse.adventofcode


fun main() {
    println(Day2().runProgramFromResource())
    val day2combo = Day2().findInputsThatProduce(19690720)
    println(day2combo.second + (day2combo.first * 100))
}


class Day2 {

    fun runProgramFromResource(): String {
        return IntCodeComputer(listOf(4)).executeNamedResourceProgram("/day2.input.txt")
            .joinToString(",")
    }

    fun findInputsThatProduce(expected: Int): Pair<Int, Int> {
        val intCodeComputer = IntCodeComputer(listOf(4))
        val inputProgram = intCodeComputer.getInputProgram("/day2.input.txt")
        val range = IntRange(0, 99)
        return sequence {
            range.forEach { a ->
                range.forEach { b ->
                    yield(a to b)
                }
            }
        }.map {
            val prog = inputProgram.toMutableList()
            prog[1] = it.first
            prog[2] = it.second
            it to intCodeComputer.executeProgram(prog)[0]
        }.filter { it.second == expected }.first().first
    }
}