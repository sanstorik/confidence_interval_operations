package com.example.chloe.confidence_interval_operations.triangular_numbers

class TriangularNumber private constructor(
        val leftBound: Double,
        val mid: Double,
        val rightBound: Double
){

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

    companion object {
        @JvmStatic
        fun of(leftBound: Double, mid: Double, rightBound: Double) =
                TriangularNumber(leftBound, mid, rightBound)
    }
}