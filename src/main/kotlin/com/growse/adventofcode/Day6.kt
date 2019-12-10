package com.growse.adventofcode.com.growse.adventofcode

fun main() {
    println(Day6().getOrbitChecksumForResource("/day6.input.txt"))
}

class Day6 {
    fun getOrbitChecksum(input: List<String>): Int {
        val graph: MutableMap<String, String?> = mutableMapOf()
        input.forEach {
            val nodeNames = it.split(")", limit = 2)
            graph.putIfAbsent(nodeNames[0], null)
            graph.putIfAbsent(nodeNames[1], null)
            graph[nodeNames[1]] = nodeNames[0]

        }
        return graph.keys.sumBy { sumForNode(it, graph) }

    }

    private fun sumForNode(key: String, graph: Map<String, String?>): Int {
        return if (graph[key] == null) {
            0
        } else {
            1 + sumForNode(graph[key]!!, graph)
        }
    }

    fun getInputProgram(resourceName: String): List<String> =
        this::class
            .java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
            .split("\n")

    fun getOrbitChecksumForResource(s: String): Int {
        return getOrbitChecksum(getInputProgram(s))

    }
}
