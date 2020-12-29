package dev.chulwoo.nb.order.di

import dev.chulwoo.nb.order.device.MemoryCache
import dev.chulwoo.nb.order.features.category.data.repository.CategoryRepositoryImpl
import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.CategoryViewModel
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository
import dev.chulwoo.nb.order.features.domain.usecase.GetCategories
import dev.chulwoo.nb.order.http.api.OrderApi
import dev.chulwoo.nb.order.http.api.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetCategories(get()) }
}

val dataModule = module {
    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
}

val presentationModule = module {
    single { CategoryViewModel(get(), Dispatchers.IO) }
}

val deviceModule = module {
    single<CategoryLocalSource> { MemoryCache() }
}

val httpModule = module {
    single<CategoryRemoteSource> {
        OrderApi(
            RetrofitFactory.create("https://nowwaiting-dev-aeb2b.web.app/")
        )
    }
}
