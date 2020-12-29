package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.product.domain.model.Product

interface ProductLocalSource {
    suspend fun getProducts(categoryId: Int): List<Product>

    suspend fun setProducts(categoryId: Int, products: List<Product>)
}
