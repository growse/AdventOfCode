package com.growse.adventofcode

fun main() {
    val emergencyHullPaintingRobot = EmergencyHullPaintingRobot()
    emergencyHullPaintingRobot.loadProgramFromResource("/day11.input.txt")
    emergencyHullPaintingRobot.runToCompletion()
    println(emergencyHullPaintingRobot.getNumberOfUniquePanelsPainted())

    val emergencyHullPaintingRobotPt2 = EmergencyHullPaintingRobot()
    emergencyHullPaintingRobotPt2.loadProgramFromResource("/day11.input.txt")
    emergencyHullPaintingRobotPt2.setInitialStartingLocationColour(PaintColour.WHITE)
    emergencyHullPaintingRobotPt2.runToCompletion()
    emergencyHullPaintingRobotPt2.renderResult()
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}

enum class PaintColour {
    BLACK,
    WHITE
}

data class ColourAndDirection(val colour: PaintColour, val direction: Direction)

class EmergencyHullPaintingRobot {
    var facingDirection: Direction = Direction.NORTH

    var currentLocation = Coordinate(0, 0)
    private val paintedPanels = mutableListOf<Pair<Coordinate, PaintColour>>()
    private val intCodeComputer = IntCodeComputer()
    private var programComplete = false
    fun loadProgramFromResource(resourceName: String) {
        intCodeComputer.loadIntoMemory(intCodeComputer.getInputProgram(resourceName))
    }

    fun paintAndMove(colourAndDirection: ColourAndDirection) {
        paintedPanels.add(Pair(currentLocation, colourAndDirection.colour))
        this.facingDirection = colourAndDirection.direction
        this.currentLocation = when (colourAndDirection.direction) {
            Direction.NORTH -> Coordinate(currentLocation.x, currentLocation.y + 1)
            Direction.EAST -> Coordinate(currentLocation.x + 1, currentLocation.y)
            Direction.SOUTH -> Coordinate(currentLocation.x, currentLocation.y - 1)
            Direction.WEST -> Coordinate(currentLocation.x - 1, currentLocation.y)
        }
    }

    fun getNumberOfUniquePanelsPainted(): Int =
        paintedPanels.distinctBy { it.first }.size

    private fun getColourOfPanel(c: Coordinate): PaintColour =
        paintedPanels.lastOrNull { it.first == c }?.second ?: PaintColour.BLACK

    fun getProgramOutput(): ColourAndDirection {
        val computerInput = if (getColourOfPanel(currentLocation) == PaintColour.WHITE) 1 else 0
        intCodeComputer.addInput(computerInput)
        try {
            intCodeComputer.resume()
            programComplete = true
        } catch (e: WaitForInputInterrupt) {

        }
        return ColourAndDirection(
            if (intCodeComputer.outputs().dropLast(1).last().toInt() == 1) PaintColour.WHITE else PaintColour.BLACK,
            getNewDirection(this.facingDirection, intCodeComputer.outputs().last().toInt())
        )
    }

    fun getNewDirection(initial: Direction, value: Int): Direction {
        return when (initial) {
            Direction.NORTH -> if (value == 1) Direction.EAST else Direction.WEST
            Direction.EAST -> if (value == 1) Direction.SOUTH else Direction.NORTH
            Direction.SOUTH -> if (value == 1) Direction.WEST else Direction.EAST
            Direction.WEST -> if (value == 1) Direction.NORTH else Direction.SOUTH
        }

    }

    fun runToCompletion() {
        while (!programComplete) {
            paintAndMove(getProgramOutput())
        }
    }

    fun setInitialStartingLocationColour(colour: PaintColour) {
        paintedPanels.add(Pair(Coordinate(0, 0), colour))
    }

    fun renderResult() {
        val topLeft =
            Coordinate(paintedPanels.minBy { it.first.x }!!.first.x, paintedPanels.maxBy { it.first.y }!!.first.y)
        val bottomRight =
            Coordinate(paintedPanels.maxBy { it.first.x }!!.first.x, paintedPanels.minBy { it.first.y }!!.first.y)
        println("$topLeft $bottomRight")
        (topLeft.y downTo bottomRight.y).forEach { row ->
            println((topLeft.x..bottomRight.x).map { col ->
                paintedPanels.lastOrNull {
                    it.first == Coordinate(
                        col,
                        row
                    )
                }?.second ?: PaintColour.BLACK
            }.joinToString { if (it == PaintColour.WHITE) "#" else "." })
        }
    }
}