package dev.chulwoo.nb.order.features.cart.data.repository

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository

class CartRepositoryImpl(private val localSource: CartLocalSource) : CartRepository {

    override suspend fun get(): Cart = localSource.get()

    override suspend fun add(productId: Int): Cart = localSource.add(productId)

    override suspend fun remove(productId: Int): Cart = localSource.remove(productId)

    override suspend fun clear(): Cart = localSource.clear()
}
