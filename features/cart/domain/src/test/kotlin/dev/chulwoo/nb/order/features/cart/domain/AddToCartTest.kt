package dev.chulwoo.nb.order.features.cart.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.AddToCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.AddToCartParam
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test


class AddToCartTest {
    private val products =
        listOf(
            Product(1, 1, 2500.0, "1", ""),
            Product(2, 1, 2500.0, "2", ""),
            Product(3, 2, 1000.0, "3", "")
        )

    @Test
    fun `When invoke AddToCart Then returns updated cart from repository`() {
        runBlocking {
            val repository = mock<CartRepository> {
                onBlocking { add(any()) } doAnswer {
                    val productId = it.arguments[0] as Int
                    Cart(listOf(CartItem(products[productId - 1], 1)))
                }
            }
            val addToCart = AddToCart(repository)
            val result = addToCart(AddToCartParam(1))
            verify(repository).add(1)
            assertThat(result, equalTo(Cart(listOf(CartItem(products[0], 1)))))
        }
    }

    @Test
    fun `Given error on repository When invoke AddToCart Then rethrows error`() {
        val repository = mock<CartRepository> {
            onBlocking { add(any()) } doAnswer { throw Exception() }
        }
        val addToCart = AddToCart(repository)
        assertThrows(Exception::class.java) { runBlocking { addToCart(AddToCartParam(1)) } }
    }
}
