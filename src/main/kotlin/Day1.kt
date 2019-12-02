package com.growse.adventofcode

import kotlin.math.floor

fun main(args: Array<String>) {
    print(Day1().sumFuelRequiredForMasses())
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

    fun calculateFuelRequired(inputMass: Int): Int {
        return (floor((inputMass / 3).toDouble())).toInt() - 2
    }
}
