package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class TestDay10 {
    private val testGrid = """
.#..#
.....
#####
....#
...##
    """.trimIndent()


    @Test
    fun visibleAsteroidsFromAsteroidInGrid() {
        val grid = Day10().stringToGrid(testGrid)
        assertEquals(7, Day10().visibleAsteroids(grid.asteroids.first(), grid.asteroids))
    }

    @Test
    fun stringToGrid() {
        val grid = Day10().stringToGrid(testGrid)
        assertEquals(5, grid.width)
        assertEquals(5, grid.height)
        assertEquals(10, grid.asteroids.size)
        assertTrue(
            grid.asteroids.containsAll(
                listOf(
                    Coordinate(1, 0),
                    Coordinate(4, 0),
                    Coordinate(0, 2),
                    Coordinate(1, 2),
                    Coordinate(2, 2),
                    Coordinate(3, 2),
                    Coordinate(4, 2),
                    Coordinate(4, 3),
                    Coordinate(3, 4),
                    Coordinate(4, 4)

                )
            )
        )
    }

    companion object {
        @JvmStatic
        fun visibleAsteroidTestCases(): Stream<Arguments> =
            of(
                Arguments.of(
                    Coordinate(1, 1),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(1, 1)
                    )
                ),
                Arguments.of(
                    Coordinate(2, 1),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(1, 1),
                        Coordinate(2, 1)
                    )
                ),
                Arguments.of(
                    Coordinate(0, 5),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(0, 5),
                        Coordinate(0, -3)
                    )
                ),
                Arguments.of(
                    Coordinate(0, -5),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(0, -5),
                        Coordinate(0, 3)
                    )
                ),
                Arguments.of(
                    Coordinate(5, 0),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(5, 0),
                        Coordinate(-3, 0)
                    )
                ),
                Arguments.of(
                    Coordinate(-5, 0),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(-5, 0),
                        Coordinate(3, 0)
                    )
                )
            )

        @JvmStatic
        fun invisibleAsteroidTestCases(): Stream<Arguments> =
            of(
                Arguments.of(
                    Coordinate(0, 5),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(0, 5),
                        Coordinate(0, 3)
                    )
                ),
                Arguments.of(
                    Coordinate(0, -5),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(0, -5),
                        Coordinate(0, -3)
                    )
                ),
                Arguments.of(
                    Coordinate(5, 0),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(5, 0),
                        Coordinate(3, 0)
                    )
                ),
                Arguments.of(
                    Coordinate(-5, 0),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(-5, 0),
                        Coordinate(-3, 0)
                    )
                ),
                Arguments.of(
                    Coordinate(4, 2),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(2, 1),
                        Coordinate(4, 2)
                    )
                ),
                Arguments.of(
                    Coordinate(24, 12),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(24, 12),
                        Coordinate(4, 2)
                    )
                ),
                Arguments.of(
                    Coordinate(3, 3),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(3, 3),
                        Coordinate(1, 1)
                    )
                ),
                Arguments.of(
                    Coordinate(3, 3),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(3, 3),
                        Coordinate(2, 2)
                    )
                ),
                Arguments.of(
                    Coordinate(-3, -3),
                    listOf(
                        Coordinate(0, 0),
                        Coordinate(-3, -3),
                        Coordinate(-2, -2)
                    )
                )
            )

        @JvmStatic

        fun monitoringStationFinderTestCases(): Stream<Arguments> = of(
            Arguments.of(
                """
                .#..#
                .....
                #####
                ....#
                ...##
    """.trimIndent(), Coordinate(3, 4), 8
            ),
            Arguments.of(
                """
                ......#.#.
                #..#.#....
                ..#######.
                .#.#.###..
                .#..#.....
                ..#....#.#
                #..#....#.
                .##.#..###
                ##...#..#.
                .#....####
            """.trimIndent(), Coordinate(5, 8), 33
            ),
            Arguments.of(
                """
                #.#...#.#.
                .###....#.
                .#....#...
                ##.#.#.#.#
                ....#.#.#.
                .##..###.#
                ..#...##..
                ..##....##
                ......#...
                .####.###.
            """.trimIndent(), Coordinate(1, 2), 35
            ),
            Arguments.of(
                """
                .#..#..###
                ####.###.#
                ....###.#.
                ..###.##.#
                ##.##.#.#.
                ....###..#
                ..#.#..#.#
                #..#.#.###
                .##...##.#
                .....#.#..
            """.trimIndent(), Coordinate(6, 3), 41
            ),
            Arguments.of(
                """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##
            """.trimIndent(), Coordinate(11, 13), 210
            )
        )
    }

    @ParameterizedTest
    @MethodSource("visibleAsteroidTestCases")
    fun asteroidVisibilityTest(asteroid: Coordinate, asteroids: List<Coordinate>) {
        assertTrue(
            Day10()
                .isAsteroidVisibleFromOrigin(
                    asteroid, asteroids
                )
        )
    }

    @ParameterizedTest()
    @MethodSource("invisibleAsteroidTestCases")
    fun asteroidInvisibility(asteroid: Coordinate, asteroids: List<Coordinate>) {
        assertFalse(
            Day10()
                .isAsteroidVisibleFromOrigin(
                    asteroid, asteroids
                )
        )
    }

    @ParameterizedTest
    @MethodSource("monitoringStationFinderTestCases")
    fun monitoringStationFinder(inputGrid: String, expectedBestStation: Coordinate, expectedVisibleAsteroids: Int) {
        val bestMonitoringStation = Day10().findBestMonitoringStationLocation(inputGrid)
        assertEquals(expectedBestStation, bestMonitoringStation.first)
        assertEquals(expectedVisibleAsteroids, bestMonitoringStation.second)
    }

    @Test
    fun laserSweeperOrder() {
        val inputGrid = """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##
            """.trimIndent()
        val laserBase = Coordinate(11, 13)
        val laserSweepDestroyOrder = Day10().laserSweepAsteroidDestroyOrder(inputGrid, laserBase)
        assertEquals(Coordinate(11, 12), laserSweepDestroyOrder[0])
        assertEquals(Coordinate(12, 1), laserSweepDestroyOrder[1])
        assertEquals(Coordinate(12, 2), laserSweepDestroyOrder[2])
        assertEquals(Coordinate(12, 8), laserSweepDestroyOrder[9])
        assertEquals(Coordinate(16, 0), laserSweepDestroyOrder[19])
        assertEquals(Coordinate(16, 9), laserSweepDestroyOrder[49])
        assertEquals(Coordinate(10, 16), laserSweepDestroyOrder[99])
        assertEquals(Coordinate(9, 6), laserSweepDestroyOrder[198])
        assertEquals(Coordinate(8, 2), laserSweepDestroyOrder[199])
        assertEquals(Coordinate(10, 9), laserSweepDestroyOrder[200])
        assertEquals(Coordinate(11, 1), laserSweepDestroyOrder[298])
        assertEquals(299, laserSweepDestroyOrder.size)
    }
}