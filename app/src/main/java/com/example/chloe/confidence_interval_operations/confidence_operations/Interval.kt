package com.example.chloe.confidence_interval_operations.confidence_operations

/**
 * Abstraction for intervals, which
 * holds bounds.
 */
interface Interval {
    val leftBound: Double;
    val rightBound: Double;

    /**
     * Easy way to loop through
     * bounds. Left bound stands with index 0
     * and right bound with index 1
     */
    val boundArray: DoubleArray
}