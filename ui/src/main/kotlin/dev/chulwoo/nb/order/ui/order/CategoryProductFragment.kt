package dev.chulwoo.nb.order.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.chulwoo.nb.order.features.category.presentation.model.Category
import dev.chulwoo.nb.order.ui.databinding.CategoryProductFragmentBinding

class CategoryProductFragment : Fragment() {

    private var _binding: CategoryProductFragmentBinding? = null
    val binding: CategoryProductFragmentBinding get() = _binding!!

    companion object {
        fun newInstance() = CategoryProductFragment()
    }

    fun updateCategory(category: Category) {
        TODO()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
