package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.product.domain.model.Category
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
    fun testGetWithoutData() {
        assertThrows(CacheMissException::class.java) { runBlocking { cache.get() } }
    }

    @Test
    fun testSet() {
        runBlocking {
            cache.set(listOf(Category(1, "1")))
            assertThat(cache.get(), equalTo(listOf(Category(1, "1"))))
        }
    }
}
