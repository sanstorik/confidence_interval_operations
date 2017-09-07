package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

class AddingIntervalsOperation: BinaryIntervalOperation {

    override fun execute(first: Interval, second: Interval) =
             ConfidenceInterval.of(
                     first.leftBound + second.leftBound,
                     second.leftBound + second.rightBound)

}

class SubstractInvervalsOperations: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    first.leftBound - second.rightBound,
                    first.rightBound - second.leftBound)
}

class ReverseIntervalOperation: UnaryIntervalOperation {
    override fun execute(interval: Interval) =
            ConfidenceInterval.of(
                    -interval.rightBound,
                    -interval.leftBound)
}

class MultiplyIntervalsOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval): Interval {
        val leftBound = getMultiplySequence(first, second).min()!!
        val rightBound = getMultiplySequence(first, second).max()!!

        return ConfidenceInterval.of(leftBound, rightBound)
    }

    private fun getMultiplySequence(first: Interval, second: Interval): Array<Double> {
        return arrayOf(
                first.leftBound * second.leftBound,
                first.leftBound * second.rightBound,
                first.rightBound * second.leftBound,
                first.rightBound * second.rightBound
        )
    }
}