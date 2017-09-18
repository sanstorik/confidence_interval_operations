package com.example.chloe.confidence_interval_operations.affilation_functions

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.affilation_functions.graphs.AffiliationFunctionGraphActivity
import com.example.chloe.confidence_interval_operations.set_operations.*
import kotlinx.android.synthetic.main.activity_affiliation_function.*

class AffiliationFunctionActivity : AppCompatActivity() {
    private var _setOperation: CommonSetOperation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_function)

        button.setOnClickListener { goToGraphActivity() }
        operation_spinner.adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                arrayOf("None", "Adding", "Substracting", "Dividing", "Multiplying",
                        "Max", "Min")
        )

        operation_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                _setOperation = when (p2) {
                    1 -> AddingSetOperation()
                    2 -> SubstractingSetOperation()
                    3 -> DividingSetOperation()
                    4 -> MultiplySetOperation()
                    5 -> MaxSetOperation()
                    6 -> MinSetOperation()
                    else -> null
                }
            }
        }
    }

    fun showErrorMessage(msg: String) {
        AlertDialog.Builder(this)
                .setTitle("Input error")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(msg)
                .setPositiveButton("OK", { dialogInterface, _ ->  dialogInterface.cancel() })
                .show()
    }

    fun goToGraphActivity() {
        if (numA_left_et.text.isEmpty() || numA_mid_et.text.isEmpty()
                || numA_righ_et.text.isEmpty() || a1_et.text.isEmpty()
                || a2_et.text.isEmpty() || c1_et.text.isEmpty()
                || c2_et.text.isEmpty() || a_et.text.isEmpty()
                || b_et.text.isEmpty() || c_et.text.isEmpty()) {
            showErrorMessage("Something is empty")
            return
        }

        val intent = Intent(this, AffiliationFunctionGraphActivity::class.java)
        val bundle = Bundle()
        bundle.putDoubleArray("triangular", getTriangularArray())
        bundle.putDoubleArray("twoSided", getTwoSidedGausArray())
        bundle.putDoubleArray("generalized", getGeneralizedArray())

        bundle.putInt("steps", steps_et.text.toString().toInt())

        if (isSecond_switch.isChecked) {
            if (numA1_left_et.text.isEmpty() || numA1_mid_et.text.isEmpty()
                    || numA1_righ_et.text.isEmpty() || a1_1_et.text.isEmpty()
                    || a2_1_et.text.isEmpty() || c1_1_et.text.isEmpty()
                    || c2_1_et.text.isEmpty() || a_1_et.text.isEmpty()
                    || b_1_et.text.isEmpty() || c_1_et.text.isEmpty()) {
                showErrorMessage("Second array is empty")
                return
            }

            bundle.putDoubleArray("triangularSecond", getSecondTriangularArray())
            bundle.putDoubleArray("twoSidedSecond", getSecondTwoSidedGausArray())
            bundle.putDoubleArray("generalizedSecond", getSecondGeneralizedArray())

            if (_setOperation != null) {
                bundle.putBoolean("graphRounded", rounded_switch.isChecked)
                bundle.putSerializable("setOperation", _setOperation)
            }

        } else if (unclearIndex_switch.isChecked) {
            bundle.putBoolean("unclearIndexChecked", true)
        } else if (entropy_switch.isChecked) {
            bundle.putBoolean("entropyChecked", true)
        }

        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    private fun getTriangularArray() =
            doubleArrayOf(numA_left_et.text.toString().toDouble(),
                    numA_mid_et.text.toString().toDouble(),
                    numA_righ_et.text.toString().toDouble())


    private fun getTwoSidedGausArray() =
            doubleArrayOf(a1_et.text.toString().toDouble(),
                    a2_et.text.toString().toDouble(),
                    c1_et.text.toString().toDouble(),
                    c2_et.text.toString().toDouble())

    private fun getGeneralizedArray() =
            doubleArrayOf(a_et.text.toString().toDouble(),
                    b_et.text.toString().toDouble(),
                    c_et.text.toString().toDouble())



    private fun getSecondTriangularArray() =
        doubleArrayOf(numA1_left_et.text.toString().toDouble(),
                numA1_mid_et.text.toString().toDouble(),
                numA1_righ_et.text.toString().toDouble())


    private fun getSecondTwoSidedGausArray() =
            doubleArrayOf(a1_1_et.text.toString().toDouble(),
                    a2_1_et.text.toString().toDouble(),
                    c1_1_et.text.toString().toDouble(),
                    c2_1_et.text.toString().toDouble())

    private fun getSecondGeneralizedArray() =
            doubleArrayOf(a_1_et.text.toString().toDouble(),
                    b_1_et.text.toString().toDouble(),
                    c_1_et.text.toString().toDouble())
}
