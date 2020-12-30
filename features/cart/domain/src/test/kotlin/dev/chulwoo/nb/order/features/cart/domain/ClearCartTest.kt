package dev.chulwoo.nb.order.features.cart.domain

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.ClearCart
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test


class ClearCartTest {

    @Test
    fun `When invoke ClearCart Then returns updated cart from repository`() {
        runBlocking {
            val repository = mock<CartRepository> {
                onBlocking { clear() } doAnswer {
                    Cart(listOf())
                }
            }
            val clearCart = ClearCart(repository)
            val result = clearCart()
            verify(repository).clear()
            assertThat(result, equalTo(Cart(listOf())))
        }
    }

    @Test
    fun `Given error on repository When invoke ClearCart Then rethrows error`() {
        val repository = mock<CartRepository> {
            onBlocking { clear() } doAnswer { throw Throwable() }
        }
        val clearCart = ClearCart(repository)
        assertThrows(Throwable::class.java) { runBlocking { clearCart() } }
    }
}
