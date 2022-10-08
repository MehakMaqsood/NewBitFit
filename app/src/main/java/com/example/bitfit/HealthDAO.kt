package com.example.bitfit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDAO {
    // Query to get all data from the table HealthData
    @Query("SELECT * FROM HealthData")
    fun getAll(): LiveData<List<FoodItem>>

    // Insert new item to the HealthData table
    @Insert
    fun insert(food: FoodItem)

    // Delete all data from HealthTable
    @Query("DELETE FROM HealthData")
    fun deleteAll()

    // Delete single item from table
    @Delete
    fun deleteItem(food: FoodItem)

    //  Get the highest value
    @Query("SELECT MAX(Calories) FROM HealthData")
    fun getHighestCal() : Flow<Int>

    // Get the lowest vlue
   // @Query("SELECT MIN(Calories) FROM HealthData")
    //fun getLowest()

    // Get the average value
    //@Query("SELECT AVG(Calories) FROM HealthData")
    //fun getAvg()

}