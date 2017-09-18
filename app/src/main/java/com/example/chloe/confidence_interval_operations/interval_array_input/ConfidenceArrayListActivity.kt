package com.example.chloe.confidence_interval_operations.interval_array_input

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.chloe.confidence_interval_operations.MainActivity
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.confidence_operations.Interval
import kotlinx.android.synthetic.main.activity_confidence_array_list.*


class ConfidenceArrayListActivity : AppCompatActivity() {
    private val adapter: ArrayAdapter<Interval> by lazy { IntervalAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confidence_array_list)

        populateListView()

        input_btn.setOnClickListener {
            val bundle = Bundle()
            bundle.putDoubleArray("intervalValues", getArrayFromListView())
            backToMainActivity(bundle)
        }

        setSupportActionBar(my_toolbar)

        my_toolbar.setNavigationOnClickListener { onBackPressed() }
        my_toolbar.title = "Input array of intervals"
    }

    private fun populateListView() {
        for (i in 0 until 10) {
            adapter.add(null)
        }

        intervalArray_lv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    /**
     * Sequence of left bound and right bound
     * so size of this will be ( interval count * 2 )
     */
    private fun getArrayFromListView(): DoubleArray {
        val arr = ArrayList<Double>()

        for (i in 0 until intervalArray_lv.childCount) {
            val view = getViewByPosition(i, intervalArray_lv)

            val leftBoundText = view.findViewById<EditText>(R.id.leftBound_et).text.toString()
            val rightBoundText = view.findViewById<EditText>(R.id.rightBound_et).text.toString()

            if (leftBoundText.isEmpty() || rightBoundText.isEmpty()) break

            arr.add(leftBoundText.toDouble())
            arr.add(rightBoundText.toDouble())
        }
        Log.i("tag", arr.size.toString())

        return arr.toDoubleArray()
    }

    private fun backToMainActivity(bundle: Bundle) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("intervalList", bundle)


        startActivity(intent)
    }

    private fun getViewByPosition(pos: Int, listView: ListView): View {
        val firstListItemPosition = listView.firstVisiblePosition
        val lastListItemPosition = firstListItemPosition + listView.childCount - 1

        return if (pos < firstListItemPosition || pos > lastListItemPosition) {
            listView.adapter.getView(pos, null, listView)
        } else {
            listView.getChildAt(pos - firstListItemPosition)
        }
    }
}
