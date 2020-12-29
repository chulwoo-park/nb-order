package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import dev.chulwoo.nb.order.features.category.presentation.state.ProductState
import dev.chulwoo.nb.order.features.domain.usecase.GetProducts
import dev.chulwoo.nb.order.features.domain.usecase.GetProductsParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProducts: GetProducts,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _states: MutableStateFlow<ProductState> = MutableStateFlow(ProductState.Initial)
    val states: StateFlow<ProductState> = _states

    fun load(categoryId: Int) {
        if (_states.value is ProductState.Loading) return

        viewModelScope.launch(dispatcher) {
            _states.value = ProductState.Loading
            _states.value = try {
                ProductState.Success(getProducts(GetProductsParam(categoryId)).map {
                    Product(it.id, it.categoryId, it.price, it.name, it.imageUrl)
                })
            } catch (e: Exception) {
                ProductState.Failure(e)
            }
        }
    }
}
