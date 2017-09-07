package com.example.chloe.confidence_interval_operations.confidence_operations

class ConfidenceInterval private constructor(
        _leftBound: Double,
        _rightBound: Double
): Interval {
    override val leftBound = _leftBound
    override val rightBound = _rightBound

    override fun equals(other: Any?) = when {
        other === this ->  true
        other?.javaClass != javaClass -> false
        else -> leftBound == (other as ConfidenceInterval).leftBound
                && rightBound == other.rightBound
    }

    override fun hashCode() = leftBound.toInt() * 31 xor rightBound.toInt() * 31

    companion object {
        @JvmStatic
        fun of(leftBound: Double, rightBound: Double): Interval {
            return ConfidenceInterval(leftBound, rightBound)
        }
    }
}