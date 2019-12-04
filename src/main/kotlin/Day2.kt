package com.growse.adventofcode

fun main() {
    println(Day2().runProgramFromResource())
}

typealias Op = (Int, Int) -> Int

class Day2 {
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

    fun runProgramFromResource(): String {
        return this.executeProgram(this::class
            .java
            .getResourceAsStream("/day2.input.txt")
            .bufferedReader().use { it.readText() }
            .split(",")
            .map { it.trim().toInt() }
        )
            .joinToString(",")
    }
}