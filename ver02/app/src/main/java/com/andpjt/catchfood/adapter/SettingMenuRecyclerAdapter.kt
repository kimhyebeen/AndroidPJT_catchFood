package com.andpjt.catchfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andpjt.catchfood.databinding.ItemSetListBinding
import com.andpjt.catchfood.model.Food

class SettingMenuRecyclerAdapter
    : RecyclerView.Adapter<SettingMenuRecyclerAdapter.ItemViewHolder>() {
    private var items = listOf<Food>()

    inner class ItemViewHolder(
            private var itemBinding: ItemSetListBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(item: Food) {
            itemBinding.food = item
            // TODO("itemView의 edit button 기능")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        LayoutInflater.from(parent.context).let {
            val binding = ItemSetListBinding.inflate(it, parent, false)
            return ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setContents(list: List<Food>) {
        items = list
        notifyDataSetChanged()
    }
}