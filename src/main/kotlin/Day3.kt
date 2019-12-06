package com.growse.adventofcode

import kotlin.math.absoluteValue

fun main() {
    println(Day3().getMinDistanceFromInput())
    println(Day3().getMinPathDistanceFromInput())
}

data class Coordinate(val x: Int, val y: Int)

class Day3 {
    fun getMinDistanceFromInput(): String {
        val inputs = getProgramInput()
        return findClosestIntersectionToOrigin(inputs[0], inputs[1]).toString()
    }

    fun getMinPathDistanceFromInput(): String {
        val inputs = getProgramInput()
        return findClosestIntersectionToOriginByPathLength(inputs[0], inputs[1]).toString()
    }

    private fun getProgramInput(): List<String> {
        return this::class
            .java
            .getResourceAsStream("/day3.input.txt")
            .bufferedReader()
            .readLines()
    }

    private fun charPathToCoordinates(path: String): List<Coordinate> {
        var pos = Coordinate(0, 0)
        return path
            .split(",")
            .flatMap {
                it[0].toString().repeat(it.substring(1).toInt()).asIterable()
            }.map {
                val new = when (it) {
                    'R' -> Coordinate(pos.x + 1, pos.y)
                    'L' -> Coordinate(pos.x - 1, pos.y)
                    'U' -> Coordinate(pos.x, pos.y + 1)
                    'D' -> Coordinate(pos.x, pos.y - 1)
                    else -> pos
                }
                pos = new
                new
            }
    }

    fun findClosestIntersectionToOrigin(firstPath: String, secondPath: String): Int {
        return charPathToCoordinates(firstPath)
            .intersect(charPathToCoordinates(secondPath))
            .map { it.x.absoluteValue + it.y.absoluteValue }
            .min()!!
    }

    fun findClosestIntersectionToOriginByPathLength(firstPath: String, secondPath: String): Int {
        return charPathToCoordinates(firstPath)
            .intersect(charPathToCoordinates(secondPath))
            .map {
                charPathToCoordinates(firstPath).indexOf(it) + 1 + charPathToCoordinates(secondPath).indexOf(it) + 1
            }
            .min()!!
    }
}
