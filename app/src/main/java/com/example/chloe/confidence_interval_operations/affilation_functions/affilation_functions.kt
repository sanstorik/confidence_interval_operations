package com.example.chloe.confidence_interval_operations.affilation_functions

class TriangularAffilationFunction (
        private val a: Double,
        private val b: Double,
        private val c: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) = when {
        x <= a || x >= c -> 0.0
        a <= x && x <= b -> (x - a) / (b - a)
        b <= x && x <= c -> (c - x) / (c - b)
        else -> throw IllegalStateException("couln't be found")
    }

    override fun getMinX() = a

    override fun getMaxX() = c
}

class TrapezoidalAffiliationFunction (
        private val a: Double,
        private val b: Double,
        private val c: Double,
        private val d: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) = when {
        x <= a || x >= d -> 0.0
        a <= x && x <= b -> (x - a) / (b - a)
        b <= x && x <= c -> 1.0
        c <= x && x <= d -> (d - x) / (d - c)
        else -> throw IllegalStateException("couldn't be found")
    }

    override fun getMinX() = a

    override fun getMaxX() = d
}

class GaussSymetricAffiliationFunction (
        private val a: Double,
        private val b: Double,
        private val c: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) =
            Math.exp(
                    -( Math.pow(x - b, 2.0) ) / (2 * Math.pow(c, 2.0))
            )

    override fun getMinX() = a

    override fun getMaxX() = b
}

