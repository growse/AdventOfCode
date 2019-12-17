package com.growse.adventofcode

fun main() {
    println(Day10().findBestMonitoringStationLocation(Day10().loadGridFromResource("/day10.input.txt")))
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

    fun findBestMonitoringStationLocation(input: String): Pair<Coordinate,Int> {
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


    data class Grid(val width: Int, val height: Int, val asteroids: List<Coordinate>)
}
