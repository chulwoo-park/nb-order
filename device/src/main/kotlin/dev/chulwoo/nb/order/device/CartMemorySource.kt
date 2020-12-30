package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem
import dev.chulwoo.nb.order.features.product.domain.model.Product

class CartMemorySource : CartLocalSource {
    private val productCountMap: MutableMap<Product, CartItem> = linkedMapOf()

    override suspend fun get(): Cart = Cart(productCountMap.values.toList())

    override suspend fun add(product: Product): Cart {
        val cartItem = if (productCountMap.containsKey(product)) {
            productCountMap[product]!!
        } else {
            CartItem(product)
        }

        productCountMap[product] = cartItem.copy(count = cartItem.count + 1)

        return get()
    }

    override suspend fun remove(product: Product): Cart {
        if (productCountMap.containsKey(product)) {
            val cartItem = productCountMap[product]!!.let {
                it.copy(count = it.count - 1)
            }
            if (cartItem.count == 0) {
                productCountMap.remove(product)
            } else {
                productCountMap[product] = cartItem
            }
        }
        return get()
    }

    override suspend fun clear(): Cart {
        productCountMap.clear()
        return get()
    }
}
