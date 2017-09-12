package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chloe.confidence_interval_operations.R
import kotlinx.android.synthetic.main.activity_triangular_number.*

class TriangularNumberActivity : AppCompatActivity(), TriangularNumberActivityView {
    private val _presenter = TriangluarNumberActivityPresenter(this)

    override val leftBoundA
        get() = numA_left_et.text.toString().toDoubleOrNull()
    override val midBoundA
        get()= numA_mid_et.text.toString().toDoubleOrNull()
    override val rightBoundA
        get() = numA_right_et.text.toString().toDoubleOrNull()

    override val leftBoundB
        get() = numB_left_et.text.toString().toDoubleOrNull()
    override val midBoundB
        get() = numB_mid_et.text.toString().toDoubleOrNull()
    override val rightBoundB
        get() = numB_right_et.text.toString().toDoubleOrNull()

    override var leftBoundC
        get() = numC_left_et.text.toString().toDoubleOrNull()
        set(value) {
            numC_left_et.text = value.toString()
        }

    override var midBoundC
        get() = numC_mid_et.text.toString().toDoubleOrNull()
        set(value) {
            numC_mid_et.text = value.toString()
        }

    override var rightBoundC
        get() = numC_right_et.text.toString().toDoubleOrNull()
        set(value) {
            numC_right_et.text = value.toString()
        }

    override val inputX
        get() = inputX_et.text.toString().toDoubleOrNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangular_number)

        findSum_btn.setOnClickListener { _presenter.findSumButtonOnClick() }
        graph_btn.setOnClickListener { _presenter.graphButtonOnClick() }
        inputX_btn.setOnClickListener { _presenter.inputXButtonOnClick() }
    }

    override fun showErrorMessage(msg: String) {
        AlertDialog.Builder(this)
                .setTitle("Input error")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(msg)
                .setPositiveButton("OK", { dialogInterface, _ ->  dialogInterface.cancel() })
                .show()
    }

    override fun enableResultButtons() {
        graph_btn.isEnabled = true
        inputX_btn.isEnabled = true
        inputX_et.isEnabled = true
    }

    override fun disableResultButtons() {
        graph_btn.isEnabled = false
        inputX_btn.isEnabled = false
        inputX_et.isEnabled = false
    }

}

internal interface TriangularNumberActivityView {
    val leftBoundA: Double?
    val midBoundA: Double?
    val rightBoundA: Double?

    val leftBoundB: Double?
    val midBoundB: Double?
    val rightBoundB: Double?

    var leftBoundC: Double?
    var midBoundC: Double?
    var rightBoundC: Double?

    val inputX: Double?

    fun showErrorMessage(msg: String)

    fun enableResultButtons()
    fun disableResultButtons()
}
