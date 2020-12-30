package dev.chulwoo.nb.order.ui.order.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chulwoo.nb.order.features.cart.presentation.model.CartItem
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import dev.chulwoo.nb.order.ui.databinding.CartItemBinding

class CartItemAdapter(
    private val onProductAdded: (Product) -> Unit,
    private val onProductRemoved: (Product) -> Unit,
    private val onProductCleared: (Product) -> Unit
) : ListAdapter<CartItem, CartItemViewHolder>(CartItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.cartItem = item
        holder.binding.deleteButton.setOnClickListener {
            onProductCleared(item.product)
        }
        holder.binding.addButton.setOnClickListener {
            onProductAdded(item.product)
        }
        holder.binding.removeButton.setOnClickListener {
            onProductRemoved(item.product)
        }
    }
}

class CartItemViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)
