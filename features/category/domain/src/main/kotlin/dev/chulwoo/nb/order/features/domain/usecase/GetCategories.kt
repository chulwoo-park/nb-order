package dev.chulwoo.nb.order.features.domain.usecase

import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository

class GetCategories(private val repository: CategoryRepository) {

    suspend operator fun invoke(): List<Category> = repository.get()
}
