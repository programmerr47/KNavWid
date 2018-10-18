package com.github.programmerr47.knavwid

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat.create
import java.util.*

class NavigationIcons : ArrayList<NavigationIcons.NavigationIcon> {
    constructor(initialCapacity: Int) : super(initialCapacity) {}
    constructor() : super() {}
    constructor(c: Collection<NavigationIcon>) : super(c) {}

    operator fun contains(type: Int) = fromType(type) != null

    fun fromType(type: Int) = find { it.type == type }

    class NavigationIcon private constructor(
        val type: Int,
        @param:DrawableRes private val drawableRes: Int,
        private val drawable: Drawable?
    ) {
        private var drawableFromRes: Drawable? = null

        fun iconDrawable(context: Context) = when {
            drawable != null -> drawable
            drawableFromRes != null -> drawableFromRes
            else -> {
                create(context.resources, drawableRes, context.theme)!!.apply {
                    tintCompat(context.getColorCompat(android.R.color.white))
                }.also { drawableFromRes = it }
            }
        }

        companion object {

            fun navigationIcon(type: Int, @DrawableRes drawableRes: Int): NavigationIcon {
                return NavigationIcon(type, drawableRes, null)
            }

            fun navigationIcon(type: Int, drawable: Drawable): NavigationIcon {
                return NavigationIcon(type, 0, drawable)
            }
        }
    }
}
