package com.growse.adventofcode

fun main() {
    println(Day4().countValueCombinationsInRange(168630, 718098))
}

class Day4 {
    fun numberHasAdjacentDigits(input: Int): Boolean {
        return input
            .toString()
            .padStart(6,'0')
            .toCharArray()
            .toList()
            .zipWithNext()
            .any { it.first == it.second }
    }

    fun numberHasAlwaysIncreasingDigits(input: Int): Boolean {
        return input
            .toString()
            .padStart(6,'0')
            .toCharArray()
            .toList()
            .zipWithNext()
            .all { it.first.toInt() <= it.second.toInt() }
    }

    fun countValueCombinationsInRange(from: Int, to: Int): Int {
        return IntRange(from, to)
            .filter { numberHasAdjacentDigits(it) }
            .filter { numberHasAlwaysIncreasingDigits(it) }
            .count()
    }
}