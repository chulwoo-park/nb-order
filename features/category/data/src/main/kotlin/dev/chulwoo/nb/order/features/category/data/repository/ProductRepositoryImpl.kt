package dev.chulwoo.nb.order.features.category.data.repository

import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Product
import dev.chulwoo.nb.order.features.domain.repository.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ProductRepositoryImpl(
    private val localSource: ProductLocalSource,
    private val remoteSource: ProductRemoteSource
) : ProductRepository {

    override suspend fun get(categoryId: Int): List<Product> {
        return try {
            localSource.get(categoryId)
        } catch (e: Exception) {
            val result = remoteSource.get().groupBy { it.categoryId }.toMap()
            try {
                coroutineScope {
                    result.map { async { localSource.set(it.key, it.value) } }.awaitAll()
                }
            } catch (ignore: Exception) {
                // ignore local save error
            }

            result[categoryId] ?: listOf()
        }
    }
}
