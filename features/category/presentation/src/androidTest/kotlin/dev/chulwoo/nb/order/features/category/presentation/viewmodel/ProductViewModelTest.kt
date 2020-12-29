package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.chulwoo.nb.order.common.test.presentation.MainCoroutineScopeRule
import dev.chulwoo.nb.order.features.category.presentation.model.Product
import dev.chulwoo.nb.order.features.category.presentation.state.ProductState
import dev.chulwoo.nb.order.features.domain.repository.ProductRepository
import dev.chulwoo.nb.order.features.domain.usecase.GetProducts
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.chulwoo.nb.order.features.domain.model.Product as ProductEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()


    private lateinit var observer: Observer<ProductState>
    private lateinit var repository: ProductRepository
    private lateinit var getProducts: GetProducts
    private lateinit var viewModel: ProductViewModel

    private val pe1 = ProductEntity(1, 1, 0.0, "1", "")
    private val pe2 = ProductEntity(2, 1, 0.0, "2", "")
    private val p1 = Product(1, 1, 0.0, "1", "")
    private val p2 = Product(2, 1, 0.0, "2", "")

    @Before
    fun setUp() {
        observer = mock {}
        repository = mock {}
        getProducts = GetProducts(repository)
        viewModel = ProductViewModel(getProducts, mainCoroutineRule.dispatcher)
        viewModel.states.asLiveData().observeForever(observer)
    }

    @Test
    fun testLoadSuccess() {
        // Given data When invoke load Then emit states loading - success
        runBlocking {
            whenever(repository.get(1)).thenAnswer { listOf(pe1, pe2) }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load(1)
        }

        inOrder(observer) {
            verify(observer).onChanged(ProductState.Initial)
            verify(observer).onChanged(ProductState.Loading)
            verify(observer).onChanged(ProductState.Success(listOf(p1, p2)))
        }
    }

    @Test
    fun testLoadFailure() {
        // Given error When invoke load Then emit states loading - failure
        runBlocking {
            whenever(repository.get(1)).thenAnswer { throw  Exception() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load(1)
        }

        inOrder(observer) {
            verify(observer).onChanged(ProductState.Initial)
            verify(observer).onChanged(ProductState.Loading)
            verify(observer).onChanged(isA<ProductState.Failure>())
        }
    }
}
