package com.andpjt.catchfood.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
    private val settingAdapter = SettingMenuRecyclerAdapter()

    override fun onResume() {
        super.onResume()
        vm.initSetting()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)

        setBinding()
        setDialog()

        binding.setRecyclerView.apply {
            adapter = settingAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
        }

        vm.getAll().observe(this, {
            settingAdapter.setContents(it)
        })

        // TODO("setting의 edit dialog")
    }

    private fun setBinding() {
        vm = ViewModelProvider(this, FoodViewModelFactory(application))
                .get(FoodViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set)
        binding.lifecycleOwner = this@SettingActivity
        binding.setVariable(BR.setting, this)
        binding.setVariable(BR.vm, vm)
    }

    private fun setDialog() {
        val dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.dialog_edit, null, false)
        val builder = AlertDialog.Builder(applicationContext)
        builder.setView(dialogView)
        editDialog = builder.create()
    }

    fun clickAddButton(view: View) {
        vm.insert(Food(null, vm.settingEditText.value ?: "", vm.settingRatingCount.value?.toInt() ?: 3))
        vm.initSetting()
    }

    fun clickCleanButton(view: View) {
        vm.deleteAll()
    }
}