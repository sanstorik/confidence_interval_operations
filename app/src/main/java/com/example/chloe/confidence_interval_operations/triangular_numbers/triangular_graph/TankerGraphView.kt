package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import com.example.chloe.confidence_interval_operations.interval_graph.Point
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber

class TankerGraphView @JvmOverloads constructor(
        _context: Context,
        _set: AttributeSet? = null,
        _value: Int  = 0
): TriangularGraphView(_context, _set, _value) {

    private lateinit var _left: TriangularNumber
    private lateinit var _right: TriangularNumber
    private var _iteration = 0
    private var _maxIteration = 0

    override fun onDraw(canvas: Canvas?) {
        _canvas = canvas!!

        _minValue = 0.0
        _maxValue = Math.max(_left.rightBound, _right.rightBound) * 1.05

        drawAxes()

        drawTriangularNumber(_left, _paintForA)
        drawTriangularNumber(_right, _paintForB)

        drawIterationText()
        drawLineMarkers()
    }


    fun setInitValues(left: TriangularNumber, right: TriangularNumber,
                      iteration: Int, maxIteration: Int) {
        _left = left
        _right = right
        _iteration = iteration
        _maxIteration = maxIteration
    }

    private fun drawIterationText() {
        val textToDraw = if (_iteration < _maxIteration - 1) {
            "Current iteration = ${_iteration + 1}"
        } else {
            "Conflicting situation"
        }

        drawText(textToDraw, getCornerPoint(Corners.TOP_RIGHT),
                offsetX = -400, offsetY = 50)
    }

    private fun drawLineMarkers() {
        drawLineMarker(_paintForB, "query", 80, textOffsetX = 0, textOffsetY = -20)
        drawLineMarker(_paintForA, "left", 0, textOffsetX = 0, textOffsetY = -20)
    }
}