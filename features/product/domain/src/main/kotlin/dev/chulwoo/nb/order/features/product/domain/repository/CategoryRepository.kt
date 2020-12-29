package dev.chulwoo.nb.order.features.product.domain.repository

import dev.chulwoo.nb.order.features.product.domain.model.Category

interface CategoryRepository {
    suspend fun get(): List<Category>
}
