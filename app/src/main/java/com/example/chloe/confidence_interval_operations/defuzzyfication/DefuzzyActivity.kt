package com.example.chloe.confidence_interval_operations.defuzzyfication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.set_operations.Singleton

class DefuzzyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_defuzzy)

        Log.i("tag",
                "result = " + meanOfMaximums(arrayOf(
                        Singleton.of(0.0, 10.0),
                        Singleton.of(0.6, 20.0),
                        Singleton.of(0.8, 25.0),
                        Singleton.of(0.8, 30.0),
                        Singleton.of(0.8, 35.0),
                        Singleton.of(0.6, 37.0),
                        Singleton.of(0.6, 40.0),
                        Singleton.of(0.6, 45.0),
                        Singleton.of(0.65, 50.0),
                        Singleton.of(0.6, 57.0),
                        Singleton.of(0.4, 60.0),
                        Singleton.of(0.0, 65.0))).toString())
    }

    private fun centerOfGravity(singletons: Array<Singleton>): Double {
        var up = 0.0
        var down = 0.0

        singletons.forEach {
            up += it.xValue * it.affilationValue
            down += it.affilationValue
        }

        return up / down
    }


    private fun bisectorMethod(singletons: Array<Singleton>): Double {
        for (i in 1 until singletons.size) {
            val sumLeft = singletons.filterIndexed { index, singleton ->  index < i }
                    .sumByDouble { it.affilationValue }
            val sumRight = singletons.filterIndexed { index, singleton -> index > i  }
                    .sumByDouble { it.affilationValue }

            if (Math.abs(sumLeft - sumRight) < 0.1) {
                return singletons[i].xValue
            }
        }

        return -1.0
    }


    private fun leftMostModal(singletons: Array<Singleton>): Double {
        val max = singletons.maxBy { it.affilationValue }


        return singletons.asSequence()
                .filter { it.affilationValue == max?.affilationValue }
                .first().xValue
    }


    private fun rightMostModal(singletons: Array<Singleton>): Double {
        val max = singletons.maxBy { it.affilationValue }


        return singletons.asSequence()
                .filter { it.affilationValue == max?.affilationValue }
                .last().xValue
    }


    private fun meanOfMaximums(singletons: Array<Singleton>): Double {
        val max = singletons.maxBy { it.affilationValue }
        val arr = singletons.filter { it.affilationValue == max?.affilationValue }

        return arr.sumByDouble { it.xValue } / arr.size
    }
}
