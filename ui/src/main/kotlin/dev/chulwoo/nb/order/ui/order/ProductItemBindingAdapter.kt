package dev.chulwoo.nb.order.ui.order

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ProductItemBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load(imageUrl).into(view)
    }
}
