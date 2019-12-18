package com.growse.adventofcode

import java.lang.Math.pow
import kotlin.math.sqrt

data class Coordinate(val x: Int, val y: Int) {
    fun distanceFrom(that: Coordinate): Double =
        sqrt(pow((this.x - that.x).toDouble(), 2.0) + pow((this.y - that.y).toDouble(), 2.0))
}