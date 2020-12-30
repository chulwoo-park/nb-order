package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository

class ClearCart(private val repository: CartRepository) {

    suspend operator fun invoke(): Cart {
        return repository.clear()
    }
}
