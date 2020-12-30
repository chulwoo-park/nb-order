package dev.chulwoo.nb.order.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dev.chulwoo.nb.order.features.cart.domain.usecase.AddToCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.ClearCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.GetCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.RemoveFromCart
import dev.chulwoo.nb.order.features.cart.presentation.state.CartState
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow

class CartViewModel(
    private val getCart: GetCart,
    private val addToCart: AddToCart,
    private val removeFromCart: RemoveFromCart,
    private val clearCart: ClearCart,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    lateinit var states: StateFlow<CartState>

    fun load() {
        TODO()
    }

    fun addToCart(product: Product) {
        TODO()
    }

    fun removeFromCart(product: Product) {
        TODO()
    }

    fun clearCart() {
        TODO()
    }
}
