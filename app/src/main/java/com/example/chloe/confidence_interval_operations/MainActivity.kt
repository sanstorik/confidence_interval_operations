package com.example.chloe.confidence_interval_operations

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.chloe.confidence_interval_operations.confidence_operations.ClearNumber
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.AddingIntervalsOperation
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.InversionIntervalOperation
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.MultiplyOnClearNumberOperation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("tag", InversionIntervalOperation().execute(ConfidenceInterval.of(10.0,10.0)).toString())
        Log.i("tag", AddingIntervalsOperation().execute(
                ConfidenceInterval.of(10.0, 10.0),
                ConfidenceInterval.of(5.0, 5.0)
        ).toString())

        Log.i("tag", MultiplyOnClearNumberOperation().execute(ConfidenceInterval.of(5.0, 5.0), ClearNumber.of(4.0)).toString())
    }
}
