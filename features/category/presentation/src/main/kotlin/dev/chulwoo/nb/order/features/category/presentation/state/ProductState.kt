package dev.chulwoo.nb.order.features.category.presentation.state

import dev.chulwoo.nb.order.features.category.presentation.model.Product

sealed class ProductState {

    object Initial : ProductState()
    object Loading : ProductState()
    data class Success(val data: List<Product>) : ProductState()
    data class Failure(val error: Throwable) : ProductState()
}
