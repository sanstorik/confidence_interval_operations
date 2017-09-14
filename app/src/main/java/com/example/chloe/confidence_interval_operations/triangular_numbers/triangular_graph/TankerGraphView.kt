package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber

class TankerGraphView @JvmOverloads constructor(
        _context: Context,
        _set: AttributeSet? = null,
        _value: Int  = 0
): TriangularGraphView(_context, _set, _value) {

    private lateinit var _left: TriangularNumber
    private lateinit var _right: TriangularNumber

    override fun onDraw(canvas: Canvas?) {
        _canvas = canvas!!

        _minValue = 0.0
        _maxValue = _right.rightBound

        drawAxes()

        drawTriangularNumber(_left, _paintForA)
        drawTriangularNumber(_right, _paintForB)
    }

    fun setInitValues(left: TriangularNumber, right: TriangularNumber) {
        _left = left
        _right = right
    }
}