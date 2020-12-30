package dev.chulwoo.nb.order.ui.order.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import dev.chulwoo.nb.order.features.category.presentation.state.ProductState
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.ProductViewModel
import dev.chulwoo.nb.order.ui.R
import dev.chulwoo.nb.order.ui.databinding.CategoryProductFragmentBinding
import dev.chulwoo.nb.order.ui.util.HorizontalSpacingItemDecoration
import dev.chulwoo.nb.order.ui.util.toDp
import dev.chulwoo.nb.order.ui.util.toPx
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryProductFragment : Fragment() {

    private val productViewModel: ProductViewModel by viewModel()

    private var _binding: CategoryProductFragmentBinding? = null
    val binding: CategoryProductFragmentBinding get() = _binding!!

    private val productAdapter: ProductAdapter = ProductAdapter()

    companion object {
        fun newInstance(categoryId: Int): CategoryProductFragment {
            return CategoryProductFragment().apply {
                arguments = bundleOf("categoryId" to categoryId)
            }
        }
    }

    fun updateCategory(categoryId: Int) {
        productViewModel.load(categoryId)
    }

    private fun onNewState(state: ProductState) {
        with(binding) {
            when (state) {
                ProductState.Initial -> {
                }
                ProductState.Loading -> {
                    error.root.isVisible = false
                    loadingIndicator.show()
                }
                is ProductState.Success -> {
                    val categories = state.data
                    productAdapter.submitList(categories)
                    loadingIndicator.hide()
                    error.root.isVisible = false
                }
                is ProductState.Failure -> {
                    loadingIndicator.hide()
                    error.root.isVisible = true
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategoryProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spanCount = calculateSpanCount()
        binding.recyclerView.adapter = productAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, spanCount)
        binding.recyclerView.addItemDecoration(
            HorizontalSpacingItemDecoration(
                spanCount = spanCount,
                spacing = 20.toPx()
            )
        )
        binding.error.retryButton.setOnClickListener { productViewModel.reload() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productViewModel.states.asLiveData().observe(viewLifecycleOwner) { onNewState(it) }
        arguments?.getInt("categoryId")?.let { updateCategory(it) }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun calculateSpanCount(): Int {
        val displayMetrics = requireContext().resources.displayMetrics
        val width = displayMetrics.widthPixels / displayMetrics.density
        return (width / resources.getDimensionPixelSize(R.dimen.product_width).toDp()).toInt()
    }
}
