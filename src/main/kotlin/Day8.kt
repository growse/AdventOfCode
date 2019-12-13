package com.growse.adventofcode

fun main() {
    println(
        Day8()
            .decodeImage(Day8().getInputImage("/day8.input.txt"), 25, 6)
            .layers
            .map {
                it
                    .imageData
                    .groupingBy { it1 -> it1 }
                    .eachCount()
            }
            .minBy {
                it[0] ?: error("No Images contain '0'")
            }!!
            .filter { it.key == 1 || it.key == 2 }
            .values
            .reduce { acc, i -> i * acc }
    )
}

class Day8 {
    fun getInputImage(resourceName: String): List<Int> {
        return this::class
            .java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }
            .map { it.toString().toInt() }
    }

    fun decodeImage(imageData: List<Int>, width: Int, height: Int): Image =
        Image(imageData.chunked(width * height).map {
            Layer(
                width,
                height,
                it
            )
        })

    data class Layer(val width: Int, val height: Int, val imageData: List<Int>) {
        fun rows(): List<List<Int>> = imageData.chunked(width)

    }

    data class Image(val layers: List<Layer>)
}


