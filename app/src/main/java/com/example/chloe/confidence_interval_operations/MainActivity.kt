package com.example.chloe.confidence_interval_operations

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.chloe.confidence_interval_operations.confidence_operations.ConfidenceInterval
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.*
import com.example.chloe.confidence_interval_operations.interval_array_input.ConfidenceArrayListActivity
import com.example.chloe.confidence_interval_operations.interval_graph.IntervalGraphActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(), MainActivityView {
    private val _presenter = MainActivityPresenter(this)

    override var intervalA: String
        get() = intervalA_et.text.toString()
        set(value) {
            intervalA_et.setText(value)
        }

    override var intervalB: String
        get() = intervalB_et.text.toString()
        set(value) {
            intervalB_et.setText(value)
        }

    override var isGraphButtonEnabled: Boolean
        get() = graph_btn.isEnabled
        set(value) {
              graph_btn.isEnabled = value
        }

    override val divideNumberInput: Double?
        get() = divideNumber_et.text.toString().toDoubleOrNull()
    override val multiplyNumberInput: Double?
        get() = multiplyNumber_et.text.toString().toDoubleOrNull()
    override val addNumberInput: Double?
        get() = addNumber_et.text.toString().toDoubleOrNull()
    override val substractNumberInput: Double?
        get() = substractNumber_et.text.toString().toDoubleOrNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radGroup1.setToggleListener { onToggleClicked(it) }

        exec_btn.setOnClickListener { _presenter.executeOnClick() }
        graph_btn.setOnClickListener { _presenter.graphButtonOnClick() }
        res_to_A_btn.setOnClickListener { _presenter.resultToA() }
        res_to_B_btn.setOnClickListener { _presenter.resultToB() }

        radGroup1.onClick(add_rb)

        disableResultButtons()
        isGraphButtonEnabled = false

        if (intent.extras != null) {
            if (intent.extras.getBundle("intervalList") != null) {
                val array = intent.extras.getBundle("intervalList").getDoubleArray("intervalValues")
                _presenter.transformDoubleArrayToResult(array)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        radGroup1.onClick(add_rb)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString("intervalA", intervalA)
        outState?.putString("intervalB", intervalB)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        intervalA = savedInstanceState?.getString("intervalA")!!
        intervalB = savedInstanceState.getString("intervalB")!!
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
        res_to_A_btn.isEnabled = true
        res_to_B_btn.isEnabled = true
    }

    override fun disableResultButtons() {
        res_to_A_btn.isEnabled = false
        res_to_B_btn.isEnabled = false
    }

    override fun openGraphActivity(intervalValues: DoubleArray, fullInfo: String,
                                   isOperationUnary: Boolean) {
        val intent = Intent(this, IntervalGraphActivity::class.java)
        val bundle = Bundle()

        bundle.putDoubleArray("intervalValues", intervalValues)
        bundle.putString("operationFullInfo", fullInfo)
        bundle.putBoolean("isOperationUnary", isOperationUnary)

        intent.putExtra("intervalBundle", bundle)


        startActivity(intent)
    }


    private fun openIntervalListInputActivity() =
            startActivity(Intent(this, ConfidenceArrayListActivity::class.java))


    private fun onToggleClicked(id: Int) {
        disableInput()

        when(id) {
            R.id.add_rb -> _presenter.setBinaryOperation(AddingIntervalsOperation())
            R.id.substr_rb -> _presenter.setBinaryOperation(SubstractInvervalsOperations())
            R.id.mul_rb -> _presenter.setBinaryOperation(MultiplyIntervalsOperation())
            R.id.divide_rb -> _presenter.setBinaryOperation(DivideIntervalsOperation())
            R.id.div_thesis_rb -> _presenter.setBinaryOperation(HypothesisIntervalsOperation())

            R.id.add_A_rb -> {
                _presenter.setBinaryOperation(AddingClearNumberToIntervalOperation())
                enableInput(addNumber_et)
            }
            R.id.substr_B_rb -> {
                _presenter.setBinaryOperation(SubstractClearNumberFromIntervalOperation())
                enableInput(substractNumber_et)
            }
            R.id.mul_A_rb -> {
                _presenter.setBinaryOperation(MultiplyOnClearNumberOperation())
                enableInput(multiplyNumber_et)
            }
            R.id.divide_B_rb -> {
                _presenter.setBinaryOperation(DivideOnClearNumberOperation())
                enableInput(divideNumber_et)
            }

            R.id.mul_many_rb -> openIntervalListInputActivity()

            R.id.max_rb -> _presenter.setBinaryOperation(FindMaxIntervalOperation())
            R.id.min_rb -> _presenter.setBinaryOperation(FindMinIntervalOperation())

            R.id.reverse_A_rb -> _presenter.setUnaryOperation(ReverseIntervalOperation())
            R.id.inverse_B_rb -> _presenter.setUnaryOperation(InversionIntervalOperation())

            else -> Log.i("tag", "operation wasn't found")
        }
    }

    private fun enableInput(view: View) {
        view.isEnabled = true
    }

    private fun disableInput() {
        divideNumber_et.isEnabled = false
        multiplyNumber_et.isEnabled = false
        addNumber_et.isEnabled = false
        substractNumber_et.isEnabled = false
    }
}


interface MainActivityView {
    var intervalA: String
    var intervalB: String

    val divideNumberInput: Double?
    val multiplyNumberInput: Double?
    val addNumberInput: Double?
    val substractNumberInput: Double?

    fun showErrorMessage(msg: String)

    fun enableResultButtons()
    fun disableResultButtons()

    var isGraphButtonEnabled: Boolean

    fun openGraphActivity(intervalValues: DoubleArray, fullInfo: String,
                          isOperationUnary: Boolean)
}


