package com.example.chloe.confidence_interval_operations

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.chloe.confidence_interval_operations.confidence_operations.operations.*
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

    override val divideNumberInput: Double
        get() = divideNumber_et.text.toString().toDouble()
    override val multiplyNumberInput: Double
        get() = multiplyNumber_et.text.toString().toDouble()
    override val addNumberInput: Double
        get() = addNumber_et.text.toString().toDouble()
    override val substractNumberInput: Double
        get() = substractNumber_et.text.toString().toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radGroup1.setToggleListener { onToggleClicked(it) }

        exec_btn.setOnClickListener { _presenter.executeOnClick() }
        res_to_A_btn.setOnClickListener { _presenter.resultToA() }
        res_to_B_btn.setOnClickListener { _presenter.resultToB() }
    }

    private fun onToggleClicked(id: Int) {
        disableInput()

        when(id) {
            R.id.add_rb -> _presenter.setBinaryOperation(AddingIntervalsOperation())
            R.id.substr_rb -> _presenter.setBinaryOperation(SubstractInvervalsOperations())
            R.id.mul_rb -> _presenter.setBinaryOperation(MultiplyIntervalsOperation())
            R.id.divide_rb -> _presenter.setBinaryOperation(DivideIntervalsOperation())
            R.id.div_thesis_rb -> _presenter.setBinaryOperation(HypothesisIntervalsOperation())

            R.id.add_A_rb -> {
                _presenter.setBinaryOperation(AddingIntervalsOperation())
                enableInput(addNumber_et)
            }
            R.id.substr_B_rb -> {
                _presenter.setBinaryOperation(SubstractInvervalsOperations())
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

            R.id.mul_many_rb -> _presenter.setMultipleOperation(MultipleIntervalMultiplyOperation())

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

    val divideNumberInput: Double
    val multiplyNumberInput: Double
    val addNumberInput: Double
    val substractNumberInput: Double
}


