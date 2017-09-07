package com.example.chloe.confidence_interval_operations.confidence_operations

class ClearNumber private constructor(val number: Double): Interval {
    override val leftBound = number;
    override val rightBound = number;

    override fun equals(other: Any?) = when {
        this === other -> true;
        javaClass != other?.javaClass -> false
        else -> number == (other as ClearNumber).number
    }

    companion object {
        @JvmStatic
        fun of(number: Double): Interval {
            return ClearNumber(number)
        }
    }
}