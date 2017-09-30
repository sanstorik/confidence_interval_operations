package com.example.chloe.confidence_interval_operations.confidence_operations.operations

import android.util.Log
import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval
import java.util.*

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
                    (Math.max(first.leftBound, second.leftBound)),
                    (Math.max(first.rightBound, second.rightBound)))
}


class FindMinIntervalOperation: BinaryIntervalOperation {

    override val operationFullInfo = "Finding min interval between intervals A and B"

    override fun execute(first: Interval, second: Interval) =
            ConfidenceInterval.of(
                    (Math.min(first.leftBound, second.leftBound)),
                    (Math.min(first.rightBound, second.rightBound)))
}


class MultipleIntervalMultiplyOperation: MultipleIntervalOperation {

    override val operationFullInfo = "Multiplying multiple instances of intervals"

    override fun execute(intervals: Array<Interval>): Interval {
        if (intervals.size < 2) {
            throw IllegalArgumentException("size of array must be above 2")
        }

        val result = ArrayList<Double>()
        val bitsArray = Array(
                size = Math.pow(2.0, intervals.size.toDouble()).toInt(),
                init = {
                    index -> decimalValueToBinaryArray(decimalValue = index,
                        maxCharacters = intervals.size)
                }
        )

        var value = 1.0
        for (i in 0 until bitsArray.size) {
            for (j in 0 until bitsArray[i].size) {
                value *= intervals[j].boundArray[bitsArray[i][j]]
            }

            result.add(value)
            value = 1.0
        }

        return ConfidenceInterval.of(
                leftBound = result.min()!!,
                rightBound = result.max()!!
        )
    }

    private fun decimalValueToBinaryArray(decimalValue: Int, maxCharacters: Int): IntArray {
        val array = IntArray(maxCharacters)

        val binaryValue = decimalValue.toString(2).reversed()

        binaryValue.forEachIndexed { index, c ->
            array[array.size - index - 1] = c.toString().toInt()
        }

        return array
    }
}

private fun getMultiplySequence(first: Interval, second: Interval): Array<Double> {
    return arrayOf(
            first.leftBound * second.leftBound,
            first.leftBound * second.rightBound,
            first.rightBound * second.leftBound,
            first.rightBound * second.rightBound
    )
}


