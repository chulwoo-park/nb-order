package dev.chulwoo.nb.order.http

import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.http.api.OrderApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class OrderApiTest {

    private lateinit var server: MockWebServer
    private lateinit var orderApi: OrderApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .build()

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
                orderApi.get(),
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
}
