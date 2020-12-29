package dev.chulwoo.nb.order.features.product.domain.usecase

import dev.chulwoo.nb.order.features.product.domain.model.Category
import dev.chulwoo.nb.order.features.product.domain.repository.CategoryRepository

class GetCategories(private val repository: CategoryRepository) {

    suspend operator fun invoke(): List<Category> = repository.get()
}
