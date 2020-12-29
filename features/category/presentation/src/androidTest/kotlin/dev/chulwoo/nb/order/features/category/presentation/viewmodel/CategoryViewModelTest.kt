package dev.chulwoo.nb.order.features.category.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.chulwoo.nb.order.common.test.presentation.MainCoroutineScopeRule
import dev.chulwoo.nb.order.features.category.presentation.model.Category
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository
import dev.chulwoo.nb.order.features.domain.usecase.GetCategories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.chulwoo.nb.order.features.domain.model.Category as CategoryEntity

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
            whenever(repository.get()).thenAnswer { listOf(CategoryEntity(1), CategoryEntity(2)) }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            verify(observer).onChanged(CategoryState.Initial)
            viewModel.load()
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(listOf(Category(1), Category(2)))
            )
        }
    }

    @Test
    fun testLoadFailure() {
        // Given error When invoke load Then emit states loading - failure
        val error = Exception()
        runBlocking {
            whenever(repository.get()).thenAnswer { throw  error }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            verify(observer).onChanged(CategoryState.Initial)
            viewModel.load()
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(CategoryState.Failure(error))
        }
    }

    @Test
    fun testDefaultSelectedCategory() {
        // Given data When invoke load Then first category should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer { listOf(CategoryEntity(1), CategoryEntity(2)) }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            verify(observer).onChanged(CategoryState.Initial)
            viewModel.load()
            verify(observer).onChanged(CategoryState.Loading)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(
                        Category(1, true),
                        Category(2, false)
                    )
                )
            )
        }
    }

    @Test
    fun testCategorySelect() {
        // When invoke select Then that category should be selected
        runBlocking {
            whenever(repository.get()).thenAnswer { listOf(CategoryEntity(1), CategoryEntity(2)) }
        }

        mainCoroutineRule.dispatcher.runBlockingTest {
            viewModel.load()
            viewModel.select(1)
            verify(observer).onChanged(
                CategoryState.Success(
                    listOf(Category(1, false), Category(2, true))
                )
            )
        }
    }
}
