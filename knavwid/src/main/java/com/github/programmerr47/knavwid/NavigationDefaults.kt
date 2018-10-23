package com.github.programmerr47.knavwid

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.NO_ID
import androidx.annotation.DrawableRes
import com.github.programmerr47.knavwid.NavigationIcons.NavigationIcon

import java.util.Arrays.asList

class NavigationDefaults {
    val navigationItems = NavigationItems()
    val navigationIcons = NavigationIcons()
    var navigationIconListener = DUMMY_NAV_ICON_LISTENER
        private set
    var defaultNavigationIconType: Int = 0
        private set
    var defaultBottomNavigationItem: Int = 0
        private set

    var toolbarId: Int = NO_ID
    var bottomBarId: Int = NO_ID

    fun navigationItems(vararg navigationItems: NavigationItems.NavigationItem) = apply {
        this.navigationItems.addAll(*navigationItems)
    }

    fun navigationItem(type: Int, titleRes: Int, iconRes: Int, colorRes: Int) =
        navigationItem(NavigationItems.NavigationItem.navigationItem(type, titleRes, iconRes, colorRes))

    fun navigationItem(navigationItem: NavigationItems.NavigationItem) = apply {
        navigationItems.add(navigationItem)
    }

    fun navigationIcons(vararg navigationIcons: NavigationIcon) = apply {
        this.navigationIcons.addAll(asList(*navigationIcons))
    }

    fun navigationIcon(type: Int, @DrawableRes drawableRes: Int) =
        navigationIcon(NavigationIcon.navigationIcon(type, drawableRes))

    fun navigationIcon(type: Int, drawable: Drawable) =
        navigationIcon(NavigationIcon.navigationIcon(type, drawable))

    fun navigationIcon(navigationIcon: NavigationIcon) = apply {
        this.navigationIcons.add(navigationIcon)
    }

    fun navigationIconListener(listener: View.OnClickListener?) = apply {
        this.navigationIconListener = listener ?: DUMMY_NAV_ICON_LISTENER
    }

    fun defaultNavigationIconType(defaultNavigationIconType: Int) = apply {
        this.defaultNavigationIconType = defaultNavigationIconType
    }

    fun defaultBottomNavigationItem(defaultBottomNavigationItem: Int) = apply {
        this.defaultBottomNavigationItem = defaultBottomNavigationItem
    }

    object NavigationDefaultsHolder {
        private var defaults: NavigationDefaults? = null

        fun initDefaults(defaults: NavigationDefaults) {
            NavigationDefaultsHolder.defaults = defaults
        }

        fun navigationDefaults(): NavigationDefaults? {
            return defaults
        }
    }

    companion object {
        private val DUMMY_NAV_ICON_LISTENER: View.OnClickListener = View.OnClickListener { }
    }
}
