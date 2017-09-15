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

        intent.putExtra("functions", bundle)
        startActivity(intent)
    }
}


internal interface AffiliationFunctionView {

}
