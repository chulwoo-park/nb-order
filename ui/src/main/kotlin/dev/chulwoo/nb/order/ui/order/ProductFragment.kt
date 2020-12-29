package dev.chulwoo.nb.order.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import dev.chulwoo.nb.order.features.category.presentation.state.CategoryState
import dev.chulwoo.nb.order.features.category.presentation.viewmodel.CategoryViewModel
import dev.chulwoo.nb.order.ui.databinding.ProductFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductFragment : Fragment() {

    private val categoryViewModel: CategoryViewModel by viewModel()

    private var _binding: ProductFragmentBinding? = null
    val binding: ProductFragmentBinding get() = _binding!!


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

    private fun onNewState(state: CategoryState) {
        with(binding) {
            when (state) {
                CategoryState.Initial -> {
                    message.text = "initial"
                }
                CategoryState.Loading -> {
                    message.text = "loading"
                }
                is CategoryState.Success -> {
                    message.text = "success: ${state.data}"
                }
                is CategoryState.Failure -> {
                    message.text = "failure: ${state.error}"
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
