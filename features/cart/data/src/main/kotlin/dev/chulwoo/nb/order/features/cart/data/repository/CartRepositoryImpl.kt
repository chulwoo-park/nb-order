package dev.chulwoo.nb.order.features.cart.data.repository

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.product.domain.model.Product

class CartRepositoryImpl(private val localSource: CartLocalSource) : CartRepository {

    override suspend fun get(): Cart = localSource.get()

    override suspend fun add(product: Product): Cart = localSource.add(product)

    override suspend fun remove(product: Product): Cart = localSource.remove(product)

    override suspend fun clear(): Cart = localSource.clear()
}
