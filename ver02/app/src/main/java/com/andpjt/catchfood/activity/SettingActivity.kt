package com.andpjt.catchfood.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.andpjt.catchfood.BR
import com.andpjt.catchfood.R
import com.andpjt.catchfood.databinding.ActivitySetBinding
import com.andpjt.catchfood.model.Food
import com.andpjt.catchfood.viewmodel.FoodViewModel
import com.andpjt.catchfood.viewmodel.FoodViewModelFactory

class SettingActivity: AppCompatActivity() {
    private lateinit var vm: FoodViewModel
    private lateinit var binding: ActivitySetBinding

    override fun onResume() {
        super.onResume()
        vm.initSetting()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        vm = ViewModelProvider(this, FoodViewModelFactory(application))
                .get(FoodViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set)
        binding.lifecycleOwner = this@SettingActivity
        binding.setVariable(BR.setting, this)
        binding.setVariable(BR.vm, vm)
    }

    fun clickAddButton(view: View) {
        vm.insert(Food(null, vm.settingEditText.value ?: "", vm.settingRatingCount.value ?: 3))
    }

    fun clickCleanButton(view: View) {
        vm.deleteAll()
    }
}