package com.example.chloe.confidence_interval_operations

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chloe.confidence_interval_operations.affilation_functions.AffiliationFunctionActivity
import com.example.chloe.confidence_interval_operations.triangular_numbers.TankerFuelActivity
import com.example.chloe.confidence_interval_operations.triangular_numbers.TriangularNumberActivity
import kotlinx.android.synthetic.main.activity_operation_chooser.*

class OperationChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_chooser)

        interval_btn.setOnClickListener { openIntervalActivity() }
        triangular_btn.setOnClickListener { openTriangularActivity() }
        tanker_btn.setOnClickListener { openTankerActivity() }
        functions_btn.setOnClickListener { openFunctionsActivity() }
    }

    private fun openIntervalActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openTriangularActivity() {
        startActivity(Intent(this, TriangularNumberActivity::class.java))
    }

    private fun openTankerActivity() {
        startActivity(Intent(this, TankerFuelActivity::class.java))
    }

    private fun openFunctionsActivity() {
        startActivity(Intent(this, AffiliationFunctionActivity::class.java))
    }
}
