package dev.chulwoo.nb.order.http.model

data class ProductDto(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val price: Int,
    val image: String
)
