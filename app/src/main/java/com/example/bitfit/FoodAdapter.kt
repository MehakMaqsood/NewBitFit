package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


const val ARTICLE_EXTRA = "FOOD_EXTRA"
private const val TAG = "FoodAdapter"

class FoodAdapter(private val context: Context, private val fooditems: List<DisplayItem>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fooditem = fooditems[position]
        holder.bind(fooditem)
    }

    override fun getItemCount() = fooditems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val foodnameView = itemView.findViewById<TextView>(R.id.tvFoodItem)
        private val foodcalView = itemView.findViewById<TextView>(R.id.tvNumCal)

        // helper method to help set up the onBindViewHolder method
        fun bind(fooditem: DisplayItem) {
            foodnameView.text = fooditem.foodName
            foodcalView.text = fooditem.foodCal.toString()
        }
    }

}