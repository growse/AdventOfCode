package com.growse.adventofcode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class TestDay2 {
    @ParameterizedTest()
    @CsvSource(
        "1,0,0,0,99;2,0,0,0,99",
        "2,3,0,3,99;2,3,0,6,99",
        "2,4,4,5,99,0;2,4,4,5,99,9801",
        "1,1,1,4,99,5,6,0,99;30,1,1,4,2,5,6,0,99",
        delimiter = ';'
    )
    fun testIntCodeComputerWorks(inputProgram: String, expectedOutputProgram: String) {

        val intCodeComputerOutput = Day2()
            .executeProgram(
                inputProgram.split(",").map { a -> a.toInt() }
            )
        Assertions.assertEquals(
            expectedOutputProgram,
            intCodeComputerOutput.joinToString(",")
        )
    }

    @Test
    fun testInputFinderWorks() {
        assertEquals(Pair(12, 2), Day2().findInputsThatProduce(3895705))
    }
}