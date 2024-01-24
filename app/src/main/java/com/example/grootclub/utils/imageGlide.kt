package com.example.grootclub.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.grootclub.R


fun ImageView.loadImage(uri: String?) {
    val options = com.bumptech.glide.request.RequestOptions()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}