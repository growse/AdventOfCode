package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class TestDay12 {

    companion object {
        @JvmStatic
        fun planetsStepCases(): Stream<Arguments> =
            of(
                Arguments.of(
                    listOf(
                        Planet(Position(-1, 0, 2)),
                        Planet(Position(2, -10, -7)),
                        Planet(Position(4, -8, 8)),
                        Planet(Position(3, 5, -1))
                    ),
                    1,
                    listOf(
                        Planet(Position(2, -1, 1), Velocity(3, -1, -1)),
                        Planet(Position(3, -7, -4), Velocity(1, 3, 3)),
                        Planet(Position(1, -7, 5), Velocity(-3, 1, -3)),
                        Planet(Position(2, 2, 0), Velocity(-1, -3, 1))
                    ),
                    229
                ),
                Arguments.of(
                    listOf(
                        Planet(Position(-1, 0, 2)),
                        Planet(Position(2, -10, -7)),
                        Planet(Position(4, -8, 8)),
                        Planet(Position(3, 5, -1))
                    ),
                    2,
                    listOf(
                        Planet(Position(5, -3, -1), Velocity(3, -2, -2)),
                        Planet(Position(1, -2, 2), Velocity(-2, 5, 6)),
                        Planet(Position(1, -4, -1), Velocity(0, 3, -6)),
                        Planet(Position(1, -4, 2), Velocity(-1, -6, 2))
                    ),
                    245
                ),
                Arguments.of(
                    listOf(
                        Planet(Position(-1, 0, 2)),
                        Planet(Position(2, -10, -7)),
                        Planet(Position(4, -8, 8)),
                        Planet(Position(3, 5, -1))
                    ),
                    10,
                    listOf(
                        Planet(Position(2, 1, -3), Velocity(-3, -2, 1)),
                        Planet(Position(1, -8, 0), Velocity(-1, 1, 3)),
                        Planet(Position(3, -6, 1), Velocity(3, 2, -3)),
                        Planet(Position(2, 0, 4), Velocity(1, -1, -1))
                    ),
                    179
                ),
                Arguments.of(
                    listOf(
                        Planet(Position(x = -8, y = -10, z = 0)),
                        Planet(Position(x = 5, y = 5, z = 10)),
                        Planet(Position(x = 2, y = -7, z = 3)),
                        Planet(Position(x = 9, y = -8, z = -3))
                    ), 100,
                    listOf(
                        Planet(Position(x = 8, y = -12, z = -9), Velocity(x = -7, y = 3, z = 0)),
                        Planet(Position(x = 13, y = 16, z = -3), Velocity(x = 3, y = -11, z = -5)),
                        Planet(Position(x = -29, y = -11, z = -1), Velocity(x = -3, y = 7, z = 4)),
                        Planet(Position(x = 16, y = -13, z = 23), Velocity(x = 7, y = 1, z = 1))
                    ),
                    1940
                )

            )
    }

    @ParameterizedTest
    @MethodSource("planetsStepCases")
    fun gravitySteps(startingPlanets: List<Planet>, steps: Int, expectedPlanets: List<Planet>, expectedEnergy: Int) {
        val resultingPlanets = (0 until steps).fold(startingPlanets, { acc, _ -> Day12().stepPlanets(acc) })
        assertEquals(
            expectedPlanets, resultingPlanets
        )
    }

    @ParameterizedTest
    @MethodSource("planetsStepCases")
    fun gravityStepsEnergy(
        startingPlanets: List<Planet>,
        steps: Int,
        expectedPlanets: List<Planet>,
        expectedEnergy: Int
    ) {
        val resultingPlanets = (0 until steps).fold(startingPlanets, { acc, _ -> Day12().stepPlanets(acc) })
        assertEquals(expectedEnergy, resultingPlanets.map { it.energy() }.sum())
    }

    @Test
    fun applyVelocity() {
        val planet = Planet(Position(2, 76, 4), Velocity(26, -68, 35))
        val newPlanet = Day12().applyVelocity(planet)
        assertEquals(28, newPlanet.position.x)
        assertEquals(8, newPlanet.position.y)
        assertEquals(39, newPlanet.position.z)
    }

    @Test
    fun energy() {
        assertEquals(36, Planet(Position(2, 1, 3), Velocity(-3, -2, 1)).energy())
    }

    @Test
    fun findRepeated() {
        val planets = listOf(
            Planet(Position(-1, 0, 2)),
            Planet(Position(2, -10, -7)),
            Planet(Position(4, -8, 8)),
            Planet(Position(3, 5, -1))
        )
        val statesUntilRepeated = Day12().statesUntilRepeated(planets)
        assertEquals(2772L, statesUntilRepeated)
    }

    @Test
    fun findLargeRepeated() {
        val planets = listOf(
            Planet(Position(-8, -10, 0)),
            Planet(Position(5, 5, 10)),
            Planet(Position(2, -7, 3)),
            Planet(Position(9, -8, -3))
        )
        val statesUntilRepeated = Day12().statesUntilRepeated(planets)
        assertEquals(4686774924, statesUntilRepeated)
    }
}