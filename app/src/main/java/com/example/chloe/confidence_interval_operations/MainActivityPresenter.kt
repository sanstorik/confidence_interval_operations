package com.example.chloe.confidence_interval_operations

import android.util.Log
import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.*

class MainActivityPresenter (private val _view: MainActivityView) {
    private var _binaryOperation: BinaryIntervalOperation? = null
    private var _unaryOperation: UnaryIntervalOperation? = null
    private var _currentOperation: OperationType? = null

    private var _result: Interval? = null

    public fun setBinaryOperation(operation: BinaryIntervalOperation) {
        _binaryOperation = operation
        _currentOperation = OperationType.BINARY_OPERATION
    }

    public fun setUnaryOperation(operation: UnaryIntervalOperation) {
        _unaryOperation = operation
        _currentOperation = OperationType.UNARY_OPERATION
    }

    public fun executeOnClick() {
        when(_currentOperation) {
            OperationType.BINARY_OPERATION -> {
                if (!areIntervalsProperlyFilled()) {
                    _view.showErrorMessage("Fill intervals in form 0;0")
                    return
                }

                var leftOperand = transformStringToInterval(_view.intervalA)
                val rightOperand = when (_binaryOperation!!::class.java) {
                    MultiplyOnClearNumberOperation::class.java -> {
                        ClearNumber.of(_view.multiplyNumberInput)
                    }
                    DivideOnClearNumberOperation::class.java -> {
                        if (_view.divideNumberInput == 0.0) {
                            _view.showErrorMessage("Number is zero")
                        }
                        leftOperand = transformStringToInterval(_view.intervalB)
                        ClearNumber.of(_view.divideNumberInput)
                    }
                    AddingClearNumberToIntervalOperation::class.java -> {
                        ClearNumber.of(_view.addNumberInput)
                    }
                    SubstractClearNumberFromIntervalOperation::class.java -> {
                        leftOperand = transformStringToInterval(_view.intervalB)
                        ClearNumber.of(_view.substractNumberInput)
                    }
                    DivideIntervalsOperation::class.java -> {
                        val right = transformStringToInterval(_view.intervalB)
                        if (right.leftBound == 0.0 || right.rightBound == 0.0) {
                            _view.showErrorMessage("Interval B has zero. Dividing is not allowed.")
                            return
                        }
                        right
                    }
                    HypothesisIntervalsOperation::class.java -> {
                        val right = transformStringToInterval(_view.intervalB)
                        if (right.leftBound == 0.0 || right.rightBound == 0.0
                                || leftOperand.leftBound == 0.0 || leftOperand.rightBound == 0.0) {
                            _view.showErrorMessage(
                                    "Interval A or B has zero. Dividing is not allowed.")
                            return
                        }
                        right
                    }
                    else -> transformStringToInterval(_view.intervalB)
                }

                _result = _binaryOperation?.execute(leftOperand, rightOperand)
                _view.enableResultButtons()
            }

            OperationType.UNARY_OPERATION -> {
                if (!areIntervalsProperlyFilled()) {
                    _view.showErrorMessage("Fill intervals in form 0;0")
                    return
                }

                val operand = when (_unaryOperation!!::class.java) {
                    InversionIntervalOperation::class.java -> {
                        val interval = transformStringToInterval(_view.intervalB)
                        if (interval.leftBound == 0.0 || interval.rightBound == 0.0) {
                            _view.showErrorMessage("interval bound should not be zero")
                        }
                        transformStringToInterval(_view.intervalB)
                    }
                    ReverseIntervalOperation::class.java -> {
                        transformStringToInterval(_view.intervalA)
                    }
                    else -> throw IllegalStateException("no such unary operation")
                }

                _result = _unaryOperation?.execute(operand)
                _view.enableResultButtons()
            }
            else -> { /*empty*/ }
        }
    }

    public fun resultToA() {
        val result = _result
        if (result != null) {
            _view.intervalA = transformIntervalToString(result)

            _result = null
            _view.disableResultButtons()
        }
    }

    public fun resultToB() {
        val result = _result
        if (result != null) {
            _view.intervalB = transformIntervalToString(result)

            _result = null
            _view.disableResultButtons()
        }
    }

    public fun transformDoubleArrayToResult(array: DoubleArray) {
        val arr = transformDoubleArrayToIntervals(array)
        if (arr.size >= 2) {
            _result = MultipleIntervalMultiplyOperation().execute(arr)
            _view.enableResultButtons()
        }
    }

    private fun transformDoubleArrayToIntervals(array: DoubleArray): Array<Interval> {
        val intervalArray = arrayListOf<Interval>()

        array.forEachIndexed { index, _ ->
            if (index % 2 == 0) {
                intervalArray.add(ConfidenceInterval.of(
                        leftBound = array[index],
                        rightBound = array[index + 1]
                ))
            }
        }

        return intervalArray.toTypedArray()
    }


    /**
     * This method operates as input is valid,
     * in form of <number>;<number>
     */
    private fun transformStringToInterval(string: String): Interval {
        val arr = string.split(';')

        return ConfidenceInterval.of(arr[0].toDouble(), arr[1].toDouble())
    }

    private fun transformIntervalToString(interval: Interval) =
            String.format("%.2f;%.2f", interval.leftBound, interval.rightBound)

    private fun areIntervalsProperlyFilled() =
            intervalIsProperlyFilled(_view.intervalA)
                && intervalIsProperlyFilled(_view.intervalB)


    private fun intervalIsProperlyFilled(interval: String): Boolean {
        val arr = interval.split(';')

        return arr.size == 2 && arr[0].toDoubleOrNull() != null && arr[1].toDoubleOrNull() != null
    }
}