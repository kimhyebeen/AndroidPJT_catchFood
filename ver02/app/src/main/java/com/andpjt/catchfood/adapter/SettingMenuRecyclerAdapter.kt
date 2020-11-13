package com.andpjt.catchfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.andpjt.catchfood.R
import com.andpjt.catchfood.databinding.ItemSetListBinding
import com.andpjt.catchfood.model.Food
import kotlinx.android.synthetic.main.dialog_edit.view.*
import kotlinx.android.synthetic.main.item_set_list.view.*

class SettingMenuRecyclerAdapter(
        private val dialogEditButton: (Food, String, Int) -> Unit,
        private val dialogDeleteButton: (Food) -> Unit
): RecyclerView.Adapter<SettingMenuRecyclerAdapter.ItemViewHolder>() {
    private var items = listOf<Food>()
    private lateinit var context: Context
    private lateinit var dialog: AlertDialog
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialogView: View

    inner class ItemViewHolder(
            private var itemBinding: ItemSetListBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun onBind(item: Food, index: Int) {
            itemBinding.food = item
            itemBinding.root.editButton.setOnClickListener {
                startDialog(item, index)
            }
        }

        private fun startDialog(item: Food, idx: Int) {
            dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null, false)
            builder = AlertDialog.Builder(context)
            builder.setView(dialogView)
            dialog = builder.create()

            dialogView.dialog_edit_text.setText(item.food)
            dialogView.dialog_rating_bar.rating = item.prefer.toFloat()
            dialogView.dialog_edit_button.setOnClickListener {
                val text = dialogView.dialog_edit_text.text.toString()
                val value = dialogView.dialog_rating_bar.rating.toInt()
                dialogEditButton(item, text, value)
                notifyItemChanged(idx)

                dialog.dismiss()
            }
            dialogView.dialog_delete_button.setOnClickListener {
                dialogDeleteButton(item)
                notifyItemRemoved(idx)

                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        LayoutInflater.from(parent.context).let {
            val binding = ItemSetListBinding.inflate(it, parent, false)
            return ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun setContents(list: List<Food>) {
        items = list
        notifyDataSetChanged()
    }
}