package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph.TankerGraphActivity
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import kotlinx.android.synthetic.main.activity_tanker_fuel.*

class TankerFuelActivity : AppCompatActivity(), TankerFuelActivityView {
    override val fuelLevel: Double?
        get() = fuel_level_et.text.toString().toDoubleOrNull()

    private val _presenter by lazy { TankerFuelActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanker_fuel)

        graph_btn.setOnClickListener { _presenter.graphButtonOnClick() }
    }

    override fun showErrorMessage(msg: String) {
        AlertDialog.Builder(this)
                .setTitle("Input error")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(msg)
                .setPositiveButton("OK", { dialogInterface, _ ->  dialogInterface.cancel() })
                .show()
    }

    override fun goToGraph(list: Array<TriangularNumber>) {
        val intent = Intent(this, TankerGraphActivity::class.java)
        val bundle = Bundle()

        bundle.putSerializable("arr", _presenter.getResultArray())
        intent.putExtra("arr", bundle)

        startActivity(intent)
    }
}

interface TankerFuelActivityView {
    val fuelLevel: Double?

    fun showErrorMessage(msg: String)

    fun goToGraph(list: Array<TriangularNumber>)
}
