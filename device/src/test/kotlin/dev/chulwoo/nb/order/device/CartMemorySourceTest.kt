package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class CartMemorySourceTest {

    lateinit var cartSource: CartMemorySource

    @Before
    fun setUp() {
        cartSource = CartMemorySource()
    }

    @Test
    fun `Given not data When invoke get Then return emtpy cart`() {
        runBlocking {
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }

    @Test
    fun `When invoke add Then returns updated data`() {
        runBlocking {
            cartSource.add(1)
            assertThat(cartSource.get(), equalTo(Cart(listOf(CartItem(1, 1)))))


            cartSource.add(1)
            assertThat(cartSource.get(), equalTo(Cart(listOf(CartItem(1, 2)))))

            cartSource.add(2)
            assertThat(
                cartSource.get(), equalTo(
                    Cart(
                        listOf(
                            CartItem(1, 2),
                            CartItem(2, 1)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `Given not data When invoke remove Then returns empty cart`() {
        runBlocking {
            cartSource.remove(1)
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }

    @Test
    fun `When invoke remove Then returns updated data`() {
        runBlocking {
            cartSource.add(1)
            cartSource.add(1)
            cartSource.add(2)
            cartSource.remove(1)
            assertThat(
                cartSource.get(), equalTo(
                    Cart(
                        listOf(
                            CartItem(1, 1),
                            CartItem(2, 1)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `When invoke clear Then returns empty cart`() {
        runBlocking {
            cartSource.add(1)
            cartSource.add(1)
            cartSource.add(2)
            cartSource.clear()
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }
}
