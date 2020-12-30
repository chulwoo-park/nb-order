package dev.chulwoo.nb.order.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chulwoo.nb.order.features.cart.presentation.viewmodel.CartViewModel
import dev.chulwoo.nb.order.ui.databinding.MainActivityBinding
import dev.chulwoo.nb.order.ui.order.cart.CartFragment
import dev.chulwoo.nb.order.ui.order.product.ProductFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    val cartViewModel: CartViewModel by viewModel()

    private var _binding: MainActivityBinding? = null
    val binding: MainActivityBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.product, ProductFragment.newInstance())
                .replace(R.id.cart, CartFragment.newInstance())
                .commitNow()
        }
    }
}
