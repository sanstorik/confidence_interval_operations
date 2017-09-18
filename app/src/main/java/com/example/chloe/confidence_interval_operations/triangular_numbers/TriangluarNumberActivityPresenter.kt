package com.example.chloe.confidence_interval_operations.triangular_numbers

import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.AddingTriangluarNumbersOperation
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangleTransaction
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber

internal class TriangluarNumberActivityPresenter (
        private val _view: TriangularNumberActivityView) {

    private var _result: TriangularNumber? = null
    private val _operation = AddingTriangluarNumbersOperation()
    private var _lastTransaction: TriangleTransaction? = null

    fun findSumButtonOnClick() {
        if (!isInputValid(_view.leftBoundA, _view.midBoundA, _view.rightBoundA) ||
                !isInputValid(_view.leftBoundB, _view.midBoundB, _view.rightBoundB)) {
            _view.showErrorMessage("Numbers should not be null and a <= b <= c")
            return
        }
        val leftOperand = TriangularNumber.of(
                leftBound = _view.leftBoundA!!,
                mid = _view.midBoundA!!,
                rightBound = _view.rightBoundA!!
        )
        val rightOperand = TriangularNumber.of(
                leftBound = _view.leftBoundB!!,
                mid = _view.midBoundB!!,
                rightBound = _view.rightBoundB!!
        )

        val res = _operation.execute(leftOperand, rightOperand)

        _result = res

        _view.leftBoundC = res.leftBound
        _view.midBoundC = res.mid
        _view.rightBoundC = res.rightBound

        _lastTransaction = TriangleTransaction(
                leftOperand = leftOperand,
                rightOperand = rightOperand,
                result = res
        )

        _view.enableResultButtons()

        onTriangularNumberAClicked()
    }

    fun inputXButtonOnClick() {
        if (_view.inputX == null || _lastTransaction == null) {
            _view.showErrorMessage("Input x is empty")
            return
        }

        _view.afflictionA = _lastTransaction?.leftOperand!!
                .evaluateMembershipFunction(_view.inputX!!)
        _view.afflictionB = _lastTransaction?.rightOperand!!
                .evaluateMembershipFunction(_view.inputX!!)
        _view.afflictionC = _lastTransaction?.result!!
                .evaluateMembershipFunction(_view.inputX!!)

        _lastTransaction?.xNumber = _view.inputX
    }

    fun graphButtonOnClick() {

    }

    fun onTriangularNumberAClicked() {
        if (_lastTransaction != null) {
            _view.resultString = getResultString(_lastTransaction!!.leftOperand)
        }
    }

    fun onTriangularNumberBClicked() {
        if (_lastTransaction != null) {
            _view.resultString = getResultString(_lastTransaction!!.rightOperand)
        }
    }

    fun onTriangularNumberCClicked() {
        if (_lastTransaction != null) {
            _view.resultString = getResultString(_lastTransaction!!.result)
        }
    }

    fun getLastTransaction() = _lastTransaction



    private fun getResultString(number: TriangularNumber): String {
        return String.format("mA(X) = \n\t 0, when x <= %.2f or x >= %.2f \n" +
                "\t(x - %.2f) / (%.2f - %.2f), when %.2f <= x <= %.2f \n" +
                "\t(%.2f - x) / (%.2f - %.2f), when %.2f <= x <= %.2f \n",
                number.leftBound, number.rightBound, number.leftBound,
                number.mid, number.leftBound, number.leftBound, number.mid,
                number.rightBound, number.rightBound, number.mid,
                number.mid, number.rightBound)
    }

    private fun isInputValid(leftBound: Double?, mid: Double?, rightBound: Double?): Boolean {
        return leftBound != null && mid != null && rightBound != null
                && (leftBound <= mid && leftBound <= rightBound )
                && (mid <= rightBound)
    }
}