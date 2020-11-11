package com.andpjt.catchfood.model

import android.app.Application
import androidx.lifecycle.LiveData

class FoodRepository(application: Application) {
    private val foodDatabase = FoodDatabase.getInstance(application)
    private val foodDao = foodDatabase.foodDao()

    fun getAll(): LiveData<List<Food>> {
        return foodDao.getAll()
    }

    fun insert(food: Food) {
        return foodDao.insert(food)
    }

    fun delete(food: Food) {
        return foodDao.delete(food)
    }

}