package dev.chulwoo.nb.order.features.domain.repository

import dev.chulwoo.nb.order.features.domain.model.Category

interface CategoryRepository {
    suspend fun get(): List<Category>
}
