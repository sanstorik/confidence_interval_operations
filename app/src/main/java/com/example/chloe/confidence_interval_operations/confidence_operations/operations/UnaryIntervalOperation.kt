package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

/**
 * Abstraction on interval operations
 * that execute it on one interval.
 */
interface UnaryIntervalOperation {
    /**
     * Executes operation on the interval
     * @param interval operand
     * @return result of operation as new interval
     */
    fun execute(interval: Interval): Interval
}