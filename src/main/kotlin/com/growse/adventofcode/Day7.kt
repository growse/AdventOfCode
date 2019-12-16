package com.growse.adventofcode

fun main() {
    println(
        Day7().findMaxThrust(
            IntCodeComputer(
                emptyList()
            ).getInputProgram("/day7.input.txt")
        )
    )
    println(
        Day7().findMaxThrustWithFeedback(
            IntCodeComputer(emptyList()).getInputProgram("/day7.input.txt")
        )
    )
}

class Day7 {
    fun allCombinations(input: List<Number>): List<List<Number>> {
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

    fun findMaxThrust(program: List<Number>): Number =
        allCombinations(listOf(0, 1, 2, 3, 4)).map {
            calculateThrustForPhases(program, it)
        }.maxBy { it.toLong() }!!


    fun calculateThrustForPhases(program: List<Number>, phases: List<Number>): Number =
        phases.fold(0L, { thrust: Number, phase: Number -> ampOutput(program, phase, thrust) })


    private fun ampOutput(program: List<Number>, phase: Number, thrust: Number): Number {
        val intCodeComputer =
            IntCodeComputer(listOf(phase, thrust))
        intCodeComputer.executeProgram(program)
        return intCodeComputer.outputs().last()
    }

    fun findMaxThrustWithFeedback(program: List<Number>): Number =
        allCombinations(listOf(5, 6, 7, 8, 9)).map {
            calculateThrustForPhasesWithFeedback(program, it)
        }.maxBy { it.toLong() }!!


    fun calculateThrustForPhasesWithFeedback(program: List<Number>, phases: List<Number>): Number {
        val computers = phases.map {
            IntCodeComputer(
                listOf(it)
            )
        }
        computers.forEach { it.loadIntoMemory(program) }
        var computerIndex = 0
        var currentThrustValue: Number = 0
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
