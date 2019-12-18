package com.growse.adventofcode

import kotlin.math.PI
import kotlin.math.atan2

fun main() {
    val monitoringStation = Day10().findBestMonitoringStationLocation(Day10().loadGridFromResource("/day10.input.txt"))
    println(monitoringStation)
    println(
        Day10().laserSweepAsteroidDestroyOrder(
            Day10().loadGridFromResource("/day10.input.txt"),
            monitoringStation.first
        )[199]
    )
}

class Day10 {
    fun stringToGrid(input: String): Grid {
        val inputLines = input.split("\n")
        val width = inputLines.first().trim().length
        val height = inputLines.size
        val asteroids = inputLines
            .mapIndexed { y, line ->
                line.mapIndexed { x, c -> if (c == '#') Coordinate(x, y) else null }
            }
            .flatten()
            .filterNotNull()
        return Grid(width, height, asteroids)
    }

    fun findBestMonitoringStationLocation(input: String): Pair<Coordinate, Int> {
        val grid = stringToGrid(input)
        return grid
            .asteroids
            .map { Pair(it, visibleAsteroids(it, grid.asteroids)) }
            .maxBy { it.second }!!
    }

    fun visibleAsteroids(baseAsteroid: Coordinate, allAsteroids: List<Coordinate>): Int {
        val shiftedAsteroids = allAsteroids.map { Coordinate(it.x - baseAsteroid.x, it.y - baseAsteroid.y) }
        val visibleShiftedAsteroids = shiftedAsteroids.filter { isAsteroidVisibleFromOrigin(it, shiftedAsteroids) }
        return visibleShiftedAsteroids.size
    }

    fun isAsteroidVisibleFromOrigin(asteroid: Coordinate, asteroids: List<Coordinate>): Boolean {
        if (asteroid == Coordinate(0, 0)) {
            return false
        }
        if (asteroid.x == 0) {
            return !asteroids
                .filter { it != Coordinate(0, 0) }
                .any {
                    it.x == 0 && IntRange(
                        minOf(0, asteroid.y),
                        maxOf(0, asteroid.y)
                    ).contains(it.y) && it != asteroid
                }
        }
        if (asteroid.y == 0) {
            return !asteroids
                .filter { it != Coordinate(0, 0) }
                .any {
                    it.y == 0 && IntRange(
                        minOf(0, asteroid.x),
                        maxOf(0, asteroid.x)
                    ).contains(it.x) && it != asteroid
                }
        }
        // Find all the whole fraction coordinates, and figure out if there's an asteroid there
        return !IntRange(
            minOf(0, asteroid.x),
            maxOf(0, asteroid.x)
        )
            .filter { (it * asteroid.y) % asteroid.x == 0 && it != 0 }
            .map { Coordinate(it, (it * asteroid.y) / asteroid.x) }
            .filter { it != asteroid }
            .any { asteroids.contains(it) }
    }

    fun loadGridFromResource(resourceName: String): String {
        return this::class.java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
    }

    fun laserSweepAsteroidDestroyOrder(inputGrid: String, laserBase: Coordinate): List<Coordinate> =
        stringToGrid(inputGrid)
            .asteroids
            .filter { it != laserBase }
            // calculate angle as clockwise from straight up, convert to degrees
            .groupBy {
                ((2 * PI) - (atan2(
                    it.x.toDouble() - laserBase.x,
                    it.y.toDouble() - laserBase.y
                ) + PI)) * (360 / (2 * PI))
            }
            .mapValues { it.value.sortedBy { coordinate -> coordinate.distanceFrom(laserBase) } }
            .flatMap {
                it.value.mapIndexed { index, coordinate ->
                    LaserImpactAngle(
                        coordinate,
                        it.key + (360 * index)
                    )
                }
            }
            .sortedBy { it.angle }
            .groupBy { it.angle }
            .values
            .map { it.first().coordinate }

    data class LaserImpactAngle(val coordinate: Coordinate, val angle: Double)

    data class Grid(val width: Int, val height: Int, val asteroids: List<Coordinate>)
}
