package com.github.programmerr47.knavwid

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

import android.util.TypedValue.COMPLEX_UNIT_DIP
import androidx.core.graphics.drawable.DrawableCompat

fun Drawable.tintCompat(@ColorInt tint: Int) = DrawableCompat.setTint(this, tint)
fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun dp(context: Context, dp: Float): Float {
    return applyDimen(context, dp, COMPLEX_UNIT_DIP)
}

private fun applyDimen(context: Context, value: Float, unit: Int): Float {
    val res = context.resources
    return TypedValue.applyDimension(unit, value, res.displayMetrics)
}

fun <T> bind(view: View, id: Int): T {
    return view.findViewById<View>(id) as T
}
