package com.github.programmerr47.knavwid.layoutfactory

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.widget.LinearLayout
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.VERTICAL
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import com.github.programmerr47.knavwid.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout

class NavigationLayoutFactory(
    private val navigationInfo: NavigationInfo,
    private val defaults: NavigationDefaults,
    private val origin: LayoutFactory) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?) = LinearLayout(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        orientation = VERTICAL

        navigationInfo.toolbar?.let { toolbar ->
            inflater.inflate(R.layout.toolbar, this)
            bind<AppBarLayout>(R.id.appBarLayout) {
                if (navigationInfo.tabs.count() != 0) {
                    addView(divider(context))
                    addView(tabLayout(context).apply {
                        navigationInfo.tabs.forEach { tab ->
                            addTab(newTab().run {
                                when {
                                    tab.titleRes != 0 -> setText(tab.titleRes)
                                    else -> setText(tab.title)
                                }
                            })
                        }
                    })
                }

                setStateListElevationAnimator(getShadowHeight())

                if (!toolbar.withShadow) {
                    addView(divider(context))
                }
            }

            bind<Toolbar>(R.id.toolbar) {
                id = correctId(toolbar.id, defaults.toolbarId)
            }
        }

        val child = origin.produceLayout(inflater, this)
        val childParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
            if (navigationInfo.bottomBar != null) weight = 1f
        }
        addView(child, childParams)

        navigationInfo.bottomBar?.let { bottomBar ->
            val bottomNavigation = AHBottomNavigation(context).apply {
                id = correctId(bottomBar.id, defaults.bottomBarId)
            }
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
        return if (navigationInfo.toolbar?.withShadow ?: true) context.resources.getDimension(R.dimen.knavwid_toolbar_shadow_height) else 0f
    }

    private fun isLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    private fun correctId(id: Int, fallback: Int) = if (id == NO_ID) fallback else id
}
