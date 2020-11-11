package com.andpjt.catchfood.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.andpjt.catchfood.model.Food
import com.andpjt.catchfood.model.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(
        application: Application
): AndroidViewModel(application) {
    private val repository = FoodRepository(application)

    fun getAll(): LiveData<List<Food>> {
        return repository.getAll()
    }

    fun insert(food: Food) {
        viewModelScope.launch {
            repository.insert(food)
        }
    }

    fun delete(food: Food) {
        viewModelScope.launch {
            repository.delete(food)
        }
    }
}