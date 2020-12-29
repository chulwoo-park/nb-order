package dev.chulwoo.nb.order.device

import dev.chulwoo.nb.order.features.category.data.source.CategoryLocalSource
import dev.chulwoo.nb.order.features.domain.model.Category

/**
 * 앱 재실행 시 원격 데이터가 변경됐다면 바로 반영되어야하므로 별도의 DB, 파일을 사용하지 않고 메모리 캐시만 사용.
 *
 * 다음의 방식으로 추가 개선 가능:
 *
 *  * 유즈케이스 타입을 Flow로 변경해 캐시를 우선 보여주고 이후 원격 데이터로 교체
 *  * 사용자가 명시적으로 원격 데이터 호출하도록 UI 제공
 */
class MemoryCache : CategoryLocalSource {

    var categories: List<Category>? = null

    override suspend fun get(): List<Category> {
        if (categories == null) throw CacheMissException()

        return categories!!
    }

    override suspend fun set(categories: List<Category>) {
        this.categories = categories
    }
}