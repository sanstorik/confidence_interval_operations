package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.R

class TriangularNumberActivity : AppCompatActivity(), TriangularNumberActivityView {
    private val _presenter = TriangluarNumberActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangular_number)
    }
}

internal interface TriangularNumberActivityView {

}
