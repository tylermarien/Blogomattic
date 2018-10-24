package com.tylermarien.blogomattic.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.Html
import android.widget.TextView
import com.squareup.picasso.Target

class PicassoImageGetter(val textView: TextView, val context: Context, val maxWidth: Float) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        val drawable = BitmapDrawablePlaceHolder()
        Picasso.with(context)
            .load(source)
            .into(drawable)

        return drawable
    }

    private inner class BitmapDrawablePlaceHolder : BitmapDrawable(context.resources), Target {

        var drawable: Drawable? = null
            set(value) {
                field = value
                value?.let {
                    val width = if (it.intrinsicWidth > maxWidth) {
                        maxWidth.toInt()
                    } else {
                        it.intrinsicWidth
                    }

                    val height = if (it.intrinsicWidth > maxWidth) {
                        (it.intrinsicHeight * (maxWidth / it.intrinsicWidth)).toInt()
                    } else {
                        it.intrinsicHeight
                    }

                    it.setBounds(0, 0, width, height)
                    setBounds(0, 0, width, height)
                    textView.text = textView.text
                }
            }

        override fun draw(canvas: Canvas) {
            drawable?.draw(canvas)
        }

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            drawable = BitmapDrawable(context.resources, bitmap)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {}

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

    }
}