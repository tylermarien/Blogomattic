package com.tylermarien.blogomattic.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

fun formatExcerpt(source: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT, null, null)
    } else {
        Html.fromHtml(source,  null, null)
    }
}

fun formatComment(source: String) = formatContent(source)

fun formatContent(source: String, imageGetter: Html.ImageGetter? = null): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, null)
    } else {
        Html.fromHtml(source, imageGetter, null)
    }
}