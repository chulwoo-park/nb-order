package dev.chulwoo.nb.order.features.domain.repository

import dev.chulwoo.nb.order.features.domain.model.Product

interface ProductRepository {
    suspend fun get(categoryId: Int): List<Product>
}
