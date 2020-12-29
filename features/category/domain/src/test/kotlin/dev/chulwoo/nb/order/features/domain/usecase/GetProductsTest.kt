package dev.chulwoo.nb.order.features.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.domain.model.Product
import dev.chulwoo.nb.order.features.domain.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class GetProductsTest {

    @Test
    fun `When invoke GetProducts Then returns data from repository`() {
        runBlocking {
            val repository = mock<ProductRepository> {
                onBlocking { get(1) } doAnswer {
                    listOf(
                        Product(1, 1, 0.0, "1", ""),
                        Product(2, 1, 0.0, "2", "")
                    )
                }
            }
            val getProducts = GetProducts(repository)
            val result = getProducts(GetProductsParam(1))
            verify(repository).get(1)
            assertEquals(
                listOf(
                    Product(1, 1, 0.0, "1", ""),
                    Product(2, 1, 0.0, "2", "")
                ), result
            )
        }
    }

    @Test
    fun `Given error on repository When invoke GetProducts Then rethrows error`() {
        val repository = mock<ProductRepository> {
            onBlocking { get(any()) } doAnswer { throw Throwable() }
        }
        val getProducts = GetProducts(repository)
        assertThrows(Throwable::class.java) { runBlocking { getProducts(GetProductsParam(1)) } }
    }
}
