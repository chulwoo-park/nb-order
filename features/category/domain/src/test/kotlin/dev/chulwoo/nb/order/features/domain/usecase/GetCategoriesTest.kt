package dev.chulwoo.nb.order.features.domain.usecase

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class GetCategoriesTest {

    @Test
    fun `When invoke GetCategories Then returns data from repository`() {
        runBlocking {
            val repository = mock<CategoryRepository> {
                onBlocking { get() } doAnswer { listOf(Category(1, "1"), Category(2, "2")) }
            }
            val getCategories = GetCategories(repository)
            val result = getCategories()
            verify(repository).get()
            assertEquals(listOf(Category(1, "1"), Category(2, "2")), result)
        }
    }

    @Test
    fun `Given error on repository When invoke GetCategories Then rethrows error`() {
        val repository = mock<CategoryRepository> {
            onBlocking { get() } doAnswer { throw Throwable() }
        }
        val getCategories = GetCategories(repository)
        assertThrows(Throwable::class.java) { runBlocking { getCategories() } }
    }
}
