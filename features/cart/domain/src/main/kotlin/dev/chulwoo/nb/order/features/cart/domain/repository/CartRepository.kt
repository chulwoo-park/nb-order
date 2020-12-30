package dev.chulwoo.nb.order.features.cart.domain.repository

import dev.chulwoo.nb.order.features.cart.domain.model.Cart

interface CartRepository {
    suspend fun getCart(): Cart

    suspend fun add(productId: Int): Cart
}
