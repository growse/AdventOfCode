package com.growse.adventofcode

fun main() {
    val testBoostIntCodeComputer = IntCodeComputer(listOf(1))
    testBoostIntCodeComputer.executeNamedResourceProgram("/day9.input.txt")
    println(testBoostIntCodeComputer.outputs().joinToString(","))
    val intCodeComputer = IntCodeComputer(listOf(2))
    intCodeComputer.executeNamedResourceProgram("/day9.input.txt")
    println(intCodeComputer.outputs().joinToString(","))
}