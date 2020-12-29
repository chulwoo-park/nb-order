package dev.chulwoo.nb.order.features.category.data.repository

import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) : CategoryRepository {
    override suspend fun get(): List<Category> {
        return try {
            localSource.get()
        } catch (e: Exception) {
            val result = remoteSource.get()
            try {
                localSource.set(result)
            } catch (ignore: Exception) {
                // ignore local save error
            }

            result
        }
    }
}
