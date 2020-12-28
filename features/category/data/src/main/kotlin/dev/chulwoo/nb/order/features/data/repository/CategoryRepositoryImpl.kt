package dev.chulwoo.nb.order.features.data.repository

import dev.chulwoo.nb.order.features.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) : CategoryRepository {
    override suspend fun get(): List<Category> {
        TODO("Not yet implemented")
    }
}
