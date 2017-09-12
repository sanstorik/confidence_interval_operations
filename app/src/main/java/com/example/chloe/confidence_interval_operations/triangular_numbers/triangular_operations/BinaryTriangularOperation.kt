package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations

/**
 * Abstraction for operations with
 * triangular numbers with two operands
 */
interface BinaryTriangularOperation {
    /**
     * @param left first operand
     * @param right second operand
     * @return new triangular number after operation
     */
    fun execute(left: TriangularNumber, right: TriangularNumber): TriangularNumber
}