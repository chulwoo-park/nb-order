package dev.chulwoo.nb.order.http.api

import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.http.service.CategoryService
import retrofit2.Retrofit

class OrderApi(retrofit: Retrofit) : CategoryRemoteSource {

    private val categoryService: CategoryService = retrofit.create(CategoryService::class.java)

    override suspend fun get(): List<Category> {
        return categoryService.getCategories().map { Category(it.id, it.name) }
    }
}
