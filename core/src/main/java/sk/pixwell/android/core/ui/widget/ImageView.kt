package sk.pixwell.android.core.ui.widget

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun <T> ImageView.loadImageAsync(source: T?, requestOptions: RequestOptions = RequestOptions()) {
    if (source != null) {
        Glide.with(this)
            .load(source)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    } else {
        Glide.with(this)
            .clear(this)
    }
}