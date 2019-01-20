package com.tylermarien.blogomattic.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.roundToInt

fun calculateMaxWidth(padding: Int, windowManager: WindowManager): Float {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels.toFloat() - (padding * displayMetrics.density)
}

fun convertPixelsToDp(pixels: Int, context: Context) =
    (pixels * context.resources.displayMetrics.density).roundToInt()
