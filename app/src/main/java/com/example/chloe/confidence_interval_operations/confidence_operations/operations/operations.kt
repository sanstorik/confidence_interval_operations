package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

public enum class OperationType {
    BINARY_OPERATION, UNARY_OPERATION, MULTIPLE_OPERATION
}

open class AddingIntervalsOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Adding interval A to interval B"

    override fun execute(first: Interval, second: Interval) =
             ConfidenceInterval.of(
                     first.leftBound + second.leftBound,
                     first.rightBound + second.rightBound)

}


class AddingClearNumberToIntervalOperation: AddingIntervalsOperation() {
    override val operationFullInfo = "Adding clear number to interval"
}


open class SubstractInvervalsOperations: BinaryIntervalOperation {

    override val operationFullInfo = "Substracting interval A from interal B"

    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    first.leftBound - second.rightBound,
                    first.rightBound - second.leftBound)
}


class SubstractClearNumberFromIntervalOperation: SubstractInvervalsOperations() {
    override val operationFullInfo = "Substracting clear number from interval"
}


class ReverseIntervalOperation: UnaryIntervalOperation {

    override val operationFullInfo = "Reflecting interval A"

    override fun execute(interval: Interval) =
            ConfidenceInterval.of(
                    -interval.rightBound,
                    -interval.leftBound)
}


class MultiplyIntervalsOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Multiplying interval A on interval B"

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

    override val operationFullInfo = "Dividing interval A on interval B"

    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    first.leftBound / second.rightBound,
                    first.rightBound / second.leftBound)
}


class HypothesisIntervalsOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Finding hypothesis of interval A and interval B"

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

    override val operationFullInfo = "Inverting interval A"

    override fun execute(interval: Interval) =
            ConfidenceInterval.of(
                    1 / interval.rightBound,
                    1 / interval.leftBound)
}


class MultiplyOnClearNumberOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Multiplying clear number on Interval"

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

    override val operationFullInfo = "Dividing interval on clear number"

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

    override val operationFullInfo = "Finding max interval between intervals A and B"

    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    (first.leftBound.toInt() or first.leftBound.toInt()).toDouble(),
                    (first.rightBound.toInt() or first.rightBound.toInt()).toDouble())
}


class FindMinIntervalOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Finding min interval between intervals A and B"

    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    (first.leftBound.toInt() and first.leftBound.toInt()).toDouble(),
                    (first.rightBound.toInt() and first.rightBound.toInt()).toDouble())
}


class MultipleIntervalMultiplyOperation: MultipleIntervalOperation {

    override val operationFullInfo = "Multiplying multiple instances of intervals"

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


