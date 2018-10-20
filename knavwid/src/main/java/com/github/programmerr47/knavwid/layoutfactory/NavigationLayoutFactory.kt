package com.github.programmerr47.knavwid.layoutfactory

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.github.programmerr47.knavwid.R

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.LayoutRes
import com.github.programmerr47.knavwid.bind
import com.github.programmerr47.knavwid.dp
import com.github.programmerr47.knavwid.getColorCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class NavigationLayoutFactory(
    private val includeToolbar: Boolean,
    private val includeBottomBar: Boolean,
    private val tabTitleReses: IntArray?,
    private val withoutShadow: Boolean,
    private val origin: LayoutFactory
) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?) = LinearLayout(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        orientation = VERTICAL

        if (includeToolbar) {
            inflater.inflate(R.layout.toolbar, this)
            bind<AppBarLayout>(R.id.appBarLayout) {
                if (tabTitleReses != null) {
                    addView(divider(context))
                    addView(tabLayout(context).apply {
                        tabTitleReses.forEach { tabTitleRes ->
                            addTab(newTab().setText(tabTitleRes))
                        }
                    })
                }

                setStateListElevationAnimator(getShadowHeight())

                if (withoutShadow) {
                    addView(divider(context))
                }
            }
        }

        val child = origin.produceLayout(inflater, this)
        val childParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
            if (includeBottomBar) weight = 1f
        }
        addView(child, childParams)

        if (includeBottomBar) {
            val bottomNavigation = AHBottomNavigation(context)
            addView(
                bottomNavigation,
                LinearLayout.LayoutParams(MATCH_PARENT, dp(context, 56f).toInt())
            )
        }
    }

    private fun tabLayout(context: Context) = context.inflate(R.layout.tablayout) as TabLayout

    private fun divider(context: Context) = View(context).apply {
        setBackgroundColor(context.getColorCompat(R.color.knavwid_divider))
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, context.resources.getDimensionPixelSize(R.dimen.knavwid_divider_thickness))
    }

    private fun Context.inflate(@LayoutRes resource: Int) =
        LayoutInflater.from(this).inflate(resource, null)

    private fun View.setStateListElevationAnimator(elevation: Float) {
        if (isLollipop()) {
            val stateListAnimator = StateListAnimator()
            stateListAnimator.addState(IntArray(0), ObjectAnimator.ofFloat(this, "elevation", elevation).setDuration(0))
            this.stateListAnimator = stateListAnimator
        }
    }

    private fun View.getShadowHeight(): Float {
        return if (withoutShadow) 0f else context.resources.getDimension(R.dimen.knavwid_toolbar_shadow_height)
    }

    private fun isLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}
