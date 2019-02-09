package pro.jeminay.tochka.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("app:loadUrl")
fun setImageViewLoadUrl(imageView: ImageView, url: String) {
    Picasso.get()
        .load(url)
        .into(imageView)
}