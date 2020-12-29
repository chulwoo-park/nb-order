package dev.chulwoo.nb.order.ui.order

import androidx.recyclerview.widget.DiffUtil
import dev.chulwoo.nb.order.features.category.presentation.model.Product

class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
