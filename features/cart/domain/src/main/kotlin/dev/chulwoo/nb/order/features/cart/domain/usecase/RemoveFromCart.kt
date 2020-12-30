package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository

data class RemoveFromCartParam(val productId: Int)

class RemoveFromCart(private val repository: CartRepository) {

    suspend operator fun invoke(param: RemoveFromCartParam): Cart {
        TODO()
    }
}
