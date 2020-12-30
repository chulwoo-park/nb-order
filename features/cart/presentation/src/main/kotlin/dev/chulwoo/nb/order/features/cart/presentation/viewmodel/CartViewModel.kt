package dev.chulwoo.nb.order.features.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chulwoo.nb.order.features.cart.domain.usecase.*
import dev.chulwoo.nb.order.features.cart.presentation.model.Cart
import dev.chulwoo.nb.order.features.cart.presentation.model.CartItem
import dev.chulwoo.nb.order.features.cart.presentation.state.CartState
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.chulwoo.nb.order.features.cart.domain.model.Cart as CartEntity
import dev.chulwoo.nb.order.features.product.domain.model.Product as ProductEntity

class CartViewModel(
    private val getCart: GetCart,
    private val addToCart: AddToCart,
    private val removeFromCart: RemoveFromCart,
    private val deleteFromCart: DeleteFromCart,
    private val clearCart: ClearCart,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _states: MutableStateFlow<CartState> = MutableStateFlow(CartState.Initial)
    val states: StateFlow<CartState> = _states

    fun load() {
        if (states.value is CartState.Loading) return

        _states.value = CartState.Loading

        viewModelScope.launch(dispatcher) {
            _states.value = try {
                val result = getCart()
                CartState.Success(result.toPresentationModel())
            } catch (e: Exception) {
                CartState.Failure(getDataFromPreviousState(), e)
            }
        }
    }

    fun add(product: Product) {
        if (states.value is CartState.Loading) return

        viewModelScope.launch(dispatcher) {
            _states.value = try {
                val result = addToCart(AddToCartParam(product.toEntity()))
                CartState.Success(result.toPresentationModel())
            } catch (e: Exception) {
                CartState.Failure(getDataFromPreviousState(), e)
            }
        }
    }

    fun remove(product: Product) {
        if (states.value is CartState.Loading) return

        viewModelScope.launch(dispatcher) {
            _states.value = try {
                val result = removeFromCart(RemoveFromCartParam(product.toEntity()))
                CartState.Success(result.toPresentationModel())
            } catch (e: Exception) {
                CartState.Failure(getDataFromPreviousState(), e)
            }
        }
    }

    fun delete(product: Product) {
        TODO()
    }

    fun clear() {
        if (states.value is CartState.Loading) return

        viewModelScope.launch(dispatcher) {
            _states.value = try {
                val result = clearCart()
                CartState.Success(result.toPresentationModel())
            } catch (e: Exception) {
                CartState.Failure(getDataFromPreviousState(), e)
            }
        }
    }

    private fun getDataFromPreviousState(): Cart {
        return if (_states.value is CartState.FinishLoadingState) {
            (_states.value as CartState.FinishLoadingState).data
        } else {
            Cart()
        }
    }
}

fun CartEntity.toPresentationModel(): Cart =
    Cart(products.map { CartItem(it.product.toPresentationModel(), it.count) })

fun ProductEntity.toPresentationModel(): Product = Product(id, categoryId, price, name, imageUrl)

fun Product.toEntity(): ProductEntity = ProductEntity(id, categoryId, price, name, imageUrl)
