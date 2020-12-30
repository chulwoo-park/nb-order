package dev.chulwoo.nb.order.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.chulwoo.nb.order.features.category.presentation.model.Category
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.CategoryViewModel
import dev.chulwoo.nb.order.ui.databinding.ProductFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private val categoryViewModel: CategoryViewModel by viewModel()

    private var _binding: ProductFragmentBinding? = null
    val binding: ProductFragmentBinding get() = _binding!!

    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        fun newInstance() = ProductFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoryViewModel.states.asLiveData().observe(viewLifecycleOwner) {
            onNewState(it)
        }
        categoryViewModel.load()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryAdapter = CategoryAdapter(this)
        with(binding) {
            pager.adapter = categoryAdapter
            TabLayoutMediator(tabLayout, pager) { tab, position ->
                tab.text = categoryAdapter.items[position].name
            }.attach()

            error.retryButton.setOnClickListener { categoryViewModel.load() }
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { categoryViewModel.select(it.position) }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
            categoryBefore.setOnClickListener {
                categoryViewModel.selectBefore()
            }
            categoryNext.setOnClickListener {
                categoryViewModel.selectNext()
            }
        }
    }

    private fun onNewState(state: CategoryState) {
        with(binding) {
            when (state) {
                CategoryState.Initial -> {
                }
                CategoryState.Loading -> {
                    error.root.isVisible = false
                    loadingIndicator.show()
                }
                is CategoryState.Success -> {
                    loadingIndicator.hide()
                    error.root.isVisible = false

                    val categories = state.data
                    categoryAdapter.update(categories)
                    val selectedIndex = categories.indexOfFirst { it.isSelected }
                    tabLayout.getTabAt(selectedIndex)?.select()
                    updateArrows(state.data)
                }
                is CategoryState.Failure -> {
                    loadingIndicator.hide()
                    error.root.isVisible = true
                }
            }
        }
    }

    private fun updateArrows(categories: List<Category>) {
        with(binding) {
            categories.firstOrNull()?.let {
                categoryBefore.isEnabled = !it.isSelected
            }

            categories.lastOrNull()?.let {
                categoryNext.isEnabled = !it.isSelected
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
