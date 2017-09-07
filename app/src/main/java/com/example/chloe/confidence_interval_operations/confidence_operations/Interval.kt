package com.example.chloe.confidence_interval_operations.confidence_operations

/**
 * Abstraction for intervals, which
 * holds bounds.
 */
interface Interval {
    val leftBound: Double;
    val rightBound: Double;
}