package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository

data class AddToCartParam(val productId: Int)

class AddToCart(private val repository: CartRepository) {

    suspend operator fun invoke(param: AddToCartParam): Cart {
        return repository.add(param.productId)
    }
}
