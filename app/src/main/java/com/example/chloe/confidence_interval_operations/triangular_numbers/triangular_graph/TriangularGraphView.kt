package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.example.chloe.confidence_interval_operations.interval_graph.CustomGraphView
import com.example.chloe.confidence_interval_operations.interval_graph.Point
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber

open class TriangularGraphView @JvmOverloads constructor(
        context: Context,
        set: AttributeSet? = null,
        value: Int = 0

): CustomGraphView(context, set, value) {

    private lateinit var _leftStartingPointX: Point
    private lateinit var _rightStartingPointX: Point
    private lateinit var _leftStartingPointOne: Point

    private val _helpPaint = Paint()
    protected val _paintForA = Paint()
    protected val _paintForB = Paint()
    protected val _paintForC = Paint()
    private val _paintForX = Paint()
    private val _zeroPaint = Paint()

    private lateinit var _numbers: Array<TriangularNumber>
    private var _xValue: Double? = null

    protected var _minValue = 0.0
    protected var _maxValue = 0.0

    init {
        _helpPaint.strokeWidth = 5.toFloat()
        _helpPaint.alpha = 50
        _helpPaint.color = Color.GRAY
        _helpPaint.style = Paint.Style.STROKE

        _paintForA.strokeWidth = 8.toFloat()
        _paintForA.color = Color.RED
        _paintForA.style = Paint.Style.FILL_AND_STROKE

        _paintForB.set(_paintForA)
        _paintForB.color = Color.GREEN

        _paintForC.set(_paintForA)
        _paintForC.color = Color.MAGENTA

        _paintForX.set(_paintForA)
        _paintForX.color = Color.YELLOW

        _zeroPaint.set(_paintForA)
        _zeroPaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        _canvas = canvas!!

        drawAxes()
        drawNumbers()
        drawLineMarkers()
    }


    fun startingInit(numbers: Array<TriangularNumber>, xValue: Double?) {
        _numbers = numbers
        _xValue = xValue

        _minValue = numbers.minBy { it.leftBound }!!.leftBound
        _maxValue = numbers.maxBy { it.rightBound }!!.rightBound
    }

    private fun drawLineMarkers() {
        drawLineMarker(_paintForA, "A", 0)
        drawLineMarker(_paintForB, "B", 30)
        drawLineMarker(_paintForC, "C", 60)

        if (_xValue != null) {
            drawLineMarker(_paintForX, "X", 90)
        }
    }


    protected fun drawAxes() {
        _leftStartingPointX = Point.of(width / 12, height / 2 + 50)
        _rightStartingPointX = Point.of(width - width / 8, height / 2 + 50)

        drawLine(_leftStartingPointX, _rightStartingPointX)

        _leftStartingPointOne = Point.of(_leftStartingPointX.x, height / 9)
        drawLine(_leftStartingPointOne, Point.of(_rightStartingPointX.x, height / 9))

        if (_minValue <= 0 && _maxValue >= 0) {
            drawLine(Point.of(valueToPixels(0.0), _leftStartingPointX.y),
                    Point.of(valueToPixels(0.0), _leftStartingPointOne.y), _zeroPaint)
        }
    }


    private fun drawHelperLine(point: Point, value: Double, offsetY: Int) {
        drawLine(point, Point.of(point.x, height), _helpPaint)
        drawText(value.toString(), point, 20, offsetY = offsetY)
    }


    private fun drawNumbers() {
        drawTriangularNumber(_numbers[0], _paintForA)
        drawTriangularNumber(_numbers[1], _paintForB)
        drawTriangularNumber(_numbers[2], _paintForC)

        val value = _xValue
        if (value != null && value >= _minValue && value <= _maxValue ) {
            val pointTop = Point.of(valueToPixels(value), _leftStartingPointOne.y)

            drawLine(Point.of(pointTop.x, _leftStartingPointX.y),
                    pointTop, paint = _paintForX)

            drawLine(pointTop, Point.of(pointTop.x, 0), _helpPaint)
            drawText(value.toString(), pointTop, 20, -20)
        }
    }


    protected fun drawTriangularNumber(number: TriangularNumber, paint: Paint) {
        drawLine(Point.of(valueToPixels(number.leftBound), _leftStartingPointX.y ),
                Point.of(valueToPixels(number.mid), _leftStartingPointOne.y), paint)

        drawHelperLine(Point.of(valueToPixels(number.leftBound),
                _leftStartingPointX.y), number.leftBound, 100)

        drawHelperLine(Point.of(valueToPixels(number.mid), _leftStartingPointOne.y),
                number.mid, 550)

        drawLine(Point.of(valueToPixels(number.mid), _leftStartingPointOne.y ),
                Point.of(valueToPixels(number.rightBound), _leftStartingPointX.y), paint)

        drawHelperLine(Point.of(valueToPixels(number.rightBound), _leftStartingPointX.y),
                number.rightBound, 100)
    }


    protected fun valueToPixels(value: Double): Int {
        val xLength = _leftStartingPointX.lengthX(_rightStartingPointX)
        val pixelsPerValue = xLength /  (Math.abs(_maxValue) + (Math.abs(_minValue)))

        return (pixelsPerValue * value).toInt() + (pixelsPerValue * Math.abs(_minValue)).toInt() +
                _leftStartingPointX.x
    }
}