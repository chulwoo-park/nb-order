package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.cart.domain.model.Cart
import dev.chulwoo.nb.order.features.cart.domain.model.CartItem
import dev.chulwoo.nb.order.features.product.domain.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test


class CartMemorySourceTest {

    private val products = listOf(
        Product(1, 1, 2500.0, "product", ""),
        Product(2, 1, 2500.0, "product", ""),
        Product(3, 2, 2500.0, "product", "")
    )
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
            cartSource.add(products[0])
            assertThat(cartSource.get(), equalTo(Cart(listOf(CartItem(products[0], 1)))))


            cartSource.add(products[0])
            assertThat(cartSource.get(), equalTo(Cart(listOf(CartItem(products[0], 2)))))

            cartSource.add(products[1])
            assertThat(
                cartSource.get(), equalTo(
                    Cart(
                        listOf(
                            CartItem(products[0], 2),
                            CartItem(products[1], 1)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `Given not data When invoke remove Then returns empty cart`() {
        runBlocking {
            cartSource.remove(products[0])
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }

    @Test
    fun `When invoke remove Then returns updated data`() {
        runBlocking {
            cartSource.add(products[0])
            cartSource.add(products[0])
            cartSource.add(products[1])
            cartSource.remove(products[0])
            assertThat(
                cartSource.get(), equalTo(
                    Cart(
                        listOf(
                            CartItem(products[0], 1),
                            CartItem(products[1], 1)
                        )
                    )
                )
            )
        }
    }

    @Test
    fun `Given not data When invoke delete Then returns empty cart`() {
        runBlocking {
            cartSource.delete(products[0])
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }

    @Test
    fun `When invoke delete Then returns updated data`() {
        runBlocking {
            cartSource.add(products[0])
            cartSource.add(products[0])
            cartSource.add(products[1])
            cartSource.delete(products[0])
            assertThat(
                cartSource.get(), equalTo(Cart(listOf(CartItem(products[1], 1))))
            )
        }
    }

    @Test
    fun `When invoke clear Then returns empty cart`() {
        runBlocking {
            cartSource.add(products[0])
            cartSource.add(products[0])
            cartSource.add(products[1])
            cartSource.clear()
            assertThat(cartSource.get(), equalTo(Cart()))
        }
    }
}
