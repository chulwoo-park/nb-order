package dev.chulwoo.nb.order.features.category.data.repository

import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.product.domain.model.Category
import dev.chulwoo.nb.order.features.product.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) : CategoryRepository {
    override suspend fun get(): List<Category> {
        return try {
            localSource.getCategories()
        } catch (e: Exception) {
            val result = remoteSource.getCategories()
            try {
                localSource.setCategories(result)
            } catch (ignore: Exception) {
                // ignore local save error
            }

            result
        }
    }
}
