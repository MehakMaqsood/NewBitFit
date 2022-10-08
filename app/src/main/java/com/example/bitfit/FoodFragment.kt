package com.example.bitfit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

class FoodFragment : Fragment() {

    private val newFoodActivityRequestCode = 1
    private lateinit var itemViewModel: ItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view?.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvFood)
        val adapter = FoodAdapter(requireContext())
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recyclerView?.addItemDecoration(dividerItemDecoration)
        }

        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        itemViewModel.allItems.observe(viewLifecycleOwner, Observer { food ->
            food?.let { adapter.setFood(it) }
        })

        view?.findViewById<Button>(R.id.btnAdd)?.setOnClickListener{
            val intent = Intent(activity, AddFoodActivity::class.java)
            startActivityForResult(intent, newFoodActivityRequestCode)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                // Add new food name into database
                val food = FoodItem(0,data.getStringExtra(AddFoodActivity.EXTRA_FOOD), data.getStringExtra(AddFoodActivity.EXTRA_CALORIES)?.toInt())
                itemViewModel.insert(food)
                // Add new calories into database
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