package com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_graph

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangleTransaction
import com.example.chloe.confidence_interval_operations.triangular_numbers.triangular_operations.TriangularNumber
import kotlinx.android.synthetic.main.activity_tanker_graph.*

class TankerGraphActivity : FragmentActivity() {

    private val _adapter by lazy { TankerGraphPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tanker_graph)

        graphViewPager_vp.adapter = _adapter
        _adapter.add(TriangularNumber.of(-2.0, 10.0, 15.0))
        _adapter.add(TriangularNumber.of(0.0, 10.0, 15.0))
        _adapter.add(TriangularNumber.of(2.0, 6.0, 13.0))
        _adapter.add(TriangularNumber.of(-10.0, 10.0, 15.0))
    }


    class TankerGraphPagerAdapter (
            fragmentManager: FragmentManager
    ): FragmentStatePagerAdapter(fragmentManager) {

        private val _numbersList = ArrayList<TriangularNumber>()

        override fun getCount() = _numbersList.size

        override fun getItem(position: Int): Fragment {
            val fragment = TankerGraphFragment()

            val bundle = Bundle()
            bundle.putSerializable("leftOperand", _numbersList[position])

            fragment.arguments = bundle

            return fragment
        }

        fun add(number: TriangularNumber) {
            _numbersList.add(number)
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

            view?.findViewById<TankerGraphView>(R.id.graph_gv)
                    ?.setInitValues(leftOperand)

            return view
        }
    }
}

