package dev.chulwoo.nb.order.features.product.domain.repository

import dev.chulwoo.nb.order.features.product.domain.model.Product

interface ProductRepository {
    suspend fun get(categoryId: Int): List<Product>
}
