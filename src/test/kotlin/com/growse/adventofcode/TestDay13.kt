package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay13 {
    @Test
    fun startingBlocksCountCorrect() {
        val arcadeCabinet = ArcadeCabinet()
        arcadeCabinet.loadFromResource("/day13.input.txt")
        assertEquals(369, arcadeCabinet.getOutputs.chunked(3).count { it[2].toInt() == 2 })
    }
}