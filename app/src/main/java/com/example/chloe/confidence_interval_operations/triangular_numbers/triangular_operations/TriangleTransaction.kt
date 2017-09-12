package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

internal class TriangleTransaction (
        val leftOperand: TriangularNumber,
        val rightOperand: TriangularNumber,
        val result: TriangularNumber,
        var xNumber: Double? = null
): Serializable {

    private fun writeObject(stream: ObjectOutputStream) {
        stream.defaultWriteObject()
    }

    private fun readObject(stream: ObjectInputStream) {
        stream.defaultReadObject()
    }
}