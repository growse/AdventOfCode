package com.growse.adventofcode.com.growse.adventofcode

import com.growse.adventofcode.IntCodeComputer
import com.growse.adventofcode.WaitForInputInterrupt

fun main() {
    println(Day7().findMaxThrust(IntCodeComputer(emptyList()).getInputProgram("/day7.input.txt")))
    println(Day7().findMaxThrustWithFeedback(IntCodeComputer(emptyList()).getInputProgram("/day7.input.txt")))
}

class Day7 {
    fun allCombinations(input: List<Int>): List<List<Int>> {
        return if (input.size == 1) {
            listOf(input)
        } else {
            input
                .mapIndexed { index, it ->
                    Pair(it, allCombinations(input.filterIndexed { subIndex, _ -> subIndex != index }))
                }
                .flatMap { pair -> pair.second.map { listOf(pair.first) + it } }
        }

    }

    fun findMaxThrust(program: List<Int>): Int =
        allCombinations(listOf(0, 1, 2, 3, 4)).map {
            calculateThrustForPhases(program, it)
        }.max()!!


    fun calculateThrustForPhases(program: List<Int>, phases: List<Int>): Int {
        return phases.fold(0, { thrust: Int, phase: Int -> ampOutput(program, phase, thrust) })
    }

    private fun ampOutput(program: List<Int>, phase: Int, thrust: Int): Int {
        val intCodeComputer = IntCodeComputer(listOf(phase, thrust))
        intCodeComputer.executeProgram(program)
        return intCodeComputer.outputs().last()
    }

    fun findMaxThrustWithFeedback(program: List<Int>): Int =
        allCombinations(listOf(5, 6, 7, 8, 9)).map {
            calculateThrustForPhasesWithFeedback(program, it)
        }.max()!!


    fun calculateThrustForPhasesWithFeedback(program: List<Int>, phases: List<Int>): Int {
        val computers = phases.map { IntCodeComputer(listOf(it)) }
        computers.forEach { it.load(program) }
        var computerIndex = 0
        var currentThrustValue = 0
        while (true) {
            val computer = computers[computerIndex % phases.size]
            computer.addInput(currentThrustValue)
            try {
                computer.resume()
                currentThrustValue = computer.outputs().last()
                if ((computerIndex + 1) % phases.size == 0) {
                    return currentThrustValue
                }
            } catch (e: WaitForInputInterrupt) {
                currentThrustValue = computer.outputs().last()
            }
            computerIndex += 1
        }
    }
}
