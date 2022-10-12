package com.example.bitfit

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class FoodFragment : Fragment() {

    private val newFoodActivityRequestCode = 1
    private val fooditems = mutableListOf<DisplayItem>()
    private lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view?.findViewById<RecyclerView>(R.id.rvFood)
        val adapter = FoodAdapter(requireContext(),fooditems)
        recyclerView?.adapter = adapter

        recyclerView?.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recyclerView?.addItemDecoration(dividerItemDecoration)
        }

        view?.findViewById<Button>(R.id.btnAdd)?.setOnClickListener{
            val intent = Intent(activity, AddFoodActivity::class.java)
            startActivityForResult(intent, newFoodActivityRequestCode)
        }

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
                    adapter.notifyDataSetChanged()
                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {

            intentData?.let{data ->
                lifecycleScope.launch(IO) {
                    (activity?.application as FoodApplication).db.healthDao().insertAll(
                        FoodItem(
                            id = 0,
                            foodname = data?.getStringExtra(AddFoodActivity.EXTRA_FOOD),
                            calories = data?.getStringExtra(AddFoodActivity.EXTRA_CALORIES)?.toInt()
                        )
                    )
                }
            }

        } else {
            Toast.makeText(
                 context,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    companion object {
        const val TAG = "FoodFragment"
    }

}
