package dev.chulwoo.nb.order.features.domain.usecase

import dev.chulwoo.nb.order.features.domain.model.Product
import dev.chulwoo.nb.order.features.domain.repository.ProductRepository

data class GetProductsParam(val categoryId: Int)

class GetProducts(private val repository: ProductRepository) {

    suspend operator fun invoke(param: GetProductsParam): List<Product> {
        return repository.get(param.categoryId)
    }
}
