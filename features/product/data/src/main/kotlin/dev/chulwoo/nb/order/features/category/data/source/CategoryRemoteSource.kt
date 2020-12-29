package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.product.domain.model.Category

interface CategoryRemoteSource {
    suspend fun getCategories(): List<Category>
}
