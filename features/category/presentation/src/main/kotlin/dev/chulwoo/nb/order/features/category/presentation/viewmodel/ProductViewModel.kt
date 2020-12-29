package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dev.chulwoo.nb.order.features.category.presentation.state.ProductState
import dev.chulwoo.nb.order.features.domain.usecase.GetProducts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val getProducts: GetProducts,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    lateinit var states: StateFlow<ProductState>

    fun load() {
        TODO("Not yet implemented")
    fun load(categoryId: Int) {
    }
}
