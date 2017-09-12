package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.util.Log
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.AddingTriangluarNumbersOperation
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber

internal class TriangluarNumberActivityPresenter (
        private val _view: TriangularNumberActivityView) {

    private var _result: TriangularNumber? = null
    private val _operation = AddingTriangluarNumbersOperation()

    fun findSumButtonOnClick() {
        if (!isInputValid(_view.leftBoundA, _view.midBoundA, _view.rightBoundA) ||
                !isInputValid(_view.leftBoundB, _view.midBoundB, _view.rightBoundB)) {
            _view.showErrorMessage("Numbers should not be null and a <= b <= c")
            return
        }
        val res = _operation.execute(
                TriangularNumber.of(
                        leftBound = _view.leftBoundA!!,
                        mid = _view.midBoundA!!,
                        rightBound = _view.rightBoundA!!
                ),
                TriangularNumber.of(
                        leftBound = _view.leftBoundB!!,
                        mid = _view.midBoundB!!,
                        rightBound = _view.rightBoundB!!
                ))

        _result = res

        _view.leftBoundC = res.leftBound
        _view.midBoundC = res.mid
        _view.rightBoundC = res.rightBound
        _view.enableResultButtons()
    }

    fun inputXButtonOnClick() {

    }

    fun graphButtonOnClick() {

    }

    private fun isInputValid(leftBound: Double?, mid: Double?, rightBound: Double?): Boolean {
        return leftBound != null && mid != null && rightBound != null
                && (leftBound <= mid && leftBound <= rightBound )
                && (mid <= rightBound)
    }
}