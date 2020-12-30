package dev.chulwoo.nb.order.di

import dev.chulwoo.nb.order.device.CartMemorySource
import dev.chulwoo.nb.order.device.MemoryCache
import dev.chulwoo.nb.order.features.cart.data.repository.CartRepositoryImpl
import dev.chulwoo.nb.order.features.cart.data.source.CartLocalSource
import dev.chulwoo.nb.order.features.cart.domain.repository.CartRepository
import dev.chulwoo.nb.order.features.cart.domain.usecase.*
import dev.chulwoo.nb.order.features.cart.presentation.viewmodel.CartViewModel
import dev.chulwoo.nb.order.features.category.data.repository.CategoryRepositoryImpl
import dev.chulwoo.nb.order.features.category.data.repository.ProductRepositoryImpl
import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.category.data.source.ProductLocalSource
import dev.chulwoo.nb.order.features.category.data.source.ProductRemoteSource
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.CategoryViewModel
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.ProductViewModel
import dev.chulwoo.nb.order.features.product.domain.repository.CategoryRepository
import dev.chulwoo.nb.order.features.product.domain.repository.ProductRepository
import dev.chulwoo.nb.order.features.product.domain.usecase.GetCategories
import dev.chulwoo.nb.order.features.product.domain.usecase.GetProducts
import dev.chulwoo.nb.order.http.api.OrderApi
import dev.chulwoo.nb.order.http.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetCategories(get()) }
    factory { GetProducts(get()) }
    factory { GetCart(get()) }
    factory { AddToCart(get()) }
    factory { RemoveFromCart(get()) }
    factory { DeleteFromCart(get()) }
    factory { ClearCart(get()) }
}

val dataModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }
}

val presentationModule = module {
    factory { CategoryViewModel(get(), Dispatchers.IO) }
    factory { ProductViewModel(get(), Dispatchers.IO) }
    factory {
        CartViewModel(
            getCart = get(),
            addToCart = get(),
            removeFromCart = get(),
            deleteFromCart = get(),
            clearCart = get(),
            Dispatchers.IO
        )
    }
}

val deviceModule = module {
    val memCache = MemoryCache()
    single<CategoryLocalSource> { memCache }
    single<ProductLocalSource> { memCache }
    single<CartLocalSource> { CartMemorySource() }
}

val httpModule = module {
    val orderApi = OrderApi(
        RetrofitFactory.create("https://nowwaiting-dev-aeb2b.web.app/")
    )
    single<CategoryRemoteSource> { orderApi }
    single<ProductRemoteSource> { orderApi }
}
