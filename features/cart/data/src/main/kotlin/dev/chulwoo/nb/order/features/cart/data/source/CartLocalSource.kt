package dev.chulwoo.nb.order.features.cart.data.source

import dev.chulwoo.nb.order.features.cart.domain.model.Cart

interface CartLocalSource {

    suspend fun get(): Cart

    suspend fun add(productId: Int): Cart

    suspend fun remove(productId: Int): Cart

    suspend fun clear(): Cart
}
