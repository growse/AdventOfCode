package com.growse.adventofcode

import java.util.*

class IntCodeComputer {

    private fun paramAddressesWithBitMask(memory: List<Int>, address: Int, modeMask: BitSet): List<Int> {
        return when (memory.size - 1 - address) {
            0 -> error("At memory's end")
            1 -> listOf(
                if (modeMask[0]) address + 1 else memory[address + 1]
            )
            2 -> listOf(
                if (modeMask[0]) address + 1 else memory[address + 1],
                if (modeMask[1]) address + 2 else memory[address + 2]
            )
            else -> listOf(
                if (modeMask[0]) address + 1 else memory[address + 1],
                if (modeMask[1]) address + 2 else memory[address + 2],
                if (modeMask[2]) address + 3 else memory[address + 3]
            )
        }

    }

    private val ops = mapOf(
        Pair(1, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            memory[paramAddresses[2]] = memory[paramAddresses[0]] + memory[paramAddresses[1]]
            return 4
        }),
        Pair(2, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            memory[paramAddresses[2]] = memory[paramAddresses[0]] * memory[paramAddresses[1]]
            return 4
        }),
        Pair(3, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            memory[paramAddresses[0]] = readLine()!!.toInt()
            return 2
        }),
        Pair(4, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            println(memory[paramAddresses[0]])
            return 2
        }),
        Pair(5, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            return if (memory[paramAddresses[0]] != 0) memory[paramAddresses[1]] - address else 3
        }),
        Pair(6, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            return if (memory[paramAddresses[0]] == 0) memory[paramAddresses[1]] - address else 3
        }),
        Pair(7, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            memory[paramAddresses[2]] = if (memory[paramAddresses[0]] < memory[paramAddresses[1]]) 1 else 0
            return 4
        }),
        Pair(8, fun(memory: MutableList<Int>, address: Int, modeMask: BitSet): Int {
            val paramAddresses = paramAddressesWithBitMask(memory, address, modeMask)
            memory[paramAddresses[2]] = if (memory[paramAddresses[0]] == memory[paramAddresses[1]]) 1 else 0
            return 4
        })
    )

    fun executeProgram(inputProgram: List<Int>): List<Int> {
        val memory = inputProgram.toMutableList()
        val haltOp = 99
        var address = 0
        while (true) {
            if (memory[address] == haltOp) {
                break
            }
            val opcode = memory[address] % 100
            val byteArray = ByteArray(1)
            byteArray[0] = (memory[address] / 100)
                .toString()
                .toInt(2)
                .toByte()

            val modeMask = BitSet.valueOf(byteArray)
            address += (ops[opcode] ?: error("Opcode $opcode not supported")).invoke(memory, address, modeMask)
        }
        return memory
    }

    fun getInputProgram(resourceName: String): List<Int> {
        return this::class
            .java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
            .split(",")
            .map { it.trim().toInt() }
    }

    fun executeNamedResourceProgram(resourceName: String): List<Int> {
        return executeProgram(getInputProgram(resourceName))
    }
}