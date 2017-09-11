package com.example.chloe.confidence_interval_operations.interval_graph

class Point private constructor(val x: Int, val y: Int) {

    fun lengthX(secondPoint: Point) = Math.abs(x - secondPoint.x)

    fun lengthY(secondPoint: Point) = Math.abs(y - secondPoint.y)

    companion object {
        @JvmStatic
        fun of(x: Int, y: Int) = Point(x = x, y = y)
    }
}