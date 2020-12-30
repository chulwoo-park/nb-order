package dev.chulwoo.nb.order.ui.order.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import dev.chulwoo.nb.order.features.cart.presentation.state.CartState
import dev.chulwoo.nb.order.features.cart.presentation.viewmodel.CartViewModel
import dev.chulwoo.nb.order.ui.databinding.CartFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by sharedViewModel()

    private var _binding: CartFragmentBinding? = null
    val binding: CartFragmentBinding get() = _binding!!

    private lateinit var cartItemAdapter: CartItemAdapter

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CartFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cartViewModel.states.asLiveData().observe(viewLifecycleOwner) {
            onNewState(it)
        }
        cartViewModel.load()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartItemAdapter = CartItemAdapter(
            onProductAdded = { cartViewModel.add(it) },
            onProductRemoved = { cartViewModel.remove(it) },
            onProductCleared = { cartViewModel.delete(it) }
        )
        binding.recyclerView.adapter = cartItemAdapter
        binding.recyclerView.itemAnimator = null
        binding.clearButton.setOnClickListener { cartViewModel.clear() }
    }

    private fun onNewState(state: CartState) {
        with(binding) {
            when (state) {
                CartState.Initial -> {
                }
                CartState.Loading -> {
                    loadingIndicator.show()
                }
                else -> {
                    if (state is CartState.FinishLoadingState) {
                        loadingIndicator.hide()
                        cartItemAdapter.submitList(state.data.products)
                        binding.totalPrice = state.data.totalPrice
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
