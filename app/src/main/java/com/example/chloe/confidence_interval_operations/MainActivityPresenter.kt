package com.example.chloe.confidence_interval_operations

import android.util.Log
import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.*

class MainActivityPresenter (private val _view: MainActivityView) {
    private var _binaryOperation: BinaryIntervalOperation? = null
    private var _unaryOperation: UnaryIntervalOperation? = null
    private var _multipleOperation: MultipleIntervalOperation? = null
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

    public fun setMultipleOperation(operation: MultipleIntervalOperation) {
        _multipleOperation = operation
        _currentOperation = OperationType.MULTIPLE_OPERATION
    }

    public fun executeOnClick() {
        when(_currentOperation) {
            OperationType.BINARY_OPERATION -> {
                var leftOperand = transformStringToInterval(_view.intervalA)
                var rightOperand = transformStringToInterval(_view.intervalB)

                if (_binaryOperation!!::class.java == MultiplyOnClearNumberOperation::class.java) {
                    rightOperand = ClearNumber.of(_view.multiplyNumberInput)
                } else if (_binaryOperation!!::class.java == DivideOnClearNumberOperation::class.java) {
                    rightOperand = ClearNumber.of(_view.divideNumberInput)
                }

                _result = _binaryOperation?.execute(leftOperand, rightOperand)
            }
            OperationType.UNARY_OPERATION -> {

            }
            OperationType.MULTIPLE_OPERATION -> {

            }
            null -> { /*empty*/ }
        }
    }

    public fun resultToA() {
        val result = _result
        if (result != null) {
            _view.intervalA = transformIntervalToString(result)
            _result = null
        }
    }

    public fun resultToB() {
        val result = _result
        if (result != null) {
            _view.intervalB = transformIntervalToString(result)
            _result = null
        }
    }


    /**
     * This method operates as input is valid,
     * in form of <number>;<number>
     */
    private fun transformStringToInterval(string: String): Interval {
        val arr = string.split(';')

        return ConfidenceInterval.of(arr[0].toDouble(), arr[1].toDouble())
    }

    private fun transformIntervalToString(interval: Interval) = "${interval.leftBound};${interval.rightBound}"
}