package com.github.programmerr47.knavwid

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation

class NavigationCustomizer(
    private val navigation: ShortNavigationInfo,
    private val defaults: NavigationDefaults = NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!,
    private val backListener: View.OnClickListener? = null
) {
    fun prepareNavigation(toolbar: Toolbar?, bottomNav: AHBottomNavigation?) {
        toolbar?.prepare()
        bottomNav?.prepare()
    }

    private fun Toolbar.prepare() = navigation.toolbar?.let { info ->
        when {
            info.titleRes != 0 -> {
                setTitle(info.titleRes)
            }
            else -> {
                title = info.title
            }
        }

        when {
            info.subtitleRes != 0 -> setSubtitle(info.subtitleRes)
            else -> subtitle = info.subtitle
        }

        when {
            info.logoRes != 0 -> setLogo(info.logoRes)
            else -> logo = info.logo
        }

        when {
            info.navIcon == NO_NAV_ICON -> {
                navigationIcon = null
                setNavigationOnClickListener(null)
            }
            else -> {
                val navIcon = defaults.navigationIcons.fromType(info.navIcon)
                navigationIcon = navIcon?.iconDrawable(context)
                setNavigationOnClickListener(backListener ?: defaults.navigationIconListener)
            }
        }

        menu?.clear()
        if (!info.menuReses.isEmpty()) {
            val actions = info.menuActions.build()
            for (menuRes in info.menuReses) {
                inflateMenu(menuRes)
            }
            setOnMenuItemClickListener { item -> actions.onMenuItemClick(item) }
        }
    }

    fun AHBottomNavigation.prepare() = navigation.bottomBar?.let {info ->
        val navigationItems = defaults.navigationItems
        removeAllItems()
        addItems(navigationItems.bottomNavigationItems())
        setCurrentItem(
            navigationItems.indexFromType(info.current),
            false
        )
        setOnTabSelectedListener { pos, wasSelected ->
            val itemType = defaults.navigationItems[pos].type
            //todo
            true
        }
        titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        isColored = true
    }
}