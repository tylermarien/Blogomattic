package com.tylermarien.wordpress

import android.os.Build
import android.text.Html
import android.text.Spanned

fun fromHtml(source: String, imageGetter: Html.ImageGetter? = null): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT, imageGetter, null)
    } else {
        Html.fromHtml(source, imageGetter, null)
    }
}