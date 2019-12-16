package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class TestDay5 {
    @Test
    fun intComputerRunsSimplePrintProgram() {
        val inputProgram = listOf(3, 0, 4, 0, 99)
        val intCodeComputer =
            IntCodeComputer(listOf(4))
        intCodeComputer.executeProgram(inputProgram)
        assertEquals(4, intCodeComputer.outputs().first().toInt())
    }

    @ParameterizedTest
    @CsvSource("7,999", "8,1000", "9,1001")
    fun intComputerCanDoJumps(input: String, expectedOutput: String) {
        val inputProgram = listOf(
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
            1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
            999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
        )
        val intCodeComputer =
            IntCodeComputer(listOf(input.toInt()))
        intCodeComputer.executeProgram(inputProgram)
        assertEquals(expectedOutput.toInt(), intCodeComputer.outputs().first().toInt())
    }

}