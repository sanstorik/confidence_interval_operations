package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class TriangularNumber private constructor(
        val leftBound: Double,
        val mid: Double,
        val rightBound: Double
): Serializable {

    /**
     * Evaluates M(a)
     * @return degree of affilation in range [0;1]
     */
    fun evaluateMembershipFunction(x: Double) =
        when {
            x <= leftBound || x >= rightBound -> 0.0
            x >= leftBound && x <= mid -> (x - leftBound) / (mid - leftBound)
            x >= mid && x <= rightBound -> (rightBound - x) / (rightBound - mid)
            else -> throw IllegalStateException("membership couldn't be found")
        }

    fun evaluateLeftBranch(a: Double) = a * (mid - leftBound) + leftBound

    fun evaluateRightBranch(a: Double) = rightBound - a * (rightBound - mid)

    private fun writeObject(stream: ObjectOutputStream) {
        stream.defaultWriteObject()
    }

    private fun readObject(stream: ObjectInputStream) {
        stream.defaultReadObject()
    }

    companion object {
        @JvmStatic
        fun of(leftBound: Double, mid: Double, rightBound: Double) =
                TriangularNumber(leftBound, mid, rightBound)
    }
}