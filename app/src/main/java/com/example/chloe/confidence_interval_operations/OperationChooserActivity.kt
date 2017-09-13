package com.example.chloe.confidence_interval_operations

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.triangular_numbers.TriangularNumberActivity
import kotlinx.android.synthetic.main.activity_operation_chooser.*

class OperationChooserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operation_chooser)

        interval_chooser_btn.setOnClickListener { openIntervalActivity() }
        triangular_btn.setOnClickListener { openTriangularActivity() }
    }

    private fun openIntervalActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun openTriangularActivity() {
        startActivity(Intent(this, TriangularNumberActivity::class.java))
    }
}
