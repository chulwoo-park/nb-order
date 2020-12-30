package dev.chulwoo.nb.order.features.cart.data.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test

class CartRepositoryImplTest {

    private val cart = Cart(listOf())

    @Test
    fun `Given local data When invoke get Then returns data from local`() {
        runBlocking {
            val localSource = mock<CartLocalSource> {
                onBlocking { get() } doAnswer { cart }
            }
            val repository = CartRepositoryImpl(localSource)

            repository.get()
            verify(localSource).get()
        }
    }

    @Test
    fun `Given local error When invoke get Then throws error`() {
        val localSource = mock<CartLocalSource> {
            onBlocking { get() } doAnswer { throw Exception() }
        }
        val repository = CartRepositoryImpl(localSource)

        assertThrows(Exception::class.java) { runBlocking { repository.get() } }
    }

    @Test
    fun `Given local data When invoke add Then returns updated data from local`() {
        runBlocking {
            val localSource = mock<CartLocalSource> {
                onBlocking { add(any()) } doAnswer { cart }
            }
            val repository = CartRepositoryImpl(localSource)

            val result = repository.add(1)
            verify(localSource).add(1)
            assertThat(result, equalTo(cart))
        }
    }

    @Test
    fun `Given local error When invoke add Then throws error`() {
        val localSource = mock<CartLocalSource> {
            onBlocking { add(any()) } doAnswer { throw Exception() }
        }
        val repository = CartRepositoryImpl(localSource)

        assertThrows(Exception::class.java) { runBlocking { repository.add(1) } }
    }

    @Test
    fun `Given local data When invoke remove Then returns updated data from local`() {
        runBlocking {
            val localSource = mock<CartLocalSource> {
                onBlocking { remove(any()) } doAnswer { cart }
            }
            val repository = CartRepositoryImpl(localSource)

            val result = repository.remove(1)
            verify(localSource).remove(1)
            assertThat(result, equalTo(cart))
        }
    }

    @Test
    fun `Given local error When invoke remove Then throws error`() {
        val localSource = mock<CartLocalSource> {
            onBlocking { remove(any()) } doAnswer { throw Exception() }
        }
        val repository = CartRepositoryImpl(localSource)

        assertThrows(Exception::class.java) { runBlocking { repository.remove(1) } }
    }

    @Test
    fun `Given local data When invoke clear Then returns updated data from local`() {
        runBlocking {
            val localSource = mock<CartLocalSource> {
                onBlocking { clear() } doAnswer { cart }
            }
            val repository = CartRepositoryImpl(localSource)

            val result = repository.clear()
            verify(localSource).clear()
            assertThat(result, equalTo(cart))
        }
    }

    @Test
    fun `Given local error When invoke clear Then throws error`() {
        val localSource = mock<CartLocalSource> {
            onBlocking { clear() } doAnswer { throw Exception() }
        }
        val repository = CartRepositoryImpl(localSource)

        assertThrows(Exception::class.java) { runBlocking { repository.clear() } }
    }
}
