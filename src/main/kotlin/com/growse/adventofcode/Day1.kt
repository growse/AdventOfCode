package com.growse.adventofcode

import kotlin.math.floor

fun main() {
    println(Day1().sumFuelRequiredForMasses())
    println(Day1().sumFuelRequiredForMassesAddingFuelForTheFuelToo())
}

typealias FuelFromMassCalculator = (Int) -> Int

class Day1 {
    fun sumFuelRequiredForMasses(): Int {
        return sumFuelRequiredForMassSomehow(this::calculateFuelRequired)
    }

    fun sumFuelRequiredForMassesAddingFuelForTheFuelToo(): Int {
        return sumFuelRequiredForMassSomehow(this::calculateFuelRequiredConsideringFuelAsWell)
    }

    private fun sumFuelRequiredForMassSomehow(massToFuelCalculator: FuelFromMassCalculator): Int {
        return this::class
            .java
            .getResourceAsStream("/day1.input.txt")
            .bufferedReader()
            .lines()
            .map { line -> line.toInt() }
            .map { mass -> massToFuelCalculator(mass) }
            .reduce { current, value -> current + value }
            .orElseThrow()
    }

    fun calculateFuelRequired(inputMass: Int): Int {
        return maxOf((floor((inputMass / 3).toDouble())).toInt() - 2, 0)
    }

    fun calculateFuelRequiredConsideringFuelAsWell(inputMass: Int): Int {
        return if (calculateFuelRequired(inputMass) == 0) {
            0
        } else {
            calculateFuelRequired(inputMass) + calculateFuelRequiredConsideringFuelAsWell(
                calculateFuelRequired(
                    inputMass
                )
            )
        }
    }
}