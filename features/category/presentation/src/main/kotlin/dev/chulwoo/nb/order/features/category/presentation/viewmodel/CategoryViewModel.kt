package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.domain.usecase.GetCategories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel(
    private val getCategories: GetCategories,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val states: StateFlow<CategoryState> = MutableStateFlow(CategoryState.Initial)

    fun load() {
        TODO()
    }

    fun select(index: Int) {
        TODO()
    }
}
