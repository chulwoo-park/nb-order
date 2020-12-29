package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.product.domain.model.Category
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test


class MemoryCacheTest {

    lateinit var cache: MemoryCache

    @Before
    fun setUp() {
        cache = MemoryCache()
    }

    @Test
    fun `Given not data When invoke getCategories Then throw CacheMissException`() {
        assertThrows(CacheMissException::class.java) { runBlocking { cache.getCategories() } }
    }

    @Test
    fun `When invoke setCategories Then getCategories returns that data`() {
        runBlocking {
            cache.setCategories(listOf(Category(1, "1")))
            assertThat(cache.getCategories(), equalTo(listOf(Category(1, "1"))))
        }
    }

    @Test
    fun `Given not data When invoke getProducts Then throw CacheMissException`() {
        assertThrows(CacheMissException::class.java) { runBlocking { cache.getProducts(1) } }
    }

    @Test
    fun `When invoke setProducts Then getCategories returns that data`() {
        runBlocking {
            cache.setProducts(1, listOf(Product(1, 1, 0.0, "1", "")))
            cache.setProducts(2, listOf(Product(2, 2, 0.0, "2", "")))
            assertThat(
                cache.getProducts(1),
                equalTo(listOf(Product(1, 1, 0.0, "1", "")))
            )
            assertThat(
                cache.getProducts(2),
                equalTo(listOf(Product(2, 2, 0.0, "2", "")))
            )
        }
    }
}
