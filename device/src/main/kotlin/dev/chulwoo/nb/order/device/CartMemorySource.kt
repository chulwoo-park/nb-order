package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem

class CartMemorySource : CartLocalSource {
    private val productCountMap: MutableMap<Int, Int> = mutableMapOf()

    override suspend fun get(): Cart = Cart(productCountMap.map { CartItem(it.key, it.value) })

    override suspend fun add(productId: Int): Cart {
        if (productCountMap.containsKey(productId)) {
            productCountMap[productId] = productCountMap[productId]!! + 1
        } else {
            productCountMap[productId] = 1
        }

        return get()
    }

    override suspend fun remove(productId: Int): Cart {
        if (productCountMap.containsKey(productId)) {
            productCountMap[productId] = productCountMap[productId]!! - 1
            if (productCountMap[productId] == 0) {
                productCountMap.remove(productId)
            }
        }
        return get()
    }

    override suspend fun clear(): Cart {
        productCountMap.clear()
        return get()
    }
}
