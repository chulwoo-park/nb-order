package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.domain.model.Product

interface ProductLocalSource {
    suspend fun get(categoryId: Int): List<Product>

    suspend fun set(categoryId: Int, products: List<Product>)
}
