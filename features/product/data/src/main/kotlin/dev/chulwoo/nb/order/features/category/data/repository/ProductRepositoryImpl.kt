package dev.chulwoo.nb.order.features.category.data.repository

import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.product.domain.model.Product
import dev.chulwoo.nb.order.features.product.domain.repository.ProductRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ProductRepositoryImpl(
    private val localSource: ProductLocalSource,
    private val remoteSource: ProductRemoteSource
) : ProductRepository {

    override suspend fun get(categoryId: Int): List<Product> {
        return try {
            localSource.getProducts(categoryId)
        } catch (e: Exception) {
            val result = remoteSource.getProducts().groupBy { it.categoryId }.toMutableMap()
            try {
                if (!result.containsKey(categoryId)) {
                    result[categoryId] = listOf()
                }
                coroutineScope {
                    result.map { async { localSource.setProducts(it.key, it.value) } }.awaitAll()
                }
            } catch (ignore: Exception) {
                // ignore local save error
            }

            result[categoryId] ?: listOf()
        }
    }
}
