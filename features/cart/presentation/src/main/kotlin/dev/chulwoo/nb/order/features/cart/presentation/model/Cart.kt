package dev.chulwoo.nb.order.features.cart.presentation.model

data class Cart(val products: List<CartItem> = listOf()) {
    val totalPrice: Double =
        products.fold(0.0) { acc, cartItem -> acc + cartItem.product.price * cartItem.count }
}
