package dev.chulwoo.nb.order.ui.order.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import dev.chulwoo.nb.order.ui.databinding.ProductItemBinding

class ProductAdapter(private val onProductSelected: (Product) -> Unit) :
    ListAdapter<Product, ProductViewHolder>(ProductDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        with(holder.binding) {
            this.product = product
            Glide.with(root.context).load(product.imageUrl).into(image)
            root.setOnClickListener { onProductSelected(product) }
        }
    }
}

class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
