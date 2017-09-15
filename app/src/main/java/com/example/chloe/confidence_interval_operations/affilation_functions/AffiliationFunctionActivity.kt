package com.example.chloe.confidence_interval_operations.affilation_functions

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.affilation_functions.graphs.AffiliationFunctionGraphActivity
import kotlinx.android.synthetic.main.activity_affiliation_function.*

class AffiliationFunctionActivity : AppCompatActivity(), AffiliationFunctionView {
    private val _presenter by lazy { AffiliationFunctionActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_function)

        button.setOnClickListener { goToGraphActivity() }
    }

    fun goToGraphActivity() {
        val intent = Intent(this, AffiliationFunctionGraphActivity::class.java)
        val bundle = Bundle()
        bundle.putDoubleArray("triangular", getTriangularArray())
        bundle.putDoubleArray("twoSided", getTwoSidedGausArray())
        bundle.putDoubleArray("generalized", getGeneralizedArray())

        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    private fun getTriangularArray() =
            doubleArrayOf(numA_left_et.text.toString().toDouble(),
                    c1_et.text.toString().toDouble(),
                    c2_et.text.toString().toDouble())


    private fun getTwoSidedGausArray() =
            doubleArrayOf(a1_et.text.toString().toDouble(),
                    a2_et.text.toString().toDouble(),
                    c1_et.toString().toDouble(),
                    c2_et.toString().toDouble())

    private fun getGeneralizedArray() =
            doubleArrayOf(a_et.text.toString().toDouble(),
                    b_et.text.toString().toDouble(),
                    c_et.text.toString().toDouble())
}


internal interface AffiliationFunctionView {

}
