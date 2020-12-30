package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart

class CartMemorySource : CartLocalSource {
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
