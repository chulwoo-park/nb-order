package dev.chulwoo.nb.order.features.product.domain.usecase

import dev.chulwoo.nb.order.features.product.domain.model.Product
import dev.chulwoo.nb.order.features.product.domain.repository.ProductRepository

data class GetProductsParam(val categoryId: Int)

class GetProducts(private val repository: ProductRepository) {

    suspend operator fun invoke(param: GetProductsParam): List<Product> {
        return repository.get(param.categoryId)
    }
}
