package com.example.chloe.confidence_interval_operations.interval_array_input

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval

class IntervalAdapter (context: Context):
        ArrayAdapter<Interval>(context, 0) {

    private var arr = Array(10, {_ -> kotlin.DoubleArray(2) })

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.interval_list_item, parent, false)

        view.findViewById<TextView>(R.id.leftBound_et)
                .addTextChangedListener(getTextListener(0, position))
        view.findViewById<TextView>(R.id.rightBound_et)
                .addTextChangedListener(getTextListener(1, position))


        view?.findViewById<TextView>(R.id.interval_tv)?.text = "Interval #${position + 1}"

        val leftBoundEdit = view?.findViewById<TextView>(R.id.leftBound_et)
        val rightBoundEdit = view?.findViewById<TextView>(R.id.rightBound_et)

        if (arr[position][0] != 0.0) {
            leftBoundEdit?.text = arr[position][0].toString()
        }
        if (arr[position][1] != 0.0) {
            rightBoundEdit?.text = arr[position][1].toString()
        }

        return view
    }


    private fun getTextListener(index: Int, position: Int): TextWatcher {
        return object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    arr[position][index] = p0?.toString()?.toDouble()!!
                } else {
                    arr[position][index] = 0.0;
                }
            }
        }
    }
}