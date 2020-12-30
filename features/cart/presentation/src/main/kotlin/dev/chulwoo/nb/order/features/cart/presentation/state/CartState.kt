package dev.chulwoo.nb.order.features.cart.presentation.state

import dev.chulwoo.nb.order.features.cart.presentation.model.Cart

sealed class CartState {
    object Initial : CartState()
    object Loading : CartState()

    interface FinishLoadingState {
        val data: Cart
    }

    data class Success(override val data: Cart) : CartState(), FinishLoadingState
    data class Failure(override val data: Cart, val error: Throwable) : CartState(),
        FinishLoadingState
}
