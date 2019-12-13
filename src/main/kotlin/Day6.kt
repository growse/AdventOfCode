package com.growse.adventofcode

fun main() {
    println(Day6().calculateOrbitChecksumForResource("/day6.input.txt"))
    println(Day6().calculateOrbitTransferFromResource("/day6.input.txt"))
}

class Day6 {
    fun calculateOrbitChecksum(input: List<String>): Int {
        val graph: MutableMap<String, String?> = buildGraph(input)
        return graph.keys.sumBy { sumForNode(it, graph) }
    }

    private fun buildGraph(input: List<String>): MutableMap<String, String?> {
        val graph: MutableMap<String, String?> = mutableMapOf()
        input.forEach {
            val nodeNames = it.split(")", limit = 2)
            graph.putIfAbsent(nodeNames[0], null)
            graph.putIfAbsent(nodeNames[1], null)
            graph[nodeNames[1]] = nodeNames[0]

        }
        return graph
    }

    private fun sumForNode(key: String, graph: Map<String, String?>): Int {
        return if (graph[key] == null) {
            0
        } else {
            1 + sumForNode(graph[key]!!, graph)
        }
    }

    private fun getInputProgram(resourceName: String): List<String> =
        this::class
            .java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
            .split("\n")

    fun calculateOrbitChecksumForResource(s: String): Int {
        return calculateOrbitChecksum(getInputProgram(s))
    }

    fun calculateOrbitTransfer(input: List<String>, from: String, to: String): Int {
        val graph = buildGraph(input)

        val fromParentList = parents(graph, from)
        val toParentList = parents(graph, to)
        val commonParents = fromParentList.asReversed().intersect(toParentList.asReversed())
        return fromParentList.indexOf(commonParents.last()) + toParentList.indexOf(commonParents.last())

    }

    private fun parents(graph: Map<String, String?>, node: String): MutableList<String> {
        val ret = mutableListOf<String>()
        var current = node
        while (graph[current] != null) {
            ret.add(graph[current]!!)
            current = graph[current]!!
        }
        return ret
    }

    fun calculateOrbitTransferFromResource(s: String): Int {
        return calculateOrbitTransfer(getInputProgram(s), "YOU", "SAN")
    }
}
