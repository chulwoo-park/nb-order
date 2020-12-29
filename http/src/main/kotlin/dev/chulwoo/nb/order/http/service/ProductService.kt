package dev.chulwoo.nb.order.http.service

import dev.chulwoo.nb.order.http.model.CategoryDto
import dev.chulwoo.nb.order.http.model.ProductDto
import retrofit2.http.GET


interface ProductService {

    @GET("json/products.json")
    suspend fun getProducts(): List<ProductDto>
}
