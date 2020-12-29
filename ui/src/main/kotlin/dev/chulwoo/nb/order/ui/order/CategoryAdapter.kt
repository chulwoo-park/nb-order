package dev.chulwoo.nb.order.ui.order

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import dev.chulwoo.nb.order.features.category.presentation.model.Category

class CategoryAdapter(
    private val fragment: Fragment,
    private val _items: MutableList<Category> = mutableListOf()
) :
    FragmentStateAdapter(fragment) {

    val items: List<Category> = _items

    fun update(newItems: List<Category>) {
        val callback = CategoryDiffUtil(_items, newItems)
        val diff = DiffUtil.calculateDiff(callback)

        _items.clear()
        _items.addAll(newItems)

        diff.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val fragment = fragment.childFragmentManager.findFragmentByTag("f${holder.itemId}")
            if (fragment != null) {
                (fragment as CategoryProductFragment).updateCategory(_items[position])
            } else {
                super.onBindViewHolder(holder, position, payloads)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemCount(): Int = _items.size

    override fun createFragment(position: Int): Fragment = CategoryProductFragment.newInstance()

    override fun getItemId(position: Int): Long = _items[position].id.toLong()

    override fun containsItem(itemId: Long): Boolean = _items.any { it.id.toLong() == itemId }
}
