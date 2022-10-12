package com.example.bitfit

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private val fooditems = mutableListOf<DisplayItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvHighestCal = view.findViewById<TextView>(R.id.tvHigh)
        val tvLowestCal = view.findViewById<TextView>(R.id.tvLow)
        val tvAverageCal = view.findViewById<TextView>(R.id.tvAverage)

        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.healthDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayItem(
                        foodName = entity.foodname,
                        foodCal = entity.calories

                    )
                }.also { mappedList ->
                    fooditems.clear()
                    //for (food in mappedList){
                    Log.i("FoodFragment", mappedList.toString())
                    fooditems.addAll(mappedList)

                    // find item with highest calories
                    var highest : Int =0
                    for (calories in fooditems.map{it.foodCal} ){
                        if(highest < calories!!) {
                            highest = calories
                        }
                    }
                    tvHighestCal.text = highest.toString()

                    // find item with lowest calories
                    var lowest : Int = highest
                    for (calories in fooditems.map{it.foodCal} ){
                        if(lowest > calories!!) {
                            lowest = calories
                        }
                    }
                    tvLowestCal.text = lowest.toString()

                    // find average of calories
                    var avgCal : Double = 0.00
                    var total :Double = 0.00
                    for (calories in fooditems.map{it.foodCal}){
                        if (calories != null) {
                            total += calories
                        }
                    }
                    avgCal = total/fooditems.size
                    tvAverageCal.text = avgCal.toString()

                }
            }
        }

    }


    companion object {
     const val TAG = "DashboardFragment"
    }
}