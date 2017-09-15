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
        _numbers.clear()
        makeTankerModeling()

        _view.goToGraph(_numbers.toTypedArray())
    }

    fun getResultArray() = _numbers.toTypedArray()

    private fun makeTankerModeling() {
        val first = getRandomTriangularFuelQuery()

        var previousResult = _operation.execute(clearNumberToTriangular(_fuelLevel), first)

        _numbers.add(clearNumberToTriangular(_fuelLevel))
        _numbers.add(first)

        while (true) {
            val operation = getRandomTriangularFuelQuery()

            _numbers.add(previousResult)
            _numbers.add(operation)

            if (operation.rightBound > previousResult.leftBound) {
                break
            }

            previousResult = _operation.execute(previousResult, operation)
        }
    }

    private fun getRandomTriangularFuelQuery(): TriangularNumber {
        val fuelLevel = _fuelLevel.toInt()

        val leftBound = random(50, 50 + fuelLevel / 10)
        val rightBound = random(leftBound + fuelLevel / 10, fuelLevel / 2)
        val mid = random(leftBound, rightBound)

        return TriangularNumber.of(leftBound.toDouble(), mid.toDouble(), rightBound.toDouble())
    }

    private fun clearNumberToTriangular(number: Double) =
            TriangularNumber.of(number, number, number)

    /**
     * @return random number in range [min, max]
     */
    private fun random(min: Int, max: Int) =
            Random().nextInt(max + 1 - min) + min
}