package dev.chulwoo.nb.order.features.data.source

import dev.chulwoo.nb.order.features.domain.model.Category

interface CategoryLocalSource {
    suspend fun get(): List<Category>
}
