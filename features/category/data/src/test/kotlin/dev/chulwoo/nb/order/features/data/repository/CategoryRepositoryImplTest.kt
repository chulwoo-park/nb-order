package dev.chulwoo.nb.order.features.data.repository

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Category
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test

class CategoryRepositoryImplTest {
    @Test
    fun `Given local data When invoke get Then use local data only`() {
        runBlocking {
            val localSource = mock<CategoryLocalSource> {
                onBlocking { get() } doAnswer { listOf(Category(1), Category(2)) }
            }
            val remoteSource = mock<CategoryRemoteSource>()
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            repository.get()
            verify(localSource).get()
            verify(remoteSource, never()).get()
        }
    }

    @Test
    fun `Given local error When invoke get Then use remote data`() {
        runBlocking {
            val localSource = mock<CategoryLocalSource> {
                onBlocking { get() } doAnswer { throw Exception() }
            }
            val remoteSource = mock<CategoryRemoteSource> {
                onBlocking { get() } doAnswer { listOf(Category(1), Category(2)) }
            }
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            val result = repository.get()
            verify(localSource).get()
            verify(remoteSource).get()
            assertThat(result, equalTo(listOf(Category(1), Category(2))))

        }
    }

    @Test
    fun `Given local error and remote error When invoke get Then throw error`() {
        runBlocking {
            val localSource = mock<CategoryLocalSource> {
                onBlocking { get() } doAnswer { throw Exception() }
            }
            val remoteSource = mock<CategoryRemoteSource> {
                onBlocking { get() } doAnswer { throw Exception() }
            }
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            assertThrows(Exception::class.java) { runBlocking { repository.get() } }
            verify(localSource).get()
            verify(remoteSource).get()
        }
    }
}

