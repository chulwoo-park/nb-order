package dev.chulwoo.nb.order.features.cart.domain.repository

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.product.domain.model.Product

interface CartRepository {
    suspend fun get(): Cart

    suspend fun add(product: Product): Cart

    /**
     * 카트 내 상품 개수를 1 줄인다.
     */
    suspend fun remove(product: Product): Cart

    /**
     * 카트 내 상품을 제거한다.
     */
    suspend fun delete(product: Product): Cart

    suspend fun clear(): Cart
}
