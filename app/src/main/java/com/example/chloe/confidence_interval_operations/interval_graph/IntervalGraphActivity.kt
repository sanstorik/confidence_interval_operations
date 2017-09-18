package com.example.chloe.confidence_interval_operations.interval_graph

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import kotlinx.android.synthetic.main.activity_interval_graph_activity.*

class IntervalGraphActivity : AppCompatActivity() {

    private lateinit var _intervalValues: DoubleArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval_graph_activity)

        var title = "Graphical representation"
        if (intent.extras != null) {
            val bundle = intent.extras.getBundle("intervalBundle")
            _intervalValues = bundle.getDoubleArray("intervalValues")
            title = bundle.getString("operationFullInfo")

            a_interval_graph.putStartingValues(getIntervalA(), "A")

            if (!bundle.getBoolean("isOperationUnary")) {
                b_interval_graph.putStartingValues(getIntervalB(), "B")
            } else {
                b_interval_graph.disable()
            }

            result_interval_graph.putStartingValues(getResultInterval(), "Result")
        }

        setSupportActionBar(my_toolbar)
        my_toolbar.title = title
        my_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getIntervalA() = ConfidenceInterval.of(_intervalValues[0], _intervalValues[1])

    private fun getIntervalB() = ConfidenceInterval.of(_intervalValues[2], _intervalValues[3])

    private fun getResultInterval() = ConfidenceInterval.of(_intervalValues[4], _intervalValues[5])
}
