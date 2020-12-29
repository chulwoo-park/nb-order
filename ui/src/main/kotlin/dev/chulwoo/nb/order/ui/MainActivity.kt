package dev.chulwoo.nb.order.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chulwoo.nb.order.ui.databinding.MainActivityBinding
import dev.chulwoo.nb.order.ui.order.ProductFragment

class MainActivity : AppCompatActivity() {

    private var _binding: MainActivityBinding? = null
    val binding: MainActivityBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ProductFragment.newInstance())
                .commitNow()
        }
    }
}
