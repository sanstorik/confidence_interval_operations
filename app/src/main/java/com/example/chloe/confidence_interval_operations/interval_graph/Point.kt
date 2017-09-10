package com.example.chloe.confidence_interval_operations.interval_graph

class Point private constructor(private val _x: Int, private val _y: Int) {

    val x get() =  _x
    val y get() = _y

    fun lengthX(secondPoint: Point) = Math.abs(_x - secondPoint._x)

    fun lengthY(secondPoint: Point) = Math.abs(_y - secondPoint._y)

    companion object {
        @JvmStatic
        fun of(x: Int, y: Int) = Point(_x = x, _y = y)
    }
}