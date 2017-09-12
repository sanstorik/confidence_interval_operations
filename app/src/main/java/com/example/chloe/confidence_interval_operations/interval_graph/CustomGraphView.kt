package com.example.chloe.confidence_interval_operations.interval_graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.example.chloe.confidence_interval_operations.R

abstract class CustomGraphView  @JvmOverloads constructor(
        context: Context,
        set: AttributeSet? = null,
        value: Int = 0)
    : View(context, set, value) {

    protected enum class Corners(var cornerIndex: Int) {
        BOTTOM_LEFT(0),
        TOP_LEFT(1),
        TOP_RIGHT(2),
        BOTTOM_RIGHT(3);
    }

    private val corners: Array<Point?> by lazy { arrayOfNulls<Point>(4) }

    protected lateinit var _canvas: Canvas
    private val _textPaint = Paint()
    private val _axisPaint = Paint()

    init {
        _textPaint.textSize = 35.toFloat()
        _textPaint.color = Color.BLACK

        _axisPaint.strokeWidth = 5.toFloat()
        _axisPaint.color = Color.BLUE
        _axisPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initSizes()
    }

    protected fun drawLine(start: Point, end: Point, paint: Paint = _axisPaint) {
        _canvas.drawLine(start.x.toFloat(), start.y.toFloat(),
                end.x.toFloat(), end.y.toFloat(), paint)
    }


    protected fun drawText(text: String, point: Point, offsetX: Int, offsetY: Int) {
        _canvas.drawText(text, (point.x + offsetX).toFloat(),
                (point.y + offsetY).toFloat(), _textPaint)
    }

    protected fun drawArrowX(point: Point) {
        val arrowRight = resources.getDrawable(R.drawable.arrow_axis_x)
        drawArrow(arrowRight, point, 30, 30)

        drawText("x", point, 20, 30)
    }


    protected fun drawArrowY(point: Point) {
        val arrow = resources.getDrawable(R.drawable.arrow_axis_y)
        drawArrow(arrow, point, 20, 20)

        drawText("y", point, -60, 20)
    }


    private fun drawArrow(arrow: Drawable, point: Point, xOffset: Int, yOffset: Int) {
        arrow.alpha = 255
        arrow.setBounds(point.x - xOffset, point.y - xOffset, point.x + xOffset, point.y + yOffset)
        arrow.draw(_canvas)
    }

    /**
     * clockwise corners
     */
    private fun initSizes() {
        corners[0] = Point.of(0, height)
        corners[1] = Point.of(0, 0)
        corners[2] = Point.of(width, 0)
        corners[3] = Point.of(width, height)
    }

    protected fun getCornerPoint(corner: Corners) = corners[corner.cornerIndex]!!
}