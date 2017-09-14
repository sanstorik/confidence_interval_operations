package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangleTransaction
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import kotlinx.android.synthetic.main.activity_tanker_graph.*

class TankerGraphActivity : FragmentActivity() {

    private val _adapter by lazy { TankerGraphPagerAdapter(supportFragmentManager) }
    private lateinit var _numbers: Array<TriangularNumber>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanker_graph)

        if (intent.extras != null && intent.extras.getBundle("arr") != null) {
            _numbers = intent.extras.getBundle("arr")
                    .getSerializable("arr") as Array<TriangularNumber>
        }

        graphViewPager_vp.adapter = _adapter

        for (i in 0 until _numbers.size step 2) {
            _adapter.add(_numbers[i], _numbers[i + 1])
        }
    }


    class TankerGraphPagerAdapter (
            fragmentManager: FragmentManager
    ): FragmentStatePagerAdapter(fragmentManager) {

        private val _numbersList = ArrayList<Array<TriangularNumber>>()

        override fun getCount() = _numbersList.size

        override fun getItem(position: Int): Fragment {
            val fragment = TankerGraphFragment()

            val bundle = Bundle()
            bundle.putSerializable("leftOperand", _numbersList[position][0])
            bundle.putSerializable("rightOperand", _numbersList[position][1])

            fragment.arguments = bundle

            return fragment
        }

        fun add(leftNumber: TriangularNumber, rightNumber: TriangularNumber) {
            _numbersList.add(arrayOf(leftNumber, rightNumber))
            notifyDataSetChanged()
        }
    }

    class TankerGraphFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val view = inflater?.inflate(
                    R.layout.tanker_graph_item,
                    container, false)

            val bundle = this.arguments
            val leftOperand = bundle.getSerializable("leftOperand") as TriangularNumber
            val rightOperand = bundle.getSerializable("rightOperand") as TriangularNumber

            view?.findViewById<TankerGraphView>(R.id.graph_gv)
                    ?.setInitValues(leftOperand, rightOperand)

            return view
        }
    }
}

