package dev.chulwoo.nb.order.features.cart.domain

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.DeleteFromCart
import dev.chulwoo.nb.order.features.cart.domain.usecase.DeleteFromCartParam
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test


class DeleteFromCartTest {
    private val product = Product(1, 1, 2500.0, "1", "")

    @Test
    fun `When invoke DeleteFromCart Then returns updated cart from repository`() {
        runBlocking {
            val repository = mock<CartRepository> {
                onBlocking { delete(any()) } doAnswer { Cart() }
            }
            val deleteFromCart = DeleteFromCart(repository)
            val result = deleteFromCart(DeleteFromCartParam(product))
            verify(repository).delete(product)
            assertThat(result, equalTo(Cart()))
        }
    }

    @Test
    fun `Given error on repository When invoke DeleteFromCart Then rethrows error`() {
        val repository = mock<CartRepository> {
            onBlocking { delete(any()) } doAnswer { throw Throwable() }
        }
        val deleteFromCart = DeleteFromCart(repository)
        assertThrows(Throwable::class.java) {
            runBlocking {
                deleteFromCart(DeleteFromCartParam(product))
            }
        }
    }
}
