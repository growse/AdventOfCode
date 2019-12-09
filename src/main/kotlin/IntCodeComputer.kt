package com.growse.adventofcode

fun main() {
    println(IntCodeComputer().runProgramFromResource())
    val day2combo = IntCodeComputer().findInputsThatProduce(19690720)
    println(day2combo.second + (day2combo.first * 100))
}

typealias Op = (Int, Int) -> Int

class IntCodeComputer {
    private val ops = mapOf<Int, Op>(
        Pair(1, { a: Int, b: Int -> a + b }),
        Pair(2, { a: Int, b: Int -> a * b })
    )

    fun executeProgram(inputProgram: List<Int>): List<Int> {
        val memory = inputProgram.toMutableList()
        val haltOp = 99
        var address = 0
        while (true) {
            if (memory[address] == haltOp) {
                break
            }
            memory[memory[address + 3]] =
                (ops[memory[address]] ?: error("Invalid opcode"))
                    .invoke(
                        memory[memory[address + 1]],
                        memory[memory[address + 2]]
                    )
            address += 4
        }
        return memory
    }

    private fun getInputProgram(): List<Int> {
        return this::class
            .java
            .getResourceAsStream("/day2.input.txt")
            .bufferedReader()
            .use { it.readText() }
            .split(",")
            .map { it.trim().toInt() }
    }

    fun runProgramFromResource(): String {
        return this.executeProgram(getInputProgram())
            .joinToString(",")
    }

    fun findInputsThatProduce(expected: Int): Pair<Int, Int> {
        val inputProgram = getInputProgram()
        val range = IntRange(0, 99)
        return sequence {
            range.forEach { a ->
                range.forEach { b ->
                    yield(a to b)
                }
            }
        }.map {
            val prog = inputProgram.toMutableList()
            prog.set(1, it.first)
            prog.set(2, it.second)
            it to executeProgram(prog)[0]
        }.filter { it.second == expected }.first().first
    }
}