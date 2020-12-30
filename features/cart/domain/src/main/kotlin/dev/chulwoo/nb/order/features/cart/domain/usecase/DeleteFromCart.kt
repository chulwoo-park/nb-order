package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.product.domain.model.Product

data class DeleteFromCartParam(val product: Product)

class DeleteFromCart(private val repository: CartRepository) {

    suspend operator fun invoke(param: DeleteFromCartParam): Cart {
        return repository.delete(param.product)
    }
}
