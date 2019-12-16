package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay9 {
    @Test
    fun selfOutputtingProgram() {
        val inputProgram = listOf(
            109, 1, 204, -1, 1001, 100, 1, 100, 1008,
            100, 16, 101, 1006, 101, 0, 99
        )
        val intCodeComputer = IntCodeComputer()
        intCodeComputer.executeProgram(inputProgram)
        assertEquals(inputProgram, intCodeComputer.outputs().map { it.toInt() })
    }

    @Test
    fun largeNumberOutput() {
        val inputProgram = listOf(1102, 34915192, 34915192, 7, 4, 7, 99, 0)
        val intCodeComputer = IntCodeComputer()
        intCodeComputer.executeProgram(inputProgram)
        val result = intCodeComputer.outputs().first()
        assertEquals(1219070632396864, result.toLong())
        assertEquals(16, result.toString().length)
    }

    @Test
    fun largeNumberInput() {
        val inputProgram = listOf(104,1125899906842624,99)
        val intCodeComputer = IntCodeComputer()
        intCodeComputer.executeProgram(inputProgram)
        val result = intCodeComputer.outputs().first()
        assertEquals(1125899906842624, result.toLong())
    }
}