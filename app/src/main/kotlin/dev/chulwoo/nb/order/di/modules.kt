package dev.chulwoo.nb.order.di

import dev.chulwoo.nb.order.features.category.data.repository.CategoryRepositoryImpl
import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.category.data.source.CategoryRemoteSource
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.CategoryViewModel
import dev.chulwoo.nb.order.features.domain.model.Category
import dev.chulwoo.nb.order.features.domain.repository.CategoryRepository
import dev.chulwoo.nb.order.features.domain.usecase.GetCategories
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

val tmpModule = module {
    single<CategoryLocalSource> {
        object : CategoryLocalSource {
            override suspend fun get(): List<Category> {
                return listOf(Category(0, "local0"), Category(1, "local1"), Category(2, "local2"))
            }
        }
    }

    single<CategoryRemoteSource> {
        object : CategoryRemoteSource {
            override suspend fun get(): List<Category> {
                return listOf(
                    Category(0, "remote0"),
                    Category(1, "remote1"),
                    Category(2, "remote2")
                )
            }
        }
    }
}
