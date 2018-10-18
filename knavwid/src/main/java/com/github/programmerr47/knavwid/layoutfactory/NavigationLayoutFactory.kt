package com.github.programmerr47.knavwid.layoutfactory

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.github.programmerr47.knavwid.R

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.VERTICAL
import com.github.programmerr47.knavwid.dp

class NavigationLayoutFactory(
    private val includeToolbar: Boolean,
    private val includeBottomBar: Boolean,
    private val origin: LayoutFactory
) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?) = LinearLayout(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        orientation = VERTICAL

        if (includeToolbar) {
            inflater.inflate(R.layout.toolbar, this)
        }

        val child = origin.produceLayout(inflater, this)
        val childParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
            if (includeBottomBar) weight = 1f
        }
        addView(child, childParams)

        if (includeBottomBar) {
            val bottomNavigation = AHBottomNavigation(context)
            bottomNavigation.id = R.id.bottomNavigation
            addView(
                bottomNavigation,
                LinearLayout.LayoutParams(MATCH_PARENT, dp(context, 56f).toInt())
            )
        }
    }
}
