package com.andpjt.catchfood.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.andpjt.catchfood.BR
import com.andpjt.catchfood.R
import com.andpjt.catchfood.adapter.SettingMenuRecyclerAdapter
import com.andpjt.catchfood.databinding.ActivitySetBinding
import com.andpjt.catchfood.model.Food
import com.andpjt.catchfood.viewmodel.FoodViewModel
import com.andpjt.catchfood.viewmodel.FoodViewModelFactory

class SettingActivity: AppCompatActivity() {
    private lateinit var vm: FoodViewModel
    private lateinit var binding: ActivitySetBinding
    private lateinit var editDialog: AlertDialog
    private lateinit var dialogView: View
    private lateinit var settingAdapter: SettingMenuRecyclerAdapter
    private var isExist = false

    override fun onResume() {
        super.onResume()
        vm.initSetting()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        setBinding()
        dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_edit, null, false)
        val builder = AlertDialog.Builder(applicationContext)
        builder.setView(dialogView)
        editDialog = builder.create()

        settingAdapter = SettingMenuRecyclerAdapter(
                { food, str, i -> dialogEditButton(food, str, i) },
                { food -> dialogDeleteButton(food) }
        )

        binding.setRecyclerView.apply {
            adapter = settingAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }

        observeDatabase()
    }

    private fun setBinding() {
        vm = ViewModelProvider(this, FoodViewModelFactory(application))
                .get(FoodViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set)
        binding.lifecycleOwner = this@SettingActivity
        binding.setVariable(BR.setting, this)
        binding.setVariable(BR.vm, vm)
    }

    private fun observeDatabase() {
        vm.getAll().observe(this, {
            settingAdapter.setContents(it)
        })
        vm.settingEditText.observe(this, { str ->
            vm.getItem(str).observe(this, {
                isExist = it.isNotEmpty()
            })
        })
    }

    private fun dialogEditButton(item: Food, str: String, value: Int) {
        val updateFood = item.apply {
            food = str
            prefer = value
        }
        vm.insert(updateFood)
    }

    private fun dialogDeleteButton(item: Food) {
        vm.delete(item)
    }

    fun clickAddButton(view: View) {
        if (binding.editText.text.isNotEmpty()) {
            if (isExist) Toast.makeText(this, "이미 메뉴가 존재합니다.", Toast.LENGTH_SHORT).show()
            else {
                vm.insert(Food(null, vm.settingEditText.value ?: "", vm.settingRatingCount.value?.toInt() ?: 3))
                vm.initSetting()
            }
        }
    }

    fun clickCleanButton(view: View) {
        vm.deleteAll()
    }
}