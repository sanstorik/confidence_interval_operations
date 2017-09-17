package com.example.chloe.confidence_interval_operations.set_operations

import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

abstract class CommonSetOperation: Serializable {

    protected abstract var _operation: (Double, Double) -> Double

    fun execute(firstOperand: Array<Singleton>, secondOperand: Array<Singleton>):
            Array<Singleton> {

        if (firstOperand.isEmpty() || secondOperand.isEmpty()) {
            return arrayOf()
        }

        val sums = getAllPossibleSums(firstOperand, secondOperand)

        return getMaxSingletonsOfArray(sums)
    }


    private fun getAllPossibleSums(firstOperand: Array<Singleton>, secondOperand: Array<Singleton>):
            Array<Singleton> {
        val result = ArrayList<Singleton>()

        for (i in 0 until firstOperand.size) {
            for (j in 0 until secondOperand.size) {
                result.add( Singleton.of(
                        affilationValue = Math.min(firstOperand[i].affilationValue,
                                secondOperand[j].affilationValue),
                        xValue = _operation(firstOperand[i].xValue, secondOperand[j].xValue))
                )
            }
        }

        return result.toTypedArray()
    }


    private fun getMaxSingletonsOfArray(array: Array<Singleton>): Array<Singleton> {
        val hashSet = HashMap<Double, Singleton>()

        for (i in 0 until array.size) {
            if (hashSet.containsKey(array[i].xValue)) {
                if (hashSet[array[i].xValue]!!.affilationValue < array[i].affilationValue) {
                    hashSet[array[i].xValue] = array[i]
                }
            } else {
                hashSet.put(array[i].xValue, array[i])
            }
        }

        val arr = hashSet.values.toTypedArray()
        arr.sortBy { it.xValue }

        return arr
    }
}


class AddingSetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> one + two}
}


class SubstractingSetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> one - two}
}


class DividingSetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> one / two}
}


class MultiplySetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> one * two}
}


class MaxSetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> Math.max(one, two)}
}


class MinSetOperation: CommonSetOperation() {
    override var _operation: (Double, Double) -> Double = {one, two -> Math.min(one, two)}
}