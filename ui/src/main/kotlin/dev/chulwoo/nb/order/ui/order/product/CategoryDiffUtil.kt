package dev.chulwoo.nb.order.ui.order.product

import androidx.recyclerview.widget.DiffUtil
import dev.chulwoo.nb.order.features.category.presentation.model.Category

class CategoryDiffUtil(
    private val oldList: List<Category>,
    private val newList: List<Category>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        return "payloads"
    }
}
