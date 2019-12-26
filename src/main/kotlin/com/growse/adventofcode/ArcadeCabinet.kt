package com.growse.adventofcode

import com.googlecode.lanterna.TerminalPosition
import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.TextCharacter
import com.googlecode.lanterna.TextColor
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.screen.Screen
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import java.lang.Thread.sleep


fun main() {
    val arcadeCabinet = ArcadeCabinet()
    arcadeCabinet.loadFromResource("/day13.input.txt")
    println(arcadeCabinet.getOutputs.chunked(3).count { it[2].toInt() == 2 })

    val freeArcadeCabinet = ArcadeCabinet()
    freeArcadeCabinet.loadFromResourceAndAddTwoQuarters("/day13.input.txt")

}

class ArcadeCabinet {
    enum class Tile {
        EMPTY,
        WALL,
        BLOCK,
        PADDLE,
        BALL;

        fun getChar(): TextCharacter {
            return when (this) {
                EMPTY -> TextCharacter(' ')
                WALL -> TextCharacter('⁜', TextColor.ANSI.CYAN, TextColor.ANSI.YELLOW)
                BLOCK -> TextCharacter('█', TextColor.ANSI.RED, TextColor.ANSI.BLACK)
                PADDLE -> TextCharacter('¯', TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK)
                BALL -> TextCharacter('○', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK)
            }
        }
    }

    private val terminal: Terminal =
        DefaultTerminalFactory()
            .setInitialTerminalSize(TerminalSize(50, 30))
            .createTerminal()
    private val screen: Screen = TerminalScreen(terminal)

    init {
        screen.startScreen()
        screen.cursorPosition = null;
    }


    private val intCodeComputer = IntCodeComputer()
    fun loadFromResource(resourceName: String) {
        try {
            intCodeComputer.loadIntoMemory(intCodeComputer.getInputProgram(resourceName))
            intCodeComputer.resume()
        } finally {
            destruct()
        }
    }

    fun loadFromResourceAndAddTwoQuarters(resourceName: String) {
        try {
            while (true) {
                val program = intCodeComputer.getInputProgram(resourceName)
                val freeProgram = program.mapIndexed { index, l -> if (index == 0) 2L else l }
                intCodeComputer.loadIntoMemory(freeProgram)
                while (true) {
                    try {
                        intCodeComputer.resume()
                        break
                    } catch (e: WaitForInputInterrupt) {
                    }
                    renderScreen()
                    when {
                        paddleCol == ballCol -> {
                            intCodeComputer.addInput(0)
                        }
                        paddleCol < ballCol -> {
                            intCodeComputer.addInput(1)
                        }
                        paddleCol > ballCol -> {
                            intCodeComputer.addInput(-1)
                        }
                    }
                }
                renderScreen()
                terminal.bell()
                sleep(5000)
            }

        } finally {
            destruct()
        }
    }

    private fun waitForKey(): KeyStroke? {
        while (true) {
            val input = screen.pollInput()
            if (input != null) {
                return input
            }
            try {
                sleep(10)
            } catch (ignore: InterruptedException) {
                break
            }
        }
        return null
    }

    val getOutputs: List<Number> = intCodeComputer.outputs()
    var highScore = 0
    var outputOffset = 0
    var paddleCol = 0
    var ballCol = 0
    private fun renderScreen() {
        intCodeComputer.outputs().drop(outputOffset)
            .chunked(3)
            .also {
                it
                    .filter { triple -> triple[0].toInt() == -1 && triple[1].toInt() == 0 }
                    .forEach { triple ->
                        if (triple[2].toInt() > highScore) {
                            highScore = triple[2].toInt()
                        }

                        screen.newTextGraphics().putString(0, 0, triple[2].toString().padEnd(10, ' '))
                        screen.newTextGraphics()
                            .putString(terminal.terminalSize.columns - 10, 0, highScore.toString().padStart(10, ' '))
                    }
            }
            .filter { it[0].toInt() != -1 && it[1].toInt() != 0 }
            .map { TerminalPosition(it[0].toInt(), it[1].toInt() + 1) to Tile.values()[it[2].toInt()] }
            .forEach {
                if (it.second == Tile.BALL) {
                    ballCol = it.first.column
                } else if (it.second == Tile.PADDLE) {
                    paddleCol = it.first.column
                }
                screen.setCharacter(it.first, it.second.getChar())
            }
        screen.doResizeIfNecessary()
        screen.refresh()
        outputOffset = intCodeComputer.outputs().size
    }

    private fun destruct() {
        screen.stopScreen()
        terminal.close()
    }
}
