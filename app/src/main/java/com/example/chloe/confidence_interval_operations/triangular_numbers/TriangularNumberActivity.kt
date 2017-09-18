package com.example.chloe.confidence_interval_operations.triangular_numbers

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph.TriangularNumberGraphActivity
import kotlinx.android.synthetic.main.activity_triangular_number.*

class TriangularNumberActivity : AppCompatActivity(), TriangularNumberActivityView {
    private val _presenter = TriangluarNumberActivityPresenter(this)

    override val leftBoundA
        get() = numA_left_et.text.toString().toDoubleOrNull()
    override val midBoundA
        get()= c1_et.text.toString().toDoubleOrNull()
    override val rightBoundA
        get() = c2_et.text.toString().toDoubleOrNull()

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

    override var afflictionA
        get() = affilationValA_et.text.toString().toDoubleOrNull()
        set(value) {
            affilationValA_et.text = value.toString()
        }

    override var afflictionB
        get() = affilationValB_et.text.toString().toDoubleOrNull()
        set(value) {
            affilationValB_et.text = value.toString()
        }

    override var afflictionC
        get() = affilationValC_et.text.toString().toDoubleOrNull()
        set(value) {
            affilationValC_et.text = value.toString()
        }

    override var resultString
        get() = resultM_tv.text.toString()
        set(value) {
                resultM_tv.text = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_triangular_number)

        findSum_btn.setOnClickListener { _presenter.findSumButtonOnClick() }
        graph_btn.setOnClickListener { goToGraphActivity() }
        inputX_btn.setOnClickListener { _presenter.inputXButtonOnClick() }

        radioA.setOnClickListener { _presenter.onTriangularNumberAClicked() }
        radioB.setOnClickListener { _presenter.onTriangularNumberBClicked() }
        radioC.setOnClickListener { _presenter.onTriangularNumberCClicked() }
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

        radioB.isEnabled = true
        radioC.isEnabled = true
    }

    override fun disableResultButtons() {
        graph_btn.isEnabled = false
        inputX_btn.isEnabled = false
        inputX_et.isEnabled = false
    }

    private fun goToGraphActivity() {
        val intent = Intent(this, TriangularNumberGraphActivity::class.java)

        val bundle = Bundle()
        if (_presenter.getLastTransaction() != null) {
            bundle.putSerializable("transaction", _presenter.getLastTransaction())
            intent.putExtra("transaction", bundle)
        }

        startActivity(intent)
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

    var afflictionA: Double?
    var afflictionB: Double?
    var afflictionC: Double?

    var resultString: String

    fun showErrorMessage(msg: String)

    fun enableResultButtons()
    fun disableResultButtons()
}
