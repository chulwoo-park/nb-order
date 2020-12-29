package dev.chulwoo.nb.order.features.category.data.source

import dev.chulwoo.nb.order.features.domain.model.Product

interface ProductRemoteSource {
    suspend fun get(): List<Product>
}
