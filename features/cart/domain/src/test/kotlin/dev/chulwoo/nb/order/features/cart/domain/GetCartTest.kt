package dev.chulwoo.nb.order.features.cart.domain

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.GetCart
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test


class GetCartTest {
    private val p1 = Product(1, 1, 2500.0, "1", "")
    private val p2 = Product(2, 1, 2500.0, "2", "")
    private val p3 = Product(3, 2, 1000.0, "3", "")

    private val cart1 = Cart(
        listOf(
            CartItem(p1, 1),
            CartItem(p2, 2),
            CartItem(p3, 3),
        )
    )

    @Test
    fun `When invoke GetCart Then returns data from repository`() {
        runBlocking {
            val repository = mock<CartRepository> {
                onBlocking { get() } doAnswer { cart1 }
            }
            val getCart = GetCart(repository)
            val result = getCart()
            verify(repository).get()
            assertThat(result, equalTo(cart1))
        }
    }

    @Test
    fun `Given error on repository When invoke GetCart Then rethrows error`() {
        val repository = mock<CartRepository> {
            onBlocking { get() } doAnswer { throw Throwable() }
        }
        val getCart = GetCart(repository)
        assertThrows(Throwable::class.java) { runBlocking { getCart() } }
    }
}
