package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.product.domain.model.Category

interface CategoryLocalSource {
    suspend fun getCategories(): List<Category>

    suspend fun setCategories(categories: List<Category>)
}
