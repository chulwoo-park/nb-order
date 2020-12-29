package dev.chulwoo.nb.order.di

import dev.chulwoo.nb.order.device.MemoryCache
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
}

val dataModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}

val presentationModule = module {
    single { CategoryViewModel(get(), Dispatchers.IO) }
    single { ProductViewModel(get(), Dispatchers.IO) }
}

val deviceModule = module {
    val memCache = MemoryCache()
    single<CategoryLocalSource> { memCache }
    single<ProductLocalSource> { memCache }
}

val httpModule = module {
    val orderApi = OrderApi(
        RetrofitFactory.create("https://nowwaiting-dev-aeb2b.web.app/")
    )
    single<CategoryRemoteSource> { orderApi }
    single<ProductRemoteSource> { orderApi }
}
