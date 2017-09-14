package com.example.chloe.confidence_interval_operations.triangular_numbers

import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.SubstractingTriangularNumbersOperation
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import java.util.*

class TankerFuelActivityPresenter(private val _view: TankerFuelActivityView) {
    private var _fuelLevel = 0.0
    private val _operation = SubstractingTriangularNumbersOperation()
    private val _numbers = ArrayList<TriangularNumber>()

    fun graphButtonOnClick() {
        if (_view.fuelLevel == null || _view.fuelLevel!! <= 100.0) {
            _view.showErrorMessage("Fuel level should be above 100")
            return
        }

        _fuelLevel = _view.fuelLevel!!
        makeTankerModeling()

        _view.goToGraph(_numbers.toTypedArray())
    }

    private fun makeTankerModeling() {
        val first = getRandomTriangularFuelQuery()

        val result = _operation.execute(clearNumberToTriangular(_fuelLevel), first)

        _numbers.add(first)
        _numbers.add(result)
    }

    private fun getRandomTriangularFuelQuery(): TriangularNumber {
        val random = Random()
        val currentFuelLevel = _fuelLevel.toInt()

        val leftBound = 50
        val rightBound = random.nextInt(currentFuelLevel + 1 - 51) + 51
        val mid = random.nextInt(rightBound - leftBound) + leftBound

        return TriangularNumber.of(leftBound.toDouble(), mid.toDouble(), rightBound.toDouble())
    }

    private fun clearNumberToTriangular(number: Double) =
            TriangularNumber.of(number, number, number)
}