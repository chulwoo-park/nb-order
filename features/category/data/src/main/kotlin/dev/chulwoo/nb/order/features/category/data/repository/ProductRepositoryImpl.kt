package dev.chulwoo.nb.order.features.category.data.repository

import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Product
import dev.chulwoo.nb.order.features.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val localSource: ProductLocalSource,
    private val remoteSource: ProductRemoteSource
) : ProductRepository {

    override suspend fun get(categoryId: Int): List<Product> {
        TODO("Not yet implemented")
    }
}
