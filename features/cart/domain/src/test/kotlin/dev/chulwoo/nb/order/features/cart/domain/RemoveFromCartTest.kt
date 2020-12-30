package dev.chulwoo.nb.order.features.cart.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.RemoveFromCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.RemoveFromCartParam
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test


class RemoveFromCartTest {
    private val products =
        listOf(
            Product(1, 1, 2500.0, "1", ""),
            Product(2, 1, 2500.0, "2", ""),
            Product(3, 2, 1000.0, "3", "")
        )

    private val cart1 = Cart(listOf())

    @Test
    fun `When invoke RemoveFromCart Then returns updated cart from repository`() {
        runBlocking {
            val repository = mock<CartRepository> {
                onBlocking { remove(any()) } doAnswer {
                    Cart(listOf())
                }
            }
            val removeFromCart = RemoveFromCart(repository)
            val result = removeFromCart(RemoveFromCartParam(1))
            verify(repository).remove(1)
            assertThat(result, equalTo(Cart(listOf())))
        }
    }

    @Test
    fun `Given error on repository When invoke RemoveFromCart Then rethrows error`() {
        val repository = mock<CartRepository> {
            onBlocking { remove(any()) } doAnswer { throw Throwable() }
        }
        val removeFromCart = RemoveFromCart(repository)
        assertThrows(Throwable::class.java) { runBlocking { removeFromCart(RemoveFromCartParam(1)) } }
    }
}
