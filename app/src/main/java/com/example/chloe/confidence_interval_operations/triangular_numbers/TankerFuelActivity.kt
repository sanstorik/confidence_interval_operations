package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.R
import kotlinx.android.synthetic.main.activity_tanker_fuel.*

class TankerFuelActivity : AppCompatActivity(), TankerFuelActivityView {
    override val fuelLevel: Double?
        get() = fuel_level_et.text.toString().toDoubleOrNull()

    private val _presenter by lazy { TankerFuelActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanker_fuel)
    }

    override fun showErrorMessage(msg: String) {
        AlertDialog.Builder(this)
                .setTitle("Input error")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(msg)
                .setPositiveButton("OK", { dialogInterface, _ ->  dialogInterface.cancel() })
                .show()
    }
}

interface TankerFuelActivityView {
    val fuelLevel: Double?

    fun showErrorMessage(msg: String)
}
