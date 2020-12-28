package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.domain.model.Category

interface CategoryRemoteSource {
    suspend fun get(): List<Category>
}
