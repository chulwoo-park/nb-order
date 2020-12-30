package dev.chulwoo.nb.order.features.cart.domain.usecase

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.product.domain.model.Product

data class AddToCartParam(val product: Product)

class AddToCart(private val repository: CartRepository) {

    suspend operator fun invoke(param: AddToCartParam): Cart {
        return repository.add(param.product)
    }
}
