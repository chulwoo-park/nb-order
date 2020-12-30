package dev.chulwoo.nb.order.features.cart.presentation.state

import dev.chulwoo.nb.order.features.cart.presentation.model.Cart

sealed class CartState {
    object Initial : CartState()
    object Loading : CartState()
    data class Success(val data: Cart) : CartState()
    data class Failure(val error: Throwable) : CartState()
}
