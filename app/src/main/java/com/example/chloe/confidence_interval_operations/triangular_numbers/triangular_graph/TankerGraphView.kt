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

    private lateinit var _number: TriangularNumber

    override fun onDraw(canvas: Canvas?) {
        _canvas = canvas!!

        _minValue = 0.0
        _maxValue = _number.rightBound

        drawAxes()

        drawTriangularNumber(_number, _paintForA)

        drawTriangularNumber(TriangularNumber.of(
                leftBound = 0.0,
                mid = 5.0,
                rightBound = _maxValue
        ), _paintForB)
    }

    fun setInitValues(number: TriangularNumber) {
        _number = number
    }
}