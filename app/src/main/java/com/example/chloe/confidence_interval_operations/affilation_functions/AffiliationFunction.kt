package com.example.chloe.confidence_interval_operations.affilation_functions

import java.io.Serializable

/**
 * Base interface for all functions
 * that evaluate degree
 */
interface AffiliationFunction: Serializable {
    /**
     * Operation of finding affilation degree
     * based on current function
     * @param value value to be transformed
     * into affilation degree
     * @return affilation degree of current func
     * based on value
     */
    fun findAffiliationDegree(x: Double): Double

    fun getMinX(): Double

    fun getMaxX(): Double
}