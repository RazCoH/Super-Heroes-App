package com.raz.heroesapp.objects

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

object CustomBindings {
    @JvmStatic
    @BindingAdapter("bind.loadWithGlide")
    fun loadImage(image: ShapeableImageView?, url: String?) {
        if (image != null && url != null) {
            Glide
                .with(image.context)
                .load(url)
                .into(image)
        }
    }
}