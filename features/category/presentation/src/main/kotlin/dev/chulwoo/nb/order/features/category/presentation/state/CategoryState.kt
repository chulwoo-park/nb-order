package dev.chulwoo.nb.order.features.category.presentation.state

import dev.chulwoo.nb.order.features.category.presentation.model.Category

sealed class CategoryState {

    object Initial : CategoryState()
    object Loading : CategoryState()
    data class Success(val data: List<Category>) : CategoryState()
    data class Failure(val error: Throwable) : CategoryState()
}
