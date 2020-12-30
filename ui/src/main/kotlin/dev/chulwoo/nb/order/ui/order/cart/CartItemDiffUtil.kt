package dev.chulwoo.nb.order.ui.order.cart

import androidx.recyclerview.widget.DiffUtil
import dev.chulwoo.nb.order.features.cart.presentation.model.CartItem

class CartItemDiffUtil : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.product.id == newItem.product.id
    }

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}
