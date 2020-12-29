package dev.chulwoo.nb.order.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import dev.chulwoo.nb.order.ui.databinding.ProductItemBinding

class ProductAdapter : ListAdapter<Product, ProductViewHolder>(ProductDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.product = getItem(position)
    }
}

class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
