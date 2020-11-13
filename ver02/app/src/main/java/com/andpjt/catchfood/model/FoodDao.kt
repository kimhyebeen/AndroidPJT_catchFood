package com.andpjt.catchfood.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {
    @Query("SELECT * FROM foods")
    fun getAll(): LiveData<List<Food>>

    @Query("SELECT * FROM foods WHERE food = :name")
    fun getItem(name: String): LiveData<List<Food>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: Food)

    @Delete
    fun delete(food: Food)

    @Query("DELETE FROM foods")
    fun deleteAll()
}