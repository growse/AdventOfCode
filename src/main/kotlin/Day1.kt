package com.growse.adventofcode

import kotlin.math.floor

fun main() {
    println(Day1().sumFuelRequiredForMasses())
    println(Day1().sumFuelRequiredForMassesAddingFuelForTheFuelToo())
}

class Day1 {
    fun sumFuelRequiredForMasses(): Int {
        return this::class
            .java
            .getResourceAsStream("/day1.input.txt")
            .bufferedReader()
            .lines()
            .map { line -> line.toInt() }
            .map { mass -> calculateFuelRequired(mass) }
            .reduce { current, value -> current + value }
            .orElseThrow()
    }

    fun sumFuelRequiredForMassesAddingFuelForTheFuelToo(): Int {
        return this::class
            .java
            .getResourceAsStream("/day1.input.txt")
            .bufferedReader()
            .lines()
            .map { line -> line.toInt() }
            .map { mass -> calculateFuelRequiredConsideringFuelAsWell(mass) }
            .reduce { current, value -> current + value }
            .orElseThrow()
    }

    fun calculateFuelRequired(inputMass: Int): Int {
        return maxOf((floor((inputMass / 3).toDouble())).toInt() - 2, 0)
    }

    fun calculateFuelRequiredConsideringFuelAsWell(inputMass: Int): Int {
        var totalFuel = 0
        var lastMass = inputMass
        while (lastMass > 0) {
            lastMass = calculateFuelRequired(lastMass)
            totalFuel += lastMass
        }
        return totalFuel
    }
}
