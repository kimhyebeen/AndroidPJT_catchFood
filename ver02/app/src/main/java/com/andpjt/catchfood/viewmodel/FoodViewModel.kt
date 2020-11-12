package com.andpjt.catchfood.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andpjt.catchfood.model.Food
import com.andpjt.catchfood.model.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(
        application: Application
): AndroidViewModel(application) {
    private val repository = FoodRepository(application)
    private val _isMenuButtonClicked = MutableLiveData<Boolean>()
    private val _clickedCount = MutableLiveData<Int>()
    private val _menuText = MutableLiveData<String>()
    private val _preText = MutableLiveData<String>()

    val settingEditText = MutableLiveData<String>()
    val settingRatingCount = MutableLiveData<Float>()

    init {
        initialize()
        initSetting()
    }

    val isMenuButtonClicked: LiveData<Boolean> get() = _isMenuButtonClicked
    val clickedCount: LiveData<Int> get() = _clickedCount
    val menuText: LiveData<String> get() = _menuText
    val preText: LiveData<String> get() = _preText

    fun initialize() {
        _isMenuButtonClicked.value = false
        _clickedCount.value = 0
        _menuText.value = "데이터를 세팅해주세요."
        _preText.value = ""
    }

    fun initSetting() {
        settingEditText.value = ""
        settingRatingCount.value = 3f
    }

    fun addCount() {
        _clickedCount.value = _clickedCount.value?.let { (it+1) % 3 }
    }

    fun changeMenuButton(flag: Boolean) {
        _isMenuButtonClicked.value = flag
    }

    fun changeMenuText(str: String) {
        _menuText.value = str
    }

    fun addPreText() {
        if (_clickedCount.value == 2) _preText.value = ""
        _preText.value = _preText.value?.let {
            "${_menuText.value}\n${it}"
        }
    }

    fun getAll(): LiveData<List<Food>> {
        return repository.getAll()
    }

    fun insert(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(food)
        }
    }

    fun delete(food: Food) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(food)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}