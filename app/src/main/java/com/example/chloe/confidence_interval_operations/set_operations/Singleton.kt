package com.example.chloe.confidence_interval_operations.set_operations

data class Singleton private constructor(
        val affilationValue: Double,
        val xValue: Double
) {
    companion object {
        @JvmStatic
        fun of(affilationValue: Double, xValue: Double) = Singleton(affilationValue, xValue)
    }
}