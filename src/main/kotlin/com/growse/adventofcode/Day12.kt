package com.growse.adventofcode

import kotlin.math.abs

fun main() {
    val planets = listOf(
        Planet(Position(7, 10, 17)),
        Planet(Position(-2, 7, 0)),
        Planet(Position(12, 5, 12)),
        Planet(Position(5, -8, 6))
    )
    println(
        (0 until 1000)
            .fold(planets, { acc, _ -> Day12().stepPlanets(acc) })
            .map { it.energy() }
            .sum()
    )

    println(Day12().statesUntilRepeated(planets))
}

data class Position(val x: Int, val y: Int, val z: Int) {
    operator fun plus(velocity: Velocity): Position = Position(x + velocity.x, y + velocity.y, z + velocity.z)
}

data class Velocity(val x: Int, val y: Int, val z: Int) {
    operator fun plus(velocity: Velocity): Velocity = Velocity(x + velocity.x, y + velocity.y, z + velocity.z)
}

data class Planet(
    val position: Position,
    val velocity: Velocity = Velocity(0, 0, 0)
) {
    fun energy(): Int =
        (abs(position.x) + abs(position.y) + abs(position.z)) * (abs(velocity.x) + abs(velocity.y) + abs(velocity.z))
}

class Day12 {
    fun stepPlanets(planets: List<Planet>): List<Planet> =
        planets
            .flatMap { p -> planets.filter { it != p }.map { p to it } }
            .asSequence()
            .map {
                Planet(
                    it.first.position,
                    Velocity(
                        when {
                            it.second.position.x > it.first.position.x -> 1
                            it.second.position.x < it.first.position.x -> -1
                            else -> 0
                        },
                        when {
                            it.second.position.y > it.first.position.y -> 1
                            it.second.position.y < it.first.position.y -> -1
                            else -> 0
                        },
                        when {
                            it.second.position.z > it.first.position.z -> 1
                            it.second.position.z < it.first.position.z -> -1
                            else -> 0
                        }
                    )
                )
            }

            .groupBy { it.position }
            .map {
                Planet(
                    it.key,
                    planets.first { originalPlanet -> originalPlanet.position == it.key }.velocity + it.value.fold(
                        Velocity(
                            0,
                            0,
                            0
                        ), { acc, planet -> planet.velocity + acc })
                )
            }
            .map {
                applyVelocity(it)
            }


    fun statesUntilRepeated(planets: List<Planet>): Long {
        val xseen = mutableSetOf<List<Pair<Int, Int>>>()
        val yseen = mutableSetOf<List<Pair<Int, Int>>>()
        val zseen = mutableSetOf<List<Pair<Int, Int>>>()
        var currentPlanets = planets
        var counter = 0L
        var xCounter = 0L
        var yCounter = 0L
        var zCounter = 0L
        while (true) {
            currentPlanets = stepPlanets(currentPlanets)
            if (xCounter == 0L && !xseen.add(currentPlanets.map { it.position.x to it.velocity.x })) {
                xCounter = counter
                println("X: $xCounter")
            }
            if (yCounter == 0L && !yseen.add(currentPlanets.map { it.position.y to it.velocity.y })) {
                yCounter = counter
                println("Y: $yCounter")
            }
            if (zCounter == 0L && !zseen.add(currentPlanets.map { it.position.z to it.velocity.z })) {
                zCounter = counter
                println("Z: $zCounter")
            }
            if (xCounter != 0L && yCounter != 0L && zCounter != 0L) {
                break
            }
            counter++
        }
        return listOf(xCounter, yCounter, zCounter).reduce { acc, i -> if (acc == 0L) i else lcm(acc, i) }
    }

    private fun lcm(i1: Long, i2: Long): Long = (i1 * i2) / gcd(i1, i2)

    private fun gcd(i1: Long, i2: Long): Long {
        return if (i2 != 0L) gcd(i2, i1 % i2) else i1
    }

    fun applyVelocity(planet: Planet): Planet =
        Planet(planet.position + planet.velocity, planet.velocity)
}