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

    Day8()
        .decodeImage(Day8().getInputImage("/day8.input.txt"), 25, 6)
        .flatten()
        .forEachIndexed { index, i ->
            run {
                print(if (i == 1) i else " ")
                if ((index + 1) % 25 == 0) {
                    println()
                }
            }
        }
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

    data class Image(val layers: List<Layer>) {
        fun flatten(): List<Int> =
            layers
                .first()
                .imageData
                .mapIndexed { idx, _ -> resolveTransparent(layers.map { it.imageData[idx] }) }


        private fun resolveTransparent(pixels: List<Int>): Int =
            pixels.first { it != 2 }

    }
}


