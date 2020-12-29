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
import dev.chulwoo.nb.order.features.product.domain.model.Product
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
    single<CategoryLocalSource> { MemoryCache() }
    single<ProductLocalSource> {
        object : ProductLocalSource {
            override suspend fun get(categoryId: Int): List<Product> {
                return listOf(
                    Product(1, categoryId, 1000.0, "name test", ""),
                    Product(2, categoryId, 5000.0, "name test2", ""),
                )
            }

            override suspend fun set(categoryId: Int, products: List<Product>) {

            }
        }
    }
}

val httpModule = module {
    single<CategoryRemoteSource> {
        OrderApi(
            RetrofitFactory.create("https://nowwaiting-dev-aeb2b.web.app/")
        )
    }
    single<ProductRemoteSource> {
        object : ProductRemoteSource {
            override suspend fun get(): List<Product> {
                return listOf(
                    Product(1, 1, 1000.0, "a", ""),
                    Product(2, 1, 5000.0, "b", ""),
                )
            }
        }
    }
}
