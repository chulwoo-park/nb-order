package dev.chulwoo.nb.order.features.category.data.repository

import com.nhaarman.mockitokotlin2.*
import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test

class ProductsRepositoryImplTest {
    private val p1 = Product(1, 1, 0.0, "1", "")
    private val p2 = Product(2, 1, 0.0, "2", "")
    private val p3 = Product(3, 2, 0.0, "3", "")

    @Test
    fun `Given local data When invoke get Then use local data only`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { get(1) } doAnswer { listOf(p1, p2, p3) }
            }
            val remoteSource = mock<ProductRemoteSource>()
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            repository.get(1)
            verify(localSource).get(1)
            verify(remoteSource, never()).get()
        }
    }

    @Test
    fun `Given local error When invoke get Then use remote data`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { get(1) } doAnswer { throw Exception() }
                onBlocking { set(any(), any()) } doAnswer {}
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { get() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            val result = repository.get(1)
            verify(localSource).get(1)
            verify(remoteSource).get()
            assertThat(result, equalTo(listOf(p1, p2)))

        }
    }

    @Test
    fun `Given local error When invoke get Then save data from remote to local`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { get(1) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { get() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            repository.get(1)
            verify(localSource).get(1)
            verify(remoteSource).get()
            verify(localSource).set(1, listOf(p1, p2))
            verify(localSource).set(2, listOf(p3))
        }
    }

    @Test
    fun `Given save error When invoke get Then use remote data without error`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { get(1) } doAnswer { throw Exception() }
                onBlocking { set(any(), any()) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { get() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            val result = repository.get(1)
            verify(localSource).get(1)
            verify(remoteSource).get()
            assertThat(result, equalTo(listOf(p1, p2)))
        }
    }

    @Test
    fun `Given local error and remote error When invoke get Then throw error`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { get(1) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { get() } doAnswer { throw Exception() }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            assertThrows(Exception::class.java) { runBlocking { repository.get(1) } }
            verify(localSource).get(1)
            verify(remoteSource).get()
        }
    }
}

