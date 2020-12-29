package dev.chulwoo.nb.order.features.category.presentation.model

data class Category(val id: Int, val name: String, val isSelected: Boolean = false) {

    override fun hashCode(): Int {
        return 31 + id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Category) return false
        return id == other.id
    }
}
