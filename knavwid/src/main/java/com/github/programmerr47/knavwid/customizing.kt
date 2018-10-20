package com.github.programmerr47.knavwid

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

class NavigationCustomizer(
    private val navigationBuilder: NavigationBuilder<*>,
    private val backListener: View.OnClickListener? = null
) {
    fun prepareNavigation(toolbar: Toolbar?, bottomNav: AHBottomNavigation?) {
        toolbar?.prepare()
        bottomNav?.prepare()
    }

    private fun Toolbar.prepare() {
        when {
            navigationBuilder.toolbarTitleRes != 0 -> setTitle(navigationBuilder.toolbarTitleRes)
            else -> title = navigationBuilder.toolbarTitle
        }

        when {
            navigationBuilder.toolbarSubtitleRes != 0 -> setSubtitle(navigationBuilder.toolbarSubtitleRes)
            else -> subtitle = navigationBuilder.toolbarSubtitle
        }

        when {
            navigationBuilder.toolbarLogoRes != 0 -> setLogo(navigationBuilder.toolbarLogoRes)
            else -> logo = navigationBuilder.toolbarLogo
        }

        when {
            navigationBuilder.toolbarNavigationIcon == NavigationBuilder.NO_NAV_ICON -> {
                navigationIcon = null
                setNavigationOnClickListener(null)
            }
            else -> {
                val navIcon = navigationBuilder.navigationDefaults().navigationIcons
                    .fromType(navigationBuilder.toolbarNavigationIcon)
                navigationIcon = navIcon?.iconDrawable(context)
                setNavigationOnClickListener(backListener ?: navigationBuilder.navigationDefaults().navigationIconListener)
            }
        }

        menu?.clear()
        if (!navigationBuilder.menuRes.isEmpty()) {
            val actions = navigationBuilder.menuActions.build()
            for (menuRes in navigationBuilder.menuRes) {
                inflateMenu(menuRes)
            }
            setOnMenuItemClickListener { item -> actions.onMenuItemClick(item) }
        }
    }

    fun AHBottomNavigation.prepare() {
        val navigationItems = navigationBuilder.navigationDefaults().navigationItems
        removeAllItems()
        addItems(navigationItems.bottomNavigationItems())
        setCurrentItem(
            navigationItems.indexFromType(navigationBuilder.currentBottomBarItem),
            false
        )
        setOnTabSelectedListener { pos, wasSelected ->
            val itemType = navigationBuilder.navigationDefaults().navigationItems[pos].type
            true
        }
        titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        isColored = true
    }
}