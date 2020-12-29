package dev.chulwoo.nb.order.http.service

import dev.chulwoo.nb.order.http.model.CategoryDto
import retrofit2.http.GET


interface CategoryService {

    @GET("json/categories.json")
    suspend fun getCategories(): List<CategoryDto>
}
