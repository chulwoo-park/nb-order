package dev.chulwoo.nb.order.features.cart.data.repository

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository

class CartRepositoryImpl(localSource: CartLocalSource) : CartRepository {

    override suspend fun get(): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun add(productId: Int): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun remove(productId: Int): Cart {
        TODO("Not yet implemented")
    }

    override suspend fun clear(): Cart {
        TODO("Not yet implemented")
    }
}
