package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.*
import dev.chulwoo.nb.order.common.test.presentation.MainCoroutineScopeRule
import dev.chulwoo.nb.order.features.category.presentation.model.Category
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.product.domain.repository.CategoryRepository
import dev.chulwoo.nb.order.features.product.domain.usecase.GetCategories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.chulwoo.nb.order.features.product.domain.model.Category as CategoryEntity

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class CategoryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineScopeRule()


    private lateinit var observer: Observer<CategoryState>
    private lateinit var repository: CategoryRepository
    private lateinit var getCategories: GetCategories
    private lateinit var viewModel: CategoryViewModel

    @Before
    fun setUp() {
        observer = mock {}
        repository = mock {}
        getCategories = GetCategories(repository)
        viewModel = CategoryViewModel(getCategories, mainCoroutineRule.dispatcher)
        viewModel.states.asLiveData().observeForever(observer)
    }

    @Test
    fun testLoadSuccess() {
        // Given data When invoke load Then emit states loading - success
        runBlocking {
            whenever(repository.get()).thenAnswer { listOf<Category>() }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(isA<CategoryState.Success>())
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
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(isA<CategoryState.Failure>())
        }
    }

    @Test
    fun testDefaultSelectedCategory() {
        // Given data When invoke load Then first category should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", true),
                        Category(2, "2", false)
                    )
                )
            )
        }
    }

    @Test
    fun testCategorySelect() {
        // When invoke select Then that category should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.select(1)
        }

        verify(observer).onChanged(
            CategoryState.Success(
                listOf(Category(1, "1", false), Category(2, "2", true))
            )
        )
    }

    @Test
    fun testCategorySelect_whenNotSucceedState() {
        // Given not succeed state When invoke select Then that state should be not changed
        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.select(1)
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun testCategorySelectNext() {
        // When invoke selectNext Then next index should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.selectNext()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", true),
                        Category(2, "2", false)
                    )
                )
            )
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", false),
                        Category(2, "2", true)
                    )
                )
            )
        }
    }

    @Test
    fun testCategorySelectNext_wheNotSucceedState() {
        // Given not succeed state When invoke selectNext Then state should be not changed
        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.selectNext()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verifyNoMoreInteractions(observer)
        }
    }


    @Test
    fun testCategorySelectBefore() {
        // When invoke selectBefore Then previous index should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.select(1)
            viewModel.selectBefore()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", true),
                        Category(2, "2", false)
                    )
                )
            )
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", false),
                        Category(2, "2", true)
                    )
                )
            )
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", true),
                        Category(2, "2", false)
                    )
                )
            )
        }
    }

    @Test
    fun testCategorySelectBefore_wheNotSucceedState() {
        // Given not succeed state When invoke selectBefore Then state should be not changed
        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.selectBefore()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun testCategorySelectBefore_firstIndex() {
        // Given selected first index When invoke selectBefore Then state should be not changed
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.selectBefore()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(isA<CategoryState.Success>())
            verifyNoMoreInteractions(observer)
        }
    }

    @Test
    fun testCategorySelectNext_lastIndex() {
        // Given selected last index When invoke selectNext Then state should be not changed
        runBlocking {
            whenever(repository.get()).thenAnswer {
                listOf(
                    CategoryEntity(1, "1"),
                    CategoryEntity(2, "2")
                )
            }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.select(1)
            viewModel.selectNext()
        }

        inOrder(observer) {
            verify(observer).onChanged(CategoryState.Initial)
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", true),
                        Category(2, "2", false),
                    )
                )
            )
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, "1", false),
                        Category(2, "2", true),
                    )
                )
            )
            verifyNoMoreInteractions(observer)
        }
    }
}
