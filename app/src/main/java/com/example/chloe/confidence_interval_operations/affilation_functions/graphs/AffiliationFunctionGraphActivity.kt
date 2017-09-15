package com.example.chloe.confidence_interval_operations.affilation_functions.graphs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chloe.confidence_interval_operations.R
import com.example.chloe.confidence_interval_operations.affilation_functions.AffiliationFunction
import com.example.chloe.confidence_interval_operations.affilation_functions.TrapezoidalAffiliationFunction
import com.example.chloe.confidence_interval_operations.affilation_functions.TriangularAffilationFunction
import kotlinx.android.synthetic.main.activity_affiliation_function_graph.*

class AffiliationFunctionGraphActivity : AppCompatActivity() {

    private val _functionsAdapter
            by lazy { AffiliationFunctionPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_function_graph)

        _functionsAdapter.add(TriangularAffilationFunction(-100.0, 10.0, 200.0))
        _functionsAdapter.add(TrapezoidalAffiliationFunction(200.0, 220.0, 240.0, 250.0))

        function_pager.adapter = _functionsAdapter
    }


    class AffiliationFunctionPagerAdapter (
            fragmentManager: FragmentManager
    ): FragmentStatePagerAdapter(fragmentManager) {

        val _functions = ArrayList<AffiliationFunction>()

        override fun getCount() = _functions.size

        override fun getItem(position: Int): Fragment {
            val fragment = AffiliationFunctionFragment()

            val bundle = Bundle()
            bundle.putSerializable("function", _functions[position])

            fragment.arguments = bundle
            return fragment
        }

        fun add(function: AffiliationFunction) {
            _functions.add(function)
            notifyDataSetChanged()
        }
    }


    class AffiliationFunctionFragment: Fragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?
                                  , savedInstanceState: Bundle?): View? {
            val view = inflater!!.inflate(
                    R.layout.affiliation_function_graph_item,
                    container,
                    false
            )

            val function = arguments.getSerializable("function") as AffiliationFunction

            view?.findViewById<AffiliationFunctionGraphView>(
                    R.id.affiliation_function_gv
            )?.startingInit(function = function)

            return view
        }
    }
}
