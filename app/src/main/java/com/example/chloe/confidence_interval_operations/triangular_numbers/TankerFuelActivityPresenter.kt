package com.example.chloe.confidence_interval_operations.triangular_numbers

import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import java.util.*

class TankerFuelActivityPresenter(private val _view: TankerFuelActivityView) {

    fun graphButtonOnClick() {
        if (_view.fuelLevel == null || _view.fuelLevel!! <= 100.0) {
            _view.showErrorMessage("Fuel level should be above 100")
            return
        }


    }

    private fun makeTankerModeling() {

    }

    private fun getRandomTriangularFuelQuery(): TriangularNumber {
        val random = Random()
        val currentFuelLevel = _view.fuelLevel!!.toInt()

        val leftBound = 50
        val rightBound = random.nextInt(currentFuelLevel + 1 - 51) + 51
        val mid = random.nextInt(rightBound - leftBound) + leftBound

        return TriangularNumber.of(leftBound.toDouble(), mid.toDouble(), rightBound.toDouble())
    }
}