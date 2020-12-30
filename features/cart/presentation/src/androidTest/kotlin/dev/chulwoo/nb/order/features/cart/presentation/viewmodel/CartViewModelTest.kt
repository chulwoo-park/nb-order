package dev.chulwoo.nb.order.features.cart.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import dev.chulwoo.nb.order.common.test.presentation.MainCoroutineScopeRule
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.*
import dev.chulwoo.nb.order.features.cart.presentation.model.Cart
import dev.chulwoo.nb.order.features.cart.presentation.model.CartItem
import dev.chulwoo.nb.order.features.cart.presentation.state.CartState
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.chulwoo.nb.order.features.cart.domain.model.Cart as CartEntity
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem as CartItemEntity
import dev.chulwoo.nb.order.features.product.domain.model.Product as ProductEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CartViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()


    private lateinit var observer: Observer<CartState>
    private lateinit var repository: CartRepository
    private lateinit var getCart: GetCart
    private lateinit var addToCart: AddToCart
    private lateinit var removeFromCart: RemoveFromCart
    private lateinit var deleteFromCart: DeleteFromCart
    private lateinit var clearCart: ClearCart
    private lateinit var viewModel: CartViewModel

    private val productEntities = listOf(
        ProductEntity(1, 1, 2500.0, "product", ""),
        ProductEntity(2, 1, 2500.0, "product", ""),
        ProductEntity(3, 2, 2500.0, "product", "")
    )

    private val products = listOf(
        Product(1, 1, 2500.0, "product", ""),
        Product(2, 1, 2500.0, "product", ""),
        Product(3, 2, 2500.0, "product", "")
    )

    @Before
    fun setUp() {
        observer = mock {}
        repository = mock {}
        getCart = GetCart(repository)
        addToCart = AddToCart(repository)
        removeFromCart = RemoveFromCart(repository)
        deleteFromCart = DeleteFromCart(repository)
        clearCart = ClearCart(repository)
        viewModel = CartViewModel(
            getCart,
            addToCart,
            removeFromCart,
            deleteFromCart,
            clearCart,
            mainCoroutineRule.dispatcher
        )
        viewModel.states.asLiveData().observeForever(observer)
    }

    @Test
    fun testLoadSuccess() {
        // Given data When invoke load Then emit states loading - success
        runBlocking {
            whenever(repository.get()).thenAnswer { CartEntity() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
        }

        inOrder(observer) {
            verify(observer).onChanged(CartState.Initial)
            verify(observer).onChanged(CartState.Loading)
            verify(observer).onChanged(isA<CartState.Success>())
        }
    }

    @Test
    fun testLoadFailure() {
        // Given error When invoke load Then emit states loading - failure
        runBlocking {
            whenever(repository.get()).thenAnswer { throw  Exception() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
        }

        inOrder(observer) {
            verify(observer).onChanged(CartState.Initial)
            verify(observer).onChanged(CartState.Loading)
            verify(observer).onChanged(isA<CartState.Failure>())
        }
    }

    @Test
    fun testAddToCartSuccess() {
        // When invoke addToCart Then state should be updated using data from use case
        runBlocking {
            whenever(repository.add(any())).thenAnswer {
                CartEntity(listOf(CartItemEntity(it.arguments[0] as ProductEntity, 1)))
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.add(products[0])
        }

        verify(observer).onChanged(
            CartState.Success(Cart(listOf(CartItem(products[0], 1))))
        )
    }

    @Test
    fun testAddToCartFailure() {
        // Given error When invoke addToCart Then state should be updated failure with previous data
        val error = Exception()
        runBlocking {
            whenever(repository.add(productEntities[0])).thenAnswer {
                CartEntity(listOf(CartItemEntity(productEntities[0], 1)))
            }
            whenever(repository.add(productEntities[1])).thenAnswer { throw error }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.add(products[0])
            viewModel.add(products[1])
        }

        verify(observer).onChanged(
            CartState.Failure(
                Cart(listOf(CartItem(products[0], 1))),
                error,
            )
        )
    }

    @Test
    fun testRemoveFromCart() {
        // When invoke removeFromCart Then state should be updated using data from use case
        runBlocking {
            whenever(repository.remove(any())).thenAnswer { CartEntity() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.remove(products[0])
        }

        verify(observer).onChanged(CartState.Success(Cart()))
    }

    @Test
    fun testRemoveFromCartFailure() {
        // Given error When invoke removeFromCart Then state should be updated failure with previous data
        val error = Exception()
        runBlocking {
            whenever(repository.add(productEntities[0])).thenAnswer {
                CartEntity(listOf(CartItemEntity(productEntities[0], 1)))
            }
            whenever(repository.remove(productEntities[0])).thenAnswer { throw error }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.add(products[0])
            viewModel.remove(products[0])
        }

        verify(observer).onChanged(
            CartState.Failure(
                Cart(listOf(CartItem(products[0], 1))),
                error,
            )
        )
    }

    @Test
    fun testDeleteFromCart() {
        // When invoke deleteFromCart Then state should be updated using data from use case
        runBlocking {
            whenever(repository.delete(any())).thenAnswer { CartEntity() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.delete(products[0])
        }

        verify(observer).onChanged(CartState.Success(Cart()))
    }

    @Test
    fun testDeleteFromCartFailure() {
        // Given error When invoke deleteFromCart Then state should be updated failure with previous data
        val error = Exception()
        runBlocking {
            whenever(repository.add(productEntities[0])).thenAnswer {
                CartEntity(listOf(CartItemEntity(productEntities[0], 1)))
            }
            whenever(repository.delete(productEntities[0])).thenAnswer { throw error }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.add(products[0])
            viewModel.delete(products[0])
        }

        verify(observer).onChanged(
            CartState.Failure(
                Cart(listOf(CartItem(products[0], 1))),
                error,
            )
        )
    }

    @Test
    fun testClearCart() {
        // When invoke clearCart Then state should be updated to empty cart
        runBlocking {
            whenever(repository.clear()).thenAnswer { CartEntity() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.clear()
        }

        verify(observer).onChanged(CartState.Success(Cart()))
    }

    @Test
    fun testClearCartFailure() {
        // Given error When invoke clearCart Then state should be updated failure with previous data
        val error = Exception()
        runBlocking {
            whenever(repository.add(productEntities[0])).thenAnswer {
                CartEntity(listOf(CartItemEntity(productEntities[0], 1)))
            }
            whenever(repository.clear()).thenAnswer { throw error }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.add(products[0])
            viewModel.clear()
        }

        verify(observer).onChanged(
            CartState.Failure(
                Cart(listOf(CartItem(products[0], 1))),
                error,
            )
        )
    }
}

