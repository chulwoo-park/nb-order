package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.product.domain.model.Product

data class RemoveFromCartParam(val product: Product)

class RemoveFromCart(private val repository: CartRepository) {

    suspend operator fun invoke(param: RemoveFromCartParam): Cart {
        return repository.remove(param.product)
    }
}
