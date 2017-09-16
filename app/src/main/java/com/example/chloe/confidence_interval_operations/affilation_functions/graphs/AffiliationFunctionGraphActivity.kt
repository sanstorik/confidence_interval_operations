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
import com.example.chloe.confidence_interval_operations.affilation_functions.*
import kotlinx.android.synthetic.main.activity_affiliation_function_graph.*

class AffiliationFunctionGraphActivity : AppCompatActivity() {

    private val _functionsAdapter
            by lazy { AffiliationFunctionPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affiliation_function_graph)

        if (intent.extras != null) {
            val bundle = intent.extras.getBundle("bundle")
            val triangularValues = bundle.getDoubleArray("triangular")
            val twoSidedValues = bundle.getDoubleArray("twoSided")
            val generalizedValues = bundle.getDoubleArray("generalized")

            _functionsAdapter.add(TriangularAffiliationFunction(a = triangularValues[0],
                    b = triangularValues[1], c = triangularValues[2]))

            _functionsAdapter.add(
                    TwoSidedGaussAffiliationFunction(a1 = twoSidedValues[0],
                            c1 = twoSidedValues[2], c2 = twoSidedValues[3], a2 = twoSidedValues[1]))

            _functionsAdapter.add(GeneralizedAffiliationFunction(a = generalizedValues[0],
                    c = generalizedValues[2], b = generalizedValues[1]))


            _functionsAdapter.add(
                    TrapezoidalAffiliationFunction(a = 200.0, b = 220.0, c = 240.0, d = 250.0))
            _functionsAdapter.add(GaussSymmetricAffiliationFunction(a = 3.0, b = 15.0, c = 2.0))
            _functionsAdapter.add(SigmoidAffiliationFunction(a = 1.0, c = 4.0))
            _functionsAdapter.add(
                    SigmoidMulAffiliationFunction(a1 = -0.2, a2 = 0.05, c1 = 0.003, c2 = 0.007))
            _functionsAdapter.add(
                    SigmoidSubstrAffiliationFunction(a1 = 10.0, a2 = 3.0, c1 = 9.0, c2 = 17.0))
            _functionsAdapter.add(ZLookAlikeAffiliationFunction(a = -2.0, b = 10.0))
            _functionsAdapter.add(SLookAlikeAffiliationFunction(a = -2.0, b = 10.0))
            _functionsAdapter.add(PiLookAlikeAffiliationFunction(a = 1.0, b = 4.0, c = 5.0, d = 9.0))
            _functionsAdapter.add(LaplasAffiliationFunction(b = 3.0, d = 5.0))
            _functionsAdapter.add(SquareAffiliationFunction(a = 4.0, b = 10.0))

            function_pager.adapter = _functionsAdapter
        }
    }


    class AffiliationFunctionPagerAdapter (
            fragmentManager: FragmentManager
    ): FragmentStatePagerAdapter(fragmentManager) {

        private val _functions = ArrayList<AffiliationFunction>()

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

            view.findViewById<AffiliationFunctionGraphView>(
                    R.id.affiliation_function_gv
            ).startingInit(function)

            return view
        }
    }
}
