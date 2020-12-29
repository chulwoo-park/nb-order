package dev.chulwoo.nb.order.http

import dev.chulwoo.nb.order.features.product.domain.model.Category
import dev.chulwoo.nb.order.features.product.domain.model.Product
import dev.chulwoo.nb.order.http.api.OrderApi
import dev.chulwoo.nb.order.http.api.RetrofitFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class OrderApiTest {

    private lateinit var server: MockWebServer
    private lateinit var orderApi: OrderApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val retrofit = RetrofitFactory.create(server.url("/"))
        orderApi = OrderApi(retrofit)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `Given success response When get categories Then parse to DTO`() {
        server.enqueue(MockResponse().setBody("[{\"id\": 100,\"name\": \"커피\"},{\"id\": 101,\"name\": \"라떼\"},{\"id\": 102,\"name\": \"베이커리\"},{\"id\": 103,\"name\": \"기타\"}]"))
        runBlocking {
            assertThat(
                orderApi.getCategories(),
                equalTo(
                    listOf(
                        Category(100, "커피"),
                        Category(101, "라떼"),
                        Category(102, "베이커리"),
                        Category(103, "기타")
                    )
                )
            )
        }
    }

    @Test
    fun `Given success response When get products Then parse to DTO`() {
        server.enqueue(
            MockResponse().setBody(
                "[{\"id\": 200,\"category_id\": 100,\"name\": \"아이스 아메리카노\",\"price\": 2500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/ice_americano.png\"},{\"id\": 201,\"category_id\": 100,\"name\": \"아메리카노\",\"price\": 2000,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/americano.png\"},{\"id\": 202,\"category_id\": 101,\"name\": \"라떼\",\"price\": 2600,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/latte.png\"},{\"id\": 203,\"category_id\": 101,\"name\": \"아이스 라떼\",\"price\": 3000,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/ice_latte.png\"},{\"id\": 204,\"category_id\": 101,\"name\": \"그린티 라떼\",\"price\": 3200,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/latte2.png\"},{\"id\": 205,\"category_id\": 101,\"name\": \"얼 그레이 라떼\",\"price\": 3500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/latte3.png\"},{\"id\": 206,\"category_id\": 101,\"name\": \"말차 라떼\",\"price\": 3500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/latte4.png\"},{\"id\": 207,\"category_id\": 101,\"name\": \"차이 티 라떼\",\"price\": 3500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/latte2.png\"},{\"id\": 208,\"category_id\": 102,\"name\": \"머핀\",\"price\": 2500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/muffin.png\"},{\"id\": 209,\"category_id\": 102,\"name\": \"클래스 스콘\",\"price\": 2500,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/scorn.png\"},{\"id\": 210,\"category_id\": 102,\"name\": \"블루베리 베이클\",\"price\": 3000,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/bagel.png\"},{\"id\": 211,\"category_id\": 102,\"name\": \"아몬드 크루아상\",\"price\": 3000,\"image\": \"https://nowwaiting-dev-aeb2b.web.app/image/croissant.png\"}]"
            )
        )
        runBlocking {
            assertThat(
                orderApi.getProducts().first(),
                equalTo(
                    Product(
                        200,
                        100,
                        2500.0,
                        "아이스 아메리카노",
                        "https://nowwaiting-dev-aeb2b.web.app/image/ice_americano.png"
                    )
                )
            )
        }
    }
}
