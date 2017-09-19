package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangleTransaction
import kotlinx.android.synthetic.main.activity_triangular_number_graph.*

class TriangularNumberGraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangular_number_graph)

        val transaction: TriangleTransaction
        if (intent.extras != null) {
            if (intent.extras.getBundle("transaction") != null) {
                transaction = intent.extras.getBundle("transaction")
                        .getSerializable("transaction") as TriangleTransaction

                view.startingInit(
                        numbers = arrayOf(
                                transaction.leftOperand,
                                transaction.rightOperand,
                                transaction.result
                        ), xValue = transaction.xNumber
                )
            }
        }
    }
}
