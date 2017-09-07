package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

/**
 * Abstraction for operations that
 * get as params array of intervals.
 */
interface MultipleIntervalOperation {
    /**
     * Executes operation between two intervals
     * @param intervals array of operands
     * @return result of operation as new interval
     */
    fun execute(intervals: Array<Interval>): Interval
}