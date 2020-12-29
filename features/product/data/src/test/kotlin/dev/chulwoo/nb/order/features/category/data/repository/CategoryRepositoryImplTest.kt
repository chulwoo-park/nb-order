package dev.chulwoo.nb.order.features.category.data.repository

import com.nhaarman.mockitokotlin2.*
import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.product.domain.model.Category
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
                onBlocking { get() } doAnswer { listOf(Category(1, "1"), Category(2, "2")) }
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
                onBlocking { get() } doAnswer { listOf(Category(1, "1"), Category(2, "2")) }
            }
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            val result = repository.get()
            verify(localSource).get()
            verify(remoteSource).get()
            assertThat(result, equalTo(listOf(Category(1, "1"), Category(2, "2"))))

        }
    }

    @Test
    fun `Given local error When invoke get Then save data from remote to local`() {
        runBlocking {
            val localSource = mock<CategoryLocalSource> {
                onBlocking { get() } doAnswer { throw Exception() }
            }
            val remoteSource = mock<CategoryRemoteSource> {
                onBlocking { get() } doAnswer { listOf(Category(1, "1"), Category(2, "2")) }
            }
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            repository.get()
            verify(localSource).get()
            verify(remoteSource).get()
            verify(localSource).set(listOf(Category(1, "1"), Category(2, "2")))
        }
    }

    @Test
    fun `Given save error When invoke get Then use remote data without error`() {
        runBlocking {
            val localSource = mock<CategoryLocalSource> {
                onBlocking { get() } doAnswer { throw Exception() }
                onBlocking { set(any()) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<CategoryRemoteSource> {
                onBlocking { get() } doAnswer { listOf(Category(1, "1"), Category(2, "2")) }
            }
            val repository = CategoryRepositoryImpl(localSource, remoteSource)

            val result = repository.get()
            verify(localSource).get()
            verify(remoteSource).get()
            verify(localSource).set(listOf(Category(1, "1"), Category(2, "2")))
            assertThat(result, equalTo(listOf(Category(1, "1"), Category(2, "2"))))
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

