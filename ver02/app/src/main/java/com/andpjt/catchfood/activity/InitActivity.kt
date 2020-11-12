package com.andpjt.catchfood.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.andpjt.catchfood.BR
import com.andpjt.catchfood.R
import com.andpjt.catchfood.adapter.InitMenuGridAdapter
import com.andpjt.catchfood.databinding.ActivityInitBinding
import com.andpjt.catchfood.viewmodel.FoodViewModel
import com.andpjt.catchfood.viewmodel.FoodViewModelFactory

class InitActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInitBinding
    private lateinit var vm: FoodViewModel
    private lateinit var gridAdapter: InitMenuGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        vm = ViewModelProvider(this, FoodViewModelFactory(application))
                .get(FoodViewModel::class.java)
        gridAdapter = InitMenuGridAdapter(applicationContext)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_init)
        binding.lifecycleOwner = this@InitActivity
        binding.setVariable(BR.init, this)
        binding.initMenuGridView.adapter = gridAdapter
    }

    fun clickAddButton(view: View) {
        val itemList = gridAdapter.getItems()
        val clickedList = gridAdapter.getClickedList()
        for (i in 0..26) {
            if (!clickedList[i]) continue
            println("InitActivity - $i 번째가 true야!!")
            vm.insert(itemList[i])
        }
        onBackPressed()
    }
}