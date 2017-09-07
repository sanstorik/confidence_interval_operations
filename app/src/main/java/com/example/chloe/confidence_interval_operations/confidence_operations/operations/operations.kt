package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

class AddingIntervalsOperation: BinaryIntervalOperation {

    override fun execute(first: Interval, second: Interval) =
             ConfidenceInterval.of(
                     first.leftBound + second.leftBound,
                     first.rightBound + second.rightBound)

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


class DivideIntervalsOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    first.leftBound / second.rightBound,
                    first.rightBound / second.leftBound)
}


class HypothesisIntervalsOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval): Interval {
        val leftBound = getDivideSequence(first, second).min()!!
        val rightBound = getDivideSequence(first, second).max()!!

        return ConfidenceInterval.of(leftBound, rightBound)
    }

    private fun getDivideSequence(first: Interval, second: Interval): Array<Double> {
        return arrayOf(
                first.leftBound / second.leftBound,
                first.leftBound / second.rightBound,
                first.rightBound / second.leftBound,
                first.rightBound / second.rightBound
        )
    }
}

class InversionIntervalOperation: UnaryIntervalOperation {
    override fun execute(interval: Interval) =
            ConfidenceInterval.of(
                    1 / interval.rightBound,
                    1 / interval.leftBound)
}


class MultiplyOnClearNumberOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval): Interval {
        if (second::class.java != ClearNumber::class.java
                && first::class.java != ClearNumber::class.java) {
            throw IllegalArgumentException("no numbers")
        }

        return ConfidenceInterval.of(
                first.leftBound * second.leftBound,
                first.rightBound * second.rightBound)
    }
}


class DivideOnClearNumberOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval): Interval {
        if (second::class.java != ClearNumber::class.java
                && first::class.java != ClearNumber::class.java) {
            throw IllegalArgumentException("no numbers")
        }

        return ConfidenceInterval.of(
                first.leftBound / second.leftBound,
                first.rightBound / second.rightBound)
    }
}


class FindMaxIntervalOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    (first.leftBound.toInt() or first.leftBound.toInt()).toDouble(),
                    (first.rightBound.toInt() or first.rightBound.toInt()).toDouble())
}


class FindMinIntervalOperation: BinaryIntervalOperation {
    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    (first.leftBound.toInt() and first.leftBound.toInt()).toDouble(),
                    (first.rightBound.toInt() and first.rightBound.toInt()).toDouble())
}


class MultipleIntervalMultiplyOperation: MultipleIntervalOperation {
    override fun execute(intervals: Array<Interval>): Interval {
        if (intervals.size < 2) {
            throw IllegalArgumentException("size of array must be above 2")
        }

        val operation = MultiplyIntervalsOperation()
        var result = operation.execute(intervals[0], intervals[1])

        for (i in 2 until intervals.size) {
            result = operation.execute(result, intervals[i])
        }

        return result
    }
}


