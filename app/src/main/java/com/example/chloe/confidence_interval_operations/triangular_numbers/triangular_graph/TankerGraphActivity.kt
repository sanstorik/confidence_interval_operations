package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import kotlinx.android.synthetic.main.activity_tanker_graph.*

class TankerGraphActivity : FragmentActivity() {

    private val _adapter by lazy { TankerGraphPagerAdapter(supportFragmentManager) }
   // private lateinit var _numbers: Array<TriangularNumber>

    private var _numbersList = ArrayList<TriangularNumber>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanker_graph)

        if (intent.extras != null && intent.extras.getBundle("arr") != null) {
            val arr = intent.extras.getBundle("arr")
                    .getSerializable("arr") as Array<Any>

            arr.forEach {
                _numbersList.add(it as TriangularNumber)
            }
        }

        graphViewPager_vp.adapter = _adapter

        for (i in 0 until _numbersList.size step 2) {
            _adapter.add(_numbersList[i], _numbersList[i + 1])
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
            bundle.putInt("iteration", position)
            bundle.putInt("maxIteration", count)

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
            val iteration = bundle.getInt("iteration")
            val maxIteration = bundle.getInt("maxIteration")

            view?.findViewById<TankerGraphView>(R.id.graph_gv)
                    ?.setInitValues(leftOperand, rightOperand,
                            iteration = iteration,
                            maxIteration = maxIteration)

            return view
        }
    }
}

