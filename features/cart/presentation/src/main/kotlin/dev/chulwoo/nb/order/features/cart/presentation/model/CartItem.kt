package dev.chulwoo.nb.order.features.cart.presentation.model

import dev.chulwoo.nb.order.features.product.domain.model.Product

data class CartItem(val product: Product, val count: Int = 0)
