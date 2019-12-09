package com.growse.adventofcode

fun main() {
    println(Day4().countValueCombinationsInRange(168630, 718098))
    println(Day4().countValueCombinationsInRangeWithAtLeastOnePairRestriction(168630, 718098))
}

class Day4 {
    fun numberHasAdjacentDigits(input: Int): Boolean {
        return input
            .toString()
            .padStart(6, '0')
            .toCharArray()
            .toList()
            .zipWithNext()
            .any { it.first == it.second }
    }

    fun numberHasAlwaysIncreasingDigits(input: Int): Boolean {
        return input
            .toString()
            .padStart(6, '0')
            .toCharArray()
            .toList()
            .zipWithNext()
            .all { it.first.toInt() <= it.second.toInt() }
    }

    fun numberHasAtLeastOnePairOfAdjacentDigits(input: Int): Boolean {
        val padded = input
            .toString()
            .padStart(6, '0')
            .toCharArray()
            .toList()
        return padded
            .windowed(4)
            .any { it[1] == it[2] && it[1] != it[0] && it[2] != it[3] }
                || (padded[0] == padded[1] && padded[1] != padded[2])
                || (padded.asReversed()[0] == padded.asReversed()[1] && padded.asReversed()[1] != padded.asReversed()[2])
    }


    fun countValueCombinationsInRange(from: Int, to: Int): Int {
        return IntRange(from, to)
            .filter { numberHasAdjacentDigits(it) }
            .filter { numberHasAlwaysIncreasingDigits(it) }
            .count()
    }

    fun countValueCombinationsInRangeWithAtLeastOnePairRestriction(from: Int, to: Int): Int {
        return IntRange(from, to)
            .filter { numberHasAdjacentDigits(it) }
            .filter { numberHasAlwaysIncreasingDigits(it) }
            .filter { numberHasAtLeastOnePairOfAdjacentDigits(it) }
            .count()
    }
}