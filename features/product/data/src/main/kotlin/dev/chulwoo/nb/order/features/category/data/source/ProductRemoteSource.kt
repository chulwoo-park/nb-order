package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.product.domain.model.Product

interface ProductRemoteSource {
    suspend fun getProducts(): List<Product>
}
