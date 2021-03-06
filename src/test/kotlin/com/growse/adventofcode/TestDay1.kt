package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


class TestDay1 {
    @ParameterizedTest(name = "For mass {0}, fuel is {1}")
    @CsvSource("12,2", "14,2", "1969,654", "100756,33583")
    fun testFuelIsCorrectForGivenMass(inputMass: Int, expectedFuel: Int) {
        val fuelRequired = Day1().calculateFuelRequired(inputMass)
        assertEquals(expectedFuel, fuelRequired)
    }

    @ParameterizedTest(name = "For mass {0}, fuel is {1}")
    @CsvSource("12,2", "1969,966", "100756,50346")
    fun testFuelIsCorrectForGivenMassAndFuel(inputMass: Int, expectedFuel: Int) {
        val fuelRequired = Day1().calculateFuelRequiredConsideringFuelAsWell(inputMass)
        assertEquals(expectedFuel, fuelRequired)
    }
}