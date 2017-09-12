package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations

import com.example.chloe.confidence_interval_operations.triangular_numbers.TriangularNumber


class AddingTriangluarNumbersOperation: BinaryTriangularOperation {
    override fun execute(left: TriangularNumber, right: TriangularNumber) =
            TriangularNumber.of(
                    leftBound = left.leftBound + right.rightBound,
                    mid = left.mid + right.mid,
                    rightBound = right.leftBound + right.rightBound
            )
}
