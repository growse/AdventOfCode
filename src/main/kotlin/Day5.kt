package com.growse.adventofcode

fun main() {
    Day5().executeProgram()
}

class Day5 {
    fun executeProgram(): List<Int> {
        return IntCodeComputer().executeNamedResourceProgram("/day5.input.txt")
    }
}