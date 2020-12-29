package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.product.domain.model.Category

interface CategoryLocalSource {
    suspend fun get(): List<Category>

    suspend fun set(categories: List<Category>)
}
