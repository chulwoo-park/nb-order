package dev.chulwoo.nb.order.features.domain.model

data class Product(
    val id: Int,
    val categoryId: Int,
    val price: Double,
    val name: String,
    val imageUrl: String
)
