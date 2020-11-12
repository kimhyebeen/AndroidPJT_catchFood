package com.andpjt.catchfood.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.andpjt.catchfood.BR
import com.andpjt.catchfood.R
import com.andpjt.catchfood.databinding.ActivityMainBinding
import com.andpjt.catchfood.viewmodel.FoodViewModel
import com.andpjt.catchfood.viewmodel.FoodViewModelFactory
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.dialog_help.view.*
import java.util.*

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var cycle: Int = 0
    private var random: Double = 0.0
    private var isClickedMenuButton: Boolean = false

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private lateinit var vm: FoodViewModel
    private var foodCount: Int = 0

    private val handler = Handler()
    private lateinit var dialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder
    private var menu = ArrayList<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this, FoodViewModelFactory(application))
                .get(FoodViewModel::class.java)
        setBinding()
        setMobileAds()
        observeFoodData()

        if (!isNetworkAvailable(applicationContext)) showToast("인터넷을 연결해주세요.")

        binding.menuButton.setOnClickListener {
            if (foodCount == 0) showToast("데이터를 세팅해주세요.")
            else {
                if (isClickedMenuButton) {
                    timerTask.cancel()
                    timer.cancel()
                    timer.purge()
                    vm.changeMenuButton(false)
                    /* 이전에 나왔던 menu 보여주기 (최대 3개) */
                    vm.addPreText()
                    if (cycle == 2) showMessage()
                } else {
                    timerStart()
                    vm.changeMenuButton(true)
                }
            }
        }

        /* data setting 버튼 활성화 */
        binding.datasetButton.setOnClickListener {
            val settingIntent: Intent =
                if (foodCount == 0) Intent(applicationContext, InitActivity::class.java)
                else Intent(applicationContext, SetActivity::class.java)
            startActivity(settingIntent)
        }

        /* help 버튼 활성화 */
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_help, null, false)
        builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        dialog = builder.create()
        dialogView.outButton.setOnClickListener { dialog.dismiss() }

        binding.helpText.setOnClickListener { dialog.show() }
    }

    private fun observeFoodData() {
        vm.getAll().observe(this, { list ->
            foodCount = list.size
            menu.clear()
            list.map {
                for (i in 1..it.prefer) menu.add(it.food)
            }
        })
        vm.isMenuButtonClicked.observe(this, {
            isClickedMenuButton = !isClickedMenuButton
        })
        vm.clickedCount.observe(this, {
            cycle = it
        })
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this@MainActivity
        binding.setVariable(BR.vm, vm)
    }

    private fun setMobileAds() {
        MobileAds.initialize(this)
        binding.adView.loadAd(
                AdRequest.Builder().build()
        )
    }

    // TODO("네트워크 연결 확인 부분을 NetworkCallback을 사용해서 바꾸기")
    // 참고 : https://gooners0304.tistory.com/entry/NetworkCallback%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%98%EC%97%AC-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-%EC%83%81%ED%83%9C%EB%A5%BC-%EB%B0%9B%EC%95%84%EC%98%A4%EC%9E%90
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo?.let {
            if (it.type == ConnectivityManager.TYPE_WIFI) return true
            else if (it.type == ConnectivityManager.TYPE_MOBILE) return true
        } ?: return false
        return false
    }

    private fun timerStart() {
        if (foodCount > 0) {
            vm.changeMenuButton(true)
            timerTask = object : TimerTask() {
                override fun run() {
                    val runnable = Runnable {
                        random = Math.random() * menu.size
                        vm.changeMenuText(menu.get(random.toInt()))
                    }
                    handler.post(runnable)
                }
            }
            timer = Timer()
            timer.schedule(timerTask, 0, 80)
        } else {
            vm.changeMenuText("데이터를 세팅해주세요.")
            vm.changeMenuButton(false)
        }
    }

    private fun showMessage() {
        val alert = arrayOf(
                "답정너!!!", "그만 눌러,,.,,,",
                "그만 누를 때 됐잖아.,,....",
                "그만.., 그만.......",
                "하, 이제 진짜 마지막이다???",
                "진짜 이제 그만 누르면 안돼?",
                "그냥 추천해준거 먹지..??",
                "언제까지 누를거야?",
                "이러다가 밥 시간 다 지날듯ㅎㅎ",
                "ㅋㅋ아니ㅋㅋ 밥 안먹을거야??",
                "ㅎ...그냥 아무거나 먹어..",
                "야야ㅑ 밥시간 길어? 그냥 먹지??",
                "그래그래 계에에에속 눌러라..",
                "도대체 무슨 답을 기다리는거야?",
                "진짜 답정너..."
        )
        val rdm = (Math.random() * alert.size).toInt()
        showToast(alert[rdm])
    }

    override fun onPause() {
        super.onPause()
        if (isClickedMenuButton) {
            timerTask.cancel()
            timer.apply {
                cancel()
                purge()
            }
            vm.changeMenuButton(false)
        }
    }

    private fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
}