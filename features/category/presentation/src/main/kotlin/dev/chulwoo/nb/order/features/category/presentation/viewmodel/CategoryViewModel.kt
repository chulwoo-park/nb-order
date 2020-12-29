package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chulwoo.nb.order.features.category.presentation.model.Category
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.domain.usecase.GetCategories
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val getCategories: GetCategories,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _states: MutableStateFlow<CategoryState> = MutableStateFlow(CategoryState.Initial)
    val states: StateFlow<CategoryState> = _states

    fun load() {
        if (_states.value is CategoryState.Loading) return

        viewModelScope.launch(dispatcher) {
            _states.value = CategoryState.Loading
            _states.value = try {
                CategoryState.Success(getCategories().mapIndexed { index, entity ->
                    Category(entity.id, entity.name, isSelected = index == 0)
                })
            } catch (e: Exception) {
                CategoryState.Failure(e)
            }
        }
    }

    fun select(index: Int) {
        val currentState = _states.value
        if (currentState !is CategoryState.Success) return
        _states.value = CategoryState.Success(currentState.data.mapIndexed { i, category ->
            category.copy(isSelected = i == index)
        })
    }

    fun selectBefore() {
    }

    fun selectNext() {
    }
}
