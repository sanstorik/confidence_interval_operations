package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations


class AddingTriangluarNumbersOperation: BinaryTriangularOperation {
    override fun execute(left: TriangularNumber, right: TriangularNumber) =
            TriangularNumber.of(
                    leftBound = left.leftBound + right.leftBound,
                    mid = left.mid + right.mid,
                    rightBound = left.rightBound + right.rightBound
            )
}

class SubstractingTriangularNumbersOperation: BinaryTriangularOperation {
    override fun execute(left: TriangularNumber, right: TriangularNumber) =
            TriangularNumber.of(
                    leftBound = left.leftBound - right.rightBound,
                    mid = left.mid - right.mid,
                    rightBound = left.rightBound - right.leftBound
            )
}
