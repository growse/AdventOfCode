package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay11 {

    @Test
    fun robotPaintsFirstPanelBlack() {
        val emergencyHullPaintingRobot = EmergencyHullPaintingRobot()
        emergencyHullPaintingRobot.loadProgramFromResource("/day11.input.txt")
        emergencyHullPaintingRobot.paintAndMove(emergencyHullPaintingRobot.getProgramOutput())
        assertEquals(Direction.WEST, emergencyHullPaintingRobot.facingDirection)
        assertEquals(Coordinate(-1, 0), emergencyHullPaintingRobot.currentLocation)
    }

    @Test
    fun directionTurner() {
        assertEquals(Direction.EAST, EmergencyHullPaintingRobot().getNewDirection(Direction.NORTH, 1))
        assertEquals(Direction.EAST, EmergencyHullPaintingRobot().getNewDirection(Direction.SOUTH, 0))
        assertEquals(Direction.NORTH, EmergencyHullPaintingRobot().getNewDirection(Direction.EAST, 0))
        assertEquals(Direction.SOUTH, EmergencyHullPaintingRobot().getNewDirection(Direction.WEST, 0))
    }
}