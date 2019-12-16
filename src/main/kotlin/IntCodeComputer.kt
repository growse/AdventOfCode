package com.growse.adventofcode

import java.util.*
import java.util.concurrent.ArrayBlockingQueue

class WaitForInputInterrupt : Throwable()
class IntCodeComputer(inputs: List<Int> = emptyList()) {
    private var relativeMemoryBase: Int = 0
    private var inputQueue: Queue<Int> = ArrayBlockingQueue(10)
    private val outputs = mutableListOf<Int>()
    private var address = 0
    private val memory: MutableMap<Int, Int> = mutableMapOf()

    init {
        inputQueue.addAll(inputs)
    }

    private val haltOp = 99
    private val ops = mapOf(
        Pair(1, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            inputs.memory[inputs.paramAddresses[2]] =
                inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } + inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 4,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(2, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            inputs.memory[inputs.paramAddresses[2]] =
                inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } * inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 4,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(3, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            inputs.memory[inputs.paramAddresses[0]] = inputQueue.remove()
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 2,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(4, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            outputs.add(inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 })
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 2,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(5, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            return AddressAndRelativeBase(
                if (
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } != 0
                )
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
                else
                    inputs.addressAndRelativeBase.address + 3,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(6, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            return AddressAndRelativeBase(
                if (
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } == 0
                )
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
                else
                    inputs.addressAndRelativeBase.address + 3,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(7, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            inputs.memory[inputs.paramAddresses[2]] =
                if (
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } < inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
                ) 1 else 0
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 4,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(8, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            inputs.memory[inputs.paramAddresses[2]] =
                if (
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } ==
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[1]) { 0 }
                ) 1 else 0
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 4,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(9, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 2,
                inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 }
            )
        })
    )

    private fun paramAddressesFromMask(
        memory: MutableMap<Int, Int>,
        address: Int,
        modeMask: List<Int>,
        relativeMemoryBase: Int
    ): List<Int> =
        modeMask
            // Need to flip it round as the mask index reads RtoL
            .asReversed()
            .mapIndexed { index, mode ->
                when (mode) {
                    0 -> memory.computeIfAbsent(address + index + 1) { 0 }
                    1 -> address + index + 1
                    2 -> memory.computeIfAbsent(address + index + 1) { 0 } + relativeMemoryBase
                    else -> 0
                }
            }


    fun executeProgram(inputProgram: List<Int>): List<Int> {
        load(inputProgram)
        return resume()
    }

    fun load(inputProgram: List<Int>) {
        inputProgram.mapIndexed { index, i -> memory.put(index, i) }
        address = 0
    }

    fun resume(): List<Int> {
        while (true) {
            if (memory[address] == haltOp) {
                break
            }
            val opcode = memory[address]!! % 100

            val modeMask = (memory[address]!! / 100)
                .toString()
                .padStart(3, '0')
                .map { it.toString().toInt() }

            try {
                val paramAddresses = paramAddressesFromMask(memory, address, modeMask, relativeMemoryBase)
                val newAddressState =
                    (ops[opcode] ?: error("Opcode $opcode not supported")).invoke(
                        OpCodeParams(
                            memory,
                            AddressAndRelativeBase(address, relativeMemoryBase),
                            paramAddresses
                        )
                    )
                address = newAddressState.address
                relativeMemoryBase = newAddressState.relativeMemoryBase
            } catch (e: NoSuchElementException) {
                throw WaitForInputInterrupt()
            }
        }
        return memory.values.toList()
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

    fun outputs(): List<Int> = outputs
    fun addInput(i: Int) {
        inputQueue.add(i)
    }

    data class AddressAndRelativeBase(val address: Int, val relativeMemoryBase: Int)
    data class OpCodeParams(
        val memory: MutableMap<Int, Int>,
        val addressAndRelativeBase: AddressAndRelativeBase,
        val paramAddresses: List<Int>
    )

}