package com.example.chloe.confidence_interval_operations.interval_graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval


class GraphView @JvmOverloads constructor(
        context: Context,
        set: AttributeSet? = null,
        value: Int = 0)
    : CustomGraphView(context, set, value) {

    private lateinit var _interval: Interval
    private lateinit var _intervalName: String

    private val _pointPaint = Paint()

    private lateinit var _leftPointX: Point
    private lateinit var _rightPointX: Point
    private var _maxPoint = 0.0

    private var isDisabled = false

    init {
        _pointPaint.strokeWidth = 4.toFloat()
        _pointPaint.color = Color.DKGRAY
        _pointPaint.style = Paint.Style.FILL_AND_STROKE
    }


    fun putStartingValues(interval: Interval, intervalName: String) {
        _interval = interval
        _intervalName = intervalName
    }

    fun disable() {
        isDisabled = true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isDisabled) return

        _canvas = canvas!!
        drawAxes()
        drawText(text = _intervalName, point = getCornerPoint(Corners.TOP_LEFT),
                offsetX = 20, offsetY = 45)

        drawInterval(interval = _interval)
    }


    private fun drawAxes() {
        _leftPointX = Point.of(width / 10, height / 2)
        _rightPointX = Point.of(width - width / 10, height / 2)
        drawLine(_leftPointX, _rightPointX)
        drawArrowX(_rightPointX)

        val startY_point = Point.of(width / 2, height / 10)
        drawLine(
                startY_point,
                Point.of(width / 2, height - height / 10))
        drawArrowY(startY_point)

        _maxPoint = Math.max(Math.abs(_interval.leftBound), Math.abs(_interval.rightBound)) * 1.3

        drawText(text = String.format("%.2f", -_maxPoint),
                point = Point.of(_leftPointX.x, height / 2),
                offsetX = -30,
                offsetY = -25)

        drawText(text = String.format("%.2f", _maxPoint),
                point = Point.of(_rightPointX.x, height / 2),
                offsetX = -40,
                offsetY = -25)
    }


    private fun drawInterval(interval: Interval) {
        drawPointInterval(interval.leftBound, "a1")

        if (interval.leftBound == interval.rightBound) return

        //if they stay close, draw text above to
        //exclude layering
        if (Math.abs(interval.leftBound - interval.rightBound) <= _maxPoint * 0.23) {
            drawPointInterval(interval.rightBound, "a2", -1.1)
        } else {
            drawPointInterval(interval.rightBound, "a2")
        }
    }

    private fun drawPointInterval(value: Double, tag: String, scale: Double = 1.0) {
        val centerPoint = Point.of(width / 2, height / 2)
        val valueInPixes = valueToPixels(value)

        drawLine(Point.of(centerPoint.x + valueInPixes, centerPoint.y + 20),
                Point.of(centerPoint.x + valueInPixes, centerPoint.y - 20), _pointPaint)
        drawText(String.format("%.2f", value), centerPoint, valueInPixes - 30, (50 * scale).toInt())
        drawText(tag, centerPoint, valueInPixes - 20, (85 * scale).toInt())
    }


    private fun valueToPixels(value: Double): Int {
        val pixelsInOneAxis = _rightPointX.x - _leftPointX.x
        val pixelPerOneValue = (pixelsInOneAxis / 2) / _maxPoint

        return (pixelPerOneValue * value).toInt()
    }
}