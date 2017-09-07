package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

/**
 * Abstraction for all operations with
 * two intervals, for example multiply, adding.
 */
interface BinaryIntervalOperation {
    /**
     * Executes operation between two intervals
     * @param first first operand
     * @param second second operand
     * @return result of operation as new interval
     */
    fun execute(first: Interval, second: Interval): Interval
}