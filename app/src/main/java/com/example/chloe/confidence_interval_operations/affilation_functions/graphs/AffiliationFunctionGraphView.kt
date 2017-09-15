package com.example.chloe.confidence_interval_operations.affilation_functions.graphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.chloe.confidence_interval_operations.affilation_functions.AffiliationFunction
import com.example.chloe.confidence_interval_operations.interval_graph.CustomGraphView
import com.example.chloe.confidence_interval_operations.interval_graph.Point

class AffiliationFunctionGraphView @JvmOverloads constructor(
        _context: Context,
        _set: AttributeSet? = null,
        _value: Int = 0
) : CustomGraphView(_context, _set, _value) {

    private lateinit var _function: AffiliationFunction
    private lateinit var _leftPointX: Point
    private lateinit var _rightPointX: Point
    private lateinit var _leftStartingPointOne: Point

    private var _min = 0.0
    private var _max = 0.0

    private var _funcPaint = Paint()

    init {
        _funcPaint.strokeWidth = 8.toFloat()
        _funcPaint.color = Color.BLACK
        _funcPaint.style = Paint.Style.FILL_AND_STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        _canvas = canvas!!

        drawAxes()
        drawFunction()
    }


    fun startingInit(function: AffiliationFunction) {
        _function = function

        _min = _function.getMinX() *
                if (areTheSameSign(_function.getMinX(), _function.getMaxX())) 0.85 else 1.35
        _max = _function.getMaxX() * 1.15
    }


    private fun drawAxes() {
        _leftPointX = Point.of(width / 12, height - height / 5)
        _rightPointX = Point.of(width - width / 8, height - height / 5)

        drawLine(_leftPointX, _rightPointX)

        _leftStartingPointOne = Point.of(_leftPointX.x, height / 9)

        drawArrowX(_rightPointX)

        drawLine(Point.of(valueToPixels(_min), _leftPointX.y),
                Point.of(valueToPixels(_min), _leftStartingPointOne.y))

        drawArrowY(_leftStartingPointOne, "a")
        drawText(text = "1", point = _leftStartingPointOne,
                offsetX = 20, offsetY = 20)


        drawText(text = String.format("%.2f", _min),
                point = _leftPointX,
                offsetX = -30,
                offsetY = 70)

        drawText(text = String.format("%.2f", _max),
                point = _rightPointX,
                offsetX = -40,
                offsetY = 70)

        drawText(text = _function.description,
                point = getCornerPoint(Corners.TOP_LEFT),
                offsetX = 40,
                offsetY = 25)
    }


    private fun drawFunction() {
        var value = _min
        val step = getFunctionLengthX() / 1000

        var lastPoint = Point.of(x = valueToPixels(value),
                y = yValueToPixels(_function.findAffiliationDegree(value)))

        while(value <= _max) {
            val point = Point.of(x = valueToPixels(value),
                    y = yValueToPixels(_function.findAffiliationDegree(value)))

            drawLine(lastPoint, point, _funcPaint)

            value += step
            lastPoint = point
        }
    }


    private fun valueToPixels(value: Double): Int {
        val length = _leftPointX.lengthX(_rightPointX)
        val pixelsPerValue = length / getFunctionLengthX()

        return (value * pixelsPerValue).toInt() + (-_min * pixelsPerValue).toInt() +
                _leftPointX.x
    }


    private fun areTheSameSign(_min: Double, _max: Double): Boolean {
        return (_min < 0 && _max < 0)
                || (_min > 0 && _max > 0)
    }


    private fun getFunctionLengthX() =
            if ( areTheSameSign(_min, _max) ) {
                Math.abs(_max) - Math.abs(_min)
            } else {
                Math.abs(_max) + Math.abs(_min)
            }

    /**
     * We divide y height on 100 points [0;1]]
     */
    private fun yValueToPixels(value: Double): Int {
        val length = _leftPointX.lengthY(_leftStartingPointOne)
        val pixelsPerValue = length

        return (-value * pixelsPerValue).toInt() + _leftPointX.y
    }
}