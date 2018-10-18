package com.github.programmerr47.knavwid

import android.graphics.drawable.Drawable
import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory

import java.util.ArrayList

abstract class NavigationBuilder<T : NavigationBuilder<T>>(
    private val layoutFactory: LayoutFactory,
    private val navigationDefaults: NavigationDefaults
) {

    internal var toolbarId = R.id.toolbar
    internal var bottomBarId = R.id.bottomNavigation

    internal var currentBottomBarItem: Int = 0

    internal var toolbarNavigationIcon: Int = 0
    internal var toolbarTitleRes: Int = 0
    internal var toolbarTitle: CharSequence? = null
    internal var toolbarSubtitleRes: Int = 0
    internal var toolbarSubtitle: CharSequence? = null
    internal var toolbarLogoRes: Int = 0
    internal var toolbarLogo: Drawable? = null

    internal var menuRes: MutableList<Int> = ArrayList()
    internal var menuActions = MenuActions.Builder()

    protected abstract val `this`: T

    init {
        this.toolbarNavigationIcon = navigationDefaults.defaultNavigationIconType
        this.currentBottomBarItem = navigationDefaults.defaultBottomNavigationItem
    }

    open fun layoutFactory(): LayoutFactory {
        return layoutFactory
    }

    fun navigationDefaults(): NavigationDefaults {
        return navigationDefaults
    }

    fun currentBottomBarItem(currentBottomBarItem: Int) = apply {
        if (currentBottomBarItem !in navigationDefaults.navigationItems) {
            throw IllegalArgumentException("There is no navigation item for type: $currentBottomBarItem")
        }

        this.currentBottomBarItem = currentBottomBarItem
        return `this`
    }

    fun toolbarNavigationIcon(toolbarNavigationIcon: Int) = apply {
        if (toolbarNavigationIcon !in navigationDefaults.navigationIcons && toolbarNavigationIcon != NO_NAV_ICON) {
            throw IllegalArgumentException("There is no navigation icon for type: $toolbarNavigationIcon")
        }

        this.toolbarNavigationIcon = toolbarNavigationIcon
    }

    fun toolbarTitleRes(toolbarTitleRes: Int) = apply {
        this.toolbarTitleRes = toolbarTitleRes
    }

    fun toolbarTitle(toolbarTitle: CharSequence) = apply {
        this.toolbarTitle = toolbarTitle
    }

    fun toolbarSubtitleRes(toolbarSubtitleRes: Int) = apply {
        this.toolbarSubtitleRes = toolbarSubtitleRes
    }

    fun toolbarSubtitle(toolbarSubtitle: CharSequence) = apply {
        this.toolbarSubtitle = toolbarSubtitle
    }

    fun toolbarLogoRes(toolbarLogoRes: Int) = apply {
        this.toolbarLogoRes = toolbarLogoRes
    }

    fun toolbarLogo(toolbarLogo: Drawable) = apply {
        this.toolbarLogo = toolbarLogo
    }

    fun menuRes(menuRes: Int, vararg items: MenuActions.Item) =
        menuRes(menuRes, MenuActions.Builder(*items))

    fun menuRes(menuRes: Int, menuBuilder: MenuActions.Builder) = apply {
        this.menuRes.add(menuRes)
        this.menuActions.append(menuBuilder)
    }

    fun menuRes(menuRes: Int, menuActions: MenuActions) = apply {
        this.menuRes.add(menuRes)
        this.menuActions.append(menuActions)
    }

    protected inline fun apply(init: T.() -> Unit): T {
        init.invoke(`this`)
        return `this`
    }

    companion object {
        const val NO_NAV_ICON = -1
    }
}
