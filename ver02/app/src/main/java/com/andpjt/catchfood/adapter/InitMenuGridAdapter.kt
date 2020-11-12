package com.andpjt.catchfood.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.andpjt.catchfood.R
import com.andpjt.catchfood.model.Food
import kotlinx.android.synthetic.main.item_init_menu.view.*

class InitMenuGridAdapter(
        private val context: Context
): BaseAdapter() {
    private var items = MutableList(27) { Food(null, "", 3) }
    private var itemString = listOf(
            "치킨", "피자", "햄버거", "파스타", "스테이크",
            "리조또", "알밥", "덮밥", "국밥", "마라탕",
            "돈까스", "초밥", "회", "짜장면", "쌀국수",
            "카레", "집밥", "월남쌈", "막창", "닭발",
            "찜닭", "샤브샤브", "보쌈", "족발", "고기",
            "양꼬치", "떡볶이"
    )
    private var clickedList = MutableList(30) { false }

    init {
        for (i in 0..26) {
            items[i].food = itemString[i]
        }
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    fun getClickedList(): List<Boolean> = clickedList
    fun getItems(): List<Food> = items

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.item_init_menu, parent, false)
                .apply {
                    item_init_menu_button.text = itemString[position]
                    item_init_menu_button.setOnClickListener {
                        if (clickedList[position]) it.setBackgroundResource(R.drawable.btn_unclick)
                        else it.setBackgroundResource(R.drawable.btn_click)
                        clickedList[position] = !clickedList[position]
                    }
                }
    }
}