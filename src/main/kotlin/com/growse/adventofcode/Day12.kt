package com.growse.adventofcode

import kotlin.math.abs

fun main() {
    val planets = setOf(
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
    fun stepPlanets(planets: Set<Planet>): Set<Planet> =
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
            .toSet()


    fun applyVelocity(planet: Planet): Planet =
        Planet(planet.position + planet.velocity, planet.velocity)
}