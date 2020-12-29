package dev.chulwoo.nb.order.features.category.data.repository

import com.nhaarman.mockitokotlin2.*
import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.product.domain.model.Product
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
                onBlocking { getProducts(1) } doAnswer { listOf(p1, p2, p3) }
            }
            val remoteSource = mock<ProductRemoteSource>()
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            repository.get(1)
            verify(localSource).getProducts(1)
            verify(remoteSource, never()).getProducts()
        }
    }

    @Test
    fun `Given local error When invoke get Then use remote data`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { getProducts(1) } doAnswer { throw Exception() }
                onBlocking { setProducts(any(), any()) } doAnswer {}
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { getProducts() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            val result = repository.get(1)
            verify(localSource).getProducts(1)
            verify(remoteSource).getProducts()
            assertThat(result, equalTo(listOf(p1, p2)))

        }
    }

    @Test
    fun `Given local error When invoke get Then save data from remote to local`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { getProducts(1) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { getProducts() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            repository.get(1)
            verify(localSource).getProducts(1)
            verify(remoteSource).getProducts()
            verify(localSource).setProducts(1, listOf(p1, p2))
            verify(localSource).setProducts(2, listOf(p3))
        }
    }

    @Test
    fun `Given save error When invoke get Then use remote data without error`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { getProducts(1) } doAnswer { throw Exception() }
                onBlocking { setProducts(any(), any()) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { getProducts() } doAnswer { listOf(p1, p2, p3) }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            val result = repository.get(1)
            verify(localSource).getProducts(1)
            verify(remoteSource).getProducts()
            assertThat(result, equalTo(listOf(p1, p2)))
        }
    }

    @Test
    fun `Given local error and remote error When invoke get Then throw error`() {
        runBlocking {
            val localSource = mock<ProductLocalSource> {
                onBlocking { getProducts(1) } doAnswer { throw Exception() }
            }
            val remoteSource = mock<ProductRemoteSource> {
                onBlocking { getProducts() } doAnswer { throw Exception() }
            }
            val repository = ProductRepositoryImpl(localSource, remoteSource)

            assertThrows(Exception::class.java) { runBlocking { repository.get(1) } }
            verify(localSource).getProducts(1)
            verify(remoteSource).getProducts()
        }
    }
}

