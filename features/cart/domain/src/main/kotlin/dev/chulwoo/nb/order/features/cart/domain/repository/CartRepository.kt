package dev.chulwoo.nb.order.features.cart.domain.repository

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.product.domain.model.Product

interface CartRepository {
    suspend fun get(): Cart

    suspend fun add(product: Product): Cart

    suspend fun remove(product: Product): Cart

    suspend fun clear(): Cart
}
