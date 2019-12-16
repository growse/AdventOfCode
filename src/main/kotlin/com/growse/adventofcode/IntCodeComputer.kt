package com.growse.adventofcode

import java.util.*
import java.util.concurrent.ArrayBlockingQueue

class WaitForInputInterrupt : Throwable()
class IntCodeComputer(inputs: List<Number> = emptyList()) {
    private var relativeMemoryBase: Long = 0
    private var inputQueue: Queue<Number> = ArrayBlockingQueue(10)
    private val outputs = mutableListOf<Long>()
    private var address = 0L
    private val memory: MutableMap<Long, Long> = mutableMapOf()

    init {
        inputQueue.addAll(inputs.map { it.toLong() })
    }

    private val haltOp = 99L
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
            inputs.memory[inputs.paramAddresses[0]] = inputQueue.remove().toLong()
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
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } != 0L
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
                    inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 } == 0L
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
                ) 1L else 0L
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
                ) 1L else 0L
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 4,
                inputs.addressAndRelativeBase.relativeMemoryBase
            )
        }),
        Pair(9, fun(inputs: OpCodeParams): AddressAndRelativeBase {
            return AddressAndRelativeBase(
                inputs.addressAndRelativeBase.address + 2,
                inputs.addressAndRelativeBase.relativeMemoryBase + inputs.memory.computeIfAbsent(inputs.paramAddresses[0]) { 0 }
            )
        })
    )

    private fun paramAddressesFromMask(
        memory: MutableMap<Long, Long>,
        address: Long,
        modeMask: List<Int>,
        relativeMemoryBase: Long
    ): List<Long> =
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

    fun executeProgram(inputProgram: List<Number>): List<Long> {
        loadIntoMemory(inputProgram)
        return resume()
    }

    internal fun loadIntoMemory(inputProgram: List<Number>) {
        inputProgram.mapIndexed { index, i -> memory.put(index.toLong(), i.toLong()) }
        address = 0
    }

    fun resume(): List<Long> {
        while (true) {
            if (memory[address] == haltOp) {
                break
            }
            val opcode = (memory[address]!! % 100).toInt()

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
                            AddressAndRelativeBase(
                                address,
                                relativeMemoryBase
                            ),
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

    fun getInputProgram(resourceName: String): List<Long> {
        return this::class
            .java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
            .split(",")
            .map { it.trim().toLong() }
    }

    fun executeNamedResourceProgram(resourceName: String): List<Number> {
        return executeProgram(getInputProgram(resourceName))
    }

    fun outputs(): List<Number> = outputs
    fun addInput(i: Number) {
        inputQueue.add(i)
    }

    data class AddressAndRelativeBase(val address: Long, val relativeMemoryBase: Long)
    data class OpCodeParams(
        val memory: MutableMap<Long, Long>,
        val addressAndRelativeBase: AddressAndRelativeBase,
        val paramAddresses: List<Long>
    )

}