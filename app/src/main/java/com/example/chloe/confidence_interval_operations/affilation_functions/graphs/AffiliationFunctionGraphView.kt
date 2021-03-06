package com.example.chloe.confidence_interval_operations.affilation_functions.graphs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.example.chloe.confidence_interval_operations.affilation_functions.AffiliationFunction
import com.example.chloe.confidence_interval_operations.affilation_functions.ClearSetAffiliationFunction
import com.example.chloe.confidence_interval_operations.affilation_functions.EntropyHAffiliationFunction
import com.example.chloe.confidence_interval_operations.affilation_functions.PiEntropyAffiliationFunction
import com.example.chloe.confidence_interval_operations.interval_graph.CustomGraphView
import com.example.chloe.confidence_interval_operations.interval_graph.Point
import com.example.chloe.confidence_interval_operations.set_operations.CommonSetOperation
import com.example.chloe.confidence_interval_operations.set_operations.Singleton

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

    private var _secondFunction: AffiliationFunction? = null
    private var _steps = 70
    private var _drawUnclearIndex = false
    private lateinit var _clearSet: ClearSetAffiliationFunction

    private var _drawEntropy = false

    private val _funcPaint = Paint()
    private val _secondFuncPaint = Paint()
    private val _euclidDistancePaint = Paint()
    private val _hamilgtonDistancePaint = Paint()
    private val _clearSetPaint = Paint()

    private var _setOperation: CommonSetOperation? = null
    private var _setOperationStep = 0.0
    private var _roundedGraph = false

    private var _setA: Array<Singleton>? = null
    private var _setB: Array<Singleton>? = null
    private var _setC: Array<Singleton>? = null

    init {
        _funcPaint.strokeWidth = 8.toFloat()
        _funcPaint.color = Color.BLACK
        _funcPaint.style = Paint.Style.FILL_AND_STROKE

        _secondFuncPaint.set(_funcPaint)
        _secondFuncPaint.color = Color.MAGENTA

        _euclidDistancePaint.set(_funcPaint)
        _euclidDistancePaint.color = Color.GREEN
        _euclidDistancePaint.alpha = 200

        _hamilgtonDistancePaint.set(_funcPaint)
        _hamilgtonDistancePaint.color = Color.RED
        _hamilgtonDistancePaint.alpha = 200

        _clearSetPaint.set(_funcPaint)
        _clearSetPaint.color = Color.RED
        _clearSetPaint.alpha = 100
    }

    override fun onDraw(canvas: Canvas?) {
        _canvas = canvas!!

        drawAxes()

        if (_setOperation == null) {
            drawFunction(_function, _funcPaint)
        }

        if (_setOperation != null && _secondFunction != null) {
            drawSingletons(_setA!!, _hamilgtonDistancePaint)
            drawSingletons(_setB!!, _euclidDistancePaint)
            drawSingletons(_setC!!, _funcPaint)

        } else if (_secondFunction != null) {
            drawFunction(_secondFunction!!, _secondFuncPaint)

            val hamDistance = drawHamilgtonDistance(_hamilgtonDistancePaint, _steps)
            val euclidDistance = drawEuclidDistance(_euclidDistancePaint, _steps)

            drawLineMarker(_funcPaint, "A", offsetY = 0)
            drawLineMarker(_secondFuncPaint, "B", offsetY = 30)
            drawLineMarker(_hamilgtonDistancePaint, "Haming", offsetY = 120, textOffsetY = -20)
            drawLineMarker(_euclidDistancePaint, "Euclid", offsetY = 175, textOffsetY = -20)

            drawText("Haming = $hamDistance", getCornerPoint(Corners.TOP_RIGHT),
                    offsetY = 25, offsetX = -340)
            drawText("Euclid  = $euclidDistance", getCornerPoint(Corners.TOP_RIGHT),
                    offsetY = 65, offsetX = -340)

        } else if (_drawUnclearIndex) {
            _clearSet = ClearSetAffiliationFunction(_function)

            drawFunction(_clearSet, _clearSetPaint)

            val hamDistance = calculateHamilgtonDistance(_clearSet, _steps)
            val euclidDistance = calculateEuclidDistance(_clearSet, _steps)

            drawText("Linear index = ${ (2 / _steps.toDouble()) * hamDistance }",
                    getCornerPoint(Corners.TOP_RIGHT),
                    offsetY = 25, offsetX = -350)
            drawText("Square index = ${ ((2 / Math.sqrt(_steps.toDouble()))
                    * euclidDistance) }", getCornerPoint(Corners.TOP_RIGHT),
                    offsetY = 65, offsetX = -350)

        } else if (_drawEntropy) {
            val entropy  = calculateEntropy(_function, _steps)
            drawText("Entropy =  $entropy", getCornerPoint(Corners.TOP_RIGHT),
                    offsetY = 65, offsetX = -350)

            drawFunction(PiEntropyAffiliationFunction(_function,
                    calculateAffiliationSum(_function, _steps)), _euclidDistancePaint)

            drawFunction(EntropyHAffiliationFunction(_function,
                    calculateAffiliationSum(_function, _steps)), _hamilgtonDistancePaint)
        }
    }


    fun startingInit(function: AffiliationFunction,
                     secondFunction: AffiliationFunction? = null,
                     steps: Int = 0,
                     drawUnclearIndex: Boolean,
                     drawEntropy: Boolean,
                     setOperation: CommonSetOperation?,
                     roundedGraph: Boolean) {
        _function = function
        _drawUnclearIndex = drawUnclearIndex
        _drawEntropy = drawEntropy
        _setOperation = setOperation

        _min = _function.getMinX()
        _max = _function.getMaxX()


        _steps = steps

        if (secondFunction != null) {
            _secondFunction = secondFunction

            _min = Math.min(_min, _secondFunction!!.getMinX())
            _max = Math.max(_max, _secondFunction!!.getMaxX())

            if (_setOperation != null) {
                _setOperationStep = Math.min(
                        _function.getMaxX() - _function.getMinX(),
                        _secondFunction!!.getMaxX() - _secondFunction!!.getMinX()) / _steps

                _setA = getFunctionSingletons(_function)
                _setB = getFunctionSingletons(_secondFunction!!)
                _setC = _setOperation!!.execute(_setA!!, _setB!!)
                _roundedGraph = roundedGraph

                _min = Math.min(_min, _setC!![0].xValue)
                _max = Math.max(_max, _setC!![_setC!!.size - 1].xValue)
            }
        }
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


    private fun drawFunction(function: AffiliationFunction, paint: Paint = _funcPaint) {
        var value = _min
        val step = getFunctionLengthX() / 1000

        var lastPoint = Point.of(x = valueToPixels(value),
                y = yValueToPixels(function.findAffiliationDegree(value)))

        while(value <= _max) {
            val point = Point.of(x = valueToPixels(value),
                    y = yValueToPixels(function.findAffiliationDegree(value)))

            drawLine(lastPoint, point, paint)

            value += step
            lastPoint = point
        }
    }


    private fun calculateDistance(distanceCalc: (_value: Double) -> Double, steps: Int): Double {
        var value = _min
        val step = getFunctionLengthX() / steps
        var distance = 0.0

        while(value <= _max) {
            distance += distanceCalc(value)
            value += step
        }

        return distance
    }


    private fun calculateAffiliationSum(function: AffiliationFunction, steps: Int) =
            calculateDistance({ function.findAffiliationDegree(it) }, steps)


    private fun calculatePiAffiliationSum(function: PiEntropyAffiliationFunction, steps: Int) =
            calculateDistance({ function.findAffiliationDegree(it)  *
                   Math.log( function.findAffiliationDegree(it)) }, steps)


    private fun calculateHamilgtonDistance(function: AffiliationFunction, steps: Int) =
            calculateDistance({ hamiltonDistance(_function, function, it)}, steps)


    private fun calculateEuclidDistance(function: AffiliationFunction, steps: Int) =
            calculateDistance({ euclidDistance(_function, function, it)}, steps)


    private fun drawHamilgtonDistance(paint: Paint, steps: Int): Double =
         drawDistance(paint, steps,
                { hamiltonDistance(_function, _secondFunction!!, it) } )


    private fun drawEuclidDistance(paint: Paint, steps: Int) =
            Math.sqrt(drawDistance(paint, steps,
                    { euclidDistance(_function, _secondFunction!!, it) }) )


    private fun drawDistance(paint: Paint, steps: Int,
                             distanceCalc: (_value: Double) -> Double): Double {
        var value = _min
        val step = getFunctionLengthX() / steps
        var distance = 0.0

        var lastPoint = Point.of(x = valueToPixels(value),
                y = yValueToPixels(distanceCalc(value)))

        while(value <= _max) {
            val point = Point.of(x = valueToPixels(value),
                    y = yValueToPixels(distanceCalc(value)))

            drawLine(lastPoint, point, paint)

            value += step
            lastPoint = point
            distance += Math.abs(distanceCalc(value))
        }

        return distance
    }

    private fun hamiltonDistance(firstFunc: AffiliationFunction,
                                 secondFunc: AffiliationFunction,
                                 xValue: Double): Double {
        return Math.abs(firstFunc.findAffiliationDegree(xValue)
                - secondFunc.findAffiliationDegree(xValue))
    }

    private fun euclidDistance(firstFunc: AffiliationFunction,
                               secondFunc: AffiliationFunction,
                               xValue: Double): Double {
        return Math.pow((firstFunc.findAffiliationDegree(xValue) -
                secondFunc.findAffiliationDegree(xValue)), 2.0)
    }

    private fun calculateEntropy(function: AffiliationFunction, steps: Int) =
            (1 / Math.log(steps.toDouble())) *
            calculatePiAffiliationSum(
                    function = PiEntropyAffiliationFunction(function, calculateAffiliationSum(function, steps)),
                    steps = steps)


    private fun getFunctionSingletons(function: AffiliationFunction): Array<Singleton> {
        val list = ArrayList<Singleton>()
        var value = function.getMinX()

        val stepsCount = (( function.getMaxX() - function.getMinX() ) / _setOperationStep).toInt()

        for (i in 0..stepsCount) {
            list.add(Singleton.of(
                    affilationValue = function.findAffiliationDegree(value),
                    xValue = value
            ))

            value += _setOperationStep
        }

        return list.toTypedArray()
    }

    private fun drawSingletons(singletos: Array<Singleton>, paint: Paint) {
        var lastPoint = Point.of(
                x = valueToPixels(singletos[0].xValue),
                y = yValueToPixels(singletos[0].affilationValue)
        )

        var point: Point

        for(i in 1 until singletos.size) {
            point = Point.of(valueToPixels(singletos[i].xValue),
                    yValueToPixels(singletos[i].affilationValue))

            drawLine(lastPoint, point, paint)
            lastPoint = point

            if (i < singletos.size - 1 && !_roundedGraph
                    && singletos[i].affilationValue != singletos[i + 1].affilationValue) {
                val middlePoint = Point.of(valueToPixels(singletos[i].xValue),
                        yValueToPixels(singletos[i + 1].affilationValue))

                drawLine(lastPoint, middlePoint, paint)
                lastPoint = middlePoint
            }
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

        return (-value * length).toInt() + _leftPointX.y
    }
}