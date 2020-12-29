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
        val selectedIndex = findSelectedIndex()
        val previousIndex = selectedIndex - 1
        if (previousIndex >= 0) {
            select(previousIndex)
        }
    }

    fun selectNext() {
        val selectedIndex = findSelectedIndex()
        if (selectedIndex == -1) return

        val currentState = states.value
        val nextIndex = selectedIndex + 1
        if (nextIndex < (currentState as CategoryState.Success).data.size) {
            select(nextIndex)
        }
    }

    /**
     * 찾을 수 없거나 현재 상태가 [CategoryState.Success]가 아닌 경우, -1 리턴
     */
    private fun findSelectedIndex(): Int {
        val currentState = _states.value
        if (currentState !is CategoryState.Success) return -1
        return currentState.data.indexOfFirst { it.isSelected }
    }
}
