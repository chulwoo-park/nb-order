package dev.chulwoo.nb.order.http.api

import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.product.domain.model.Category
import dev.chulwoo.nb.order.features.product.domain.model.Product
import dev.chulwoo.nb.order.http.service.CategoryService
import dev.chulwoo.nb.order.http.service.ProductService
import retrofit2.Retrofit

class OrderApi(retrofit: Retrofit) : CategoryRemoteSource, ProductRemoteSource {

    private val categoryService: CategoryService = retrofit.create(CategoryService::class.java)
    private val productService: ProductService = retrofit.create(ProductService::class.java)

    override suspend fun getCategories(): List<Category> {
        return categoryService.getCategories().map { Category(it.id, it.name) }
    }

    override suspend fun getProducts(): List<Product> {
        return productService.getProducts()
            .map { Product(it.id, it.category_id, it.price.toDouble(), it.name, it.image) }
    }
}
