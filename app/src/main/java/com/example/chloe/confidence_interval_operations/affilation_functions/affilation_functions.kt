package com.example.chloe.confidence_interval_operations.affilation_functions

class TriangularAffiliationFunction(
        private val a: Double,
        private val b: Double,
        private val c: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) = when {
        x <= a || x >= c -> 0.0
        x in a..b -> (x - a) / (b - a)
        x in b..c -> (c - x) / (c - b)
        else -> throw IllegalStateException("couldn't be found")
    }

    override fun getMinX() = a

    override fun getMaxX() = c

    override val description = "Трикутна функція"
}


class TrapezoidalAffiliationFunction (
        private val a: Double,
        private val b: Double,
        private val c: Double,
        private val d: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) = when {
        x <= a || x >= d -> 0.0
        x in a..b -> (x - a) / (b - a)
        x in b..c -> 1.0
        x in c..d -> (d - x) / (d - c)
        else -> throw IllegalStateException("couldn't be found")
    }

    override fun getMinX() = a

    override fun getMaxX() = d

    override val description = "Трапецієвидна функція"
}


class GaussSymmetricAffiliationFunction(
        private val a: Double,
        private val b: Double,
        private val c: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) =
            Math.exp(
                    -( x * x - 2 * b * x + b * b ) / (2 * Math.pow(c, 2.0))
            )

    override fun getMinX() = a

    override fun getMaxX() = 20.0

    override val description = "Симметрічна гаусівська"
}



class TwoSidedGaussAffiliationFunction (
        private val a1: Double,
        private val c1: Double,
        private val c2: Double,
        private val a2: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) =
            when {
                c1 < c2 -> leftSided(x)
                else -> rightSided(x)
            }

    // c1 < c2
    private fun leftSided(x: Double) = when {
        x < c1 -> exponentFunction(x, c1, a1)
        x in c1..c2 -> 1.0
        x > c2 -> exponentFunction(x, c2, a2)
        else -> throw Exception("couldn't be found")
    }

    private fun rightSided(x: Double) = when {
        x < c1 -> exponentFunction(x, c1, a1)
        x in c1..c2 -> exponentFunction(x, c1, a1) * exponentFunction(x, c2, a2)
        x > c2 -> exponentFunction(x, c2, a2)
        else -> throw Exception("couldn't be found")
    }

    private fun exponentFunction(x: Double, c: Double, a: Double) =
            Math.exp(- Math.pow((x - c), 2.0) / ( 2 * Math.pow(a, 2.0) ))


    override fun getMinX() = -20.0

    override fun getMaxX() = 40.0

    override val description = "Двобічна гаусівська"
}


class GeneralizedAffiliationFunction (
        private val a: Double,
        private val c: Double,
        private val b: Double
): AffiliationFunction {

    override fun findAffiliationDegree(x: Double) =
            1 / (1 + Math.pow( Math.abs( (x - c) / a ), 2 * b) )

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Узагальнена дзвоноподібна"
}


class SigmoidAffiliationFunction(
        private val a: Double,
        private val c: Double
): AffiliationFunction {

    override fun findAffiliationDegree(x: Double) =
            1 / (1 + Math.exp(-a * ( x - c )))

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Сигмоїдна"
}


class SigmoidMulAffiliationFunction(
        a1: Double,
        a2: Double,
        c1: Double,
        c2: Double
): AffiliationFunction {

    private val _firstSigmoid = SigmoidAffiliationFunction(a1, c1)
    private val _secondSigmoid = SigmoidAffiliationFunction(a2, c2)

    override fun findAffiliationDegree(x: Double) =
            _firstSigmoid.findAffiliationDegree(x) * _secondSigmoid.findAffiliationDegree(x)


    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Добуток сигмоїдних функцій"
}


class SigmoidSubstrAffiliationFunction (
        a1: Double,
        a2: Double,
        c1: Double,
        c2: Double
): AffiliationFunction {

    private val _firstSigmoid = SigmoidAffiliationFunction(a1, c1)
    private val _secondSigmoid = SigmoidAffiliationFunction(a2, c2)

    override fun findAffiliationDegree(x: Double) =
            _firstSigmoid.findAffiliationDegree(x) - _secondSigmoid.findAffiliationDegree(x)


    override fun getMinX() = 0.0

    override fun getMaxX() = 40.0

    override val description = "Різниця сигмоїдних функцій"
}


class ZLookAlikeAffiliationFunction (
        private val a: Double,
        private val b: Double
) : AffiliationFunction {

    override fun findAffiliationDegree(x: Double) = when {
        x <= a -> 1.0
        x in a..b -> 0.5 + 0.5 * ( Math.cos( ((x - a) / (b - a)) * Math.PI ))
        x >= b -> 0.0
        else -> throw IllegalStateException("couldn't be found")
    }

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Z-подібна функція"
}


class SLookAlikeAffiliationFunction (
        private val a: Double,
        private val b: Double
) : AffiliationFunction {

    override fun findAffiliationDegree(x: Double) = when {
        x <= a -> 0.0
        x in a..b -> 0.5 + 0.5 * ( Math.cos( ((x - b) / (b - a)) * Math.PI ))
        x >= b -> 1.0
        else -> throw IllegalStateException("couldn't be found")
    }

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "S-подібна функція"
}


class PiLookAlikeAffiliationFunction (
        a: Double,
        b: Double,
        c: Double,
        d: Double
) : AffiliationFunction {

    private val _left = ZLookAlikeAffiliationFunction(c, d)
    private val _right = SLookAlikeAffiliationFunction(a, b)

    override fun findAffiliationDegree(x: Double) =
            _left.findAffiliationDegree(x) * _right.findAffiliationDegree(x)

    override fun getMinX() = 0.0

    override fun getMaxX() = 10.0

    override val description = "P-подібна функція"
}


class LaplasAffiliationFunction (
        private val b: Double,
        private val d: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) =
            Math.exp(- (Math.abs(x - b) / d) )

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Лапласівська функція"
}


class SquareAffiliationFunction (
        private val a: Double,
        private val b: Double
): AffiliationFunction {
    override fun findAffiliationDegree(x: Double) = when {
        squareRoot(x) < 1 -> 1 - squareRoot(x)
        else -> 0.0
    }

    private fun squareRoot(x: Double) = Math.pow( ((x - a) / b), 2.0)

    override fun getMinX() = -20.0

    override fun getMaxX() = 20.0

    override val description = "Квадратична функція"
}




