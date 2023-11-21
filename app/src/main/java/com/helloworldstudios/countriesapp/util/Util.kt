package com.helloworldstudios.countriesapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.helloworldstudios.countriesapp.R

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(placeHolderProgressBar(context))
        .error(R.drawable.ic_error_outline)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable =
    CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
    }

@BindingAdapter("android:downloadImage")
fun downloadImage(imageView: ImageView, url: String?){
    imageView.downloadFromUrl(url, placeHolderProgressBar(imageView.context))
}