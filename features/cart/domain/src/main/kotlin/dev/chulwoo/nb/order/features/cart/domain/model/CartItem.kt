package dev.chulwoo.nb.order.features.cart.domain.model

import dev.chulwoo.nb.order.features.product.domain.model.Product

data class CartItem(val product: Product, val count: Int)
