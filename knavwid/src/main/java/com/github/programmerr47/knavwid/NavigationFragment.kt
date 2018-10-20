package com.github.programmerr47.knavwid

import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener
import com.github.programmerr47.knavwid.layoutfactory.DummyLayoutFactory

import android.view.View.GONE
import android.view.View.VISIBLE
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW
import com.github.programmerr47.knavwid.NavigationBuilder.Companion.NO_NAV_ICON

abstract class NavigationFragment : Fragment(), OnTabSelectedListener {

    private lateinit var navigationBuilder: NavigationBuilder<*>

    protected var toolbar: Toolbar? = null
    protected var bottomNavigation: AHBottomNavigation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationBuilder = buildNavigation()
        return navigationBuilder.layoutFactory().produceLayout(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.bind(navigationBuilder.toolbarId)
        bottomNavigation = view.bind(navigationBuilder.bottomBarId)
        prepareNavigation()
    }

    override fun onDestroyView() {
        toolbar = null
        bottomNavigation = null
        super.onDestroyView()
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilder<*>) {
        navigationBuilder = newNavigation
        prepareNavigation()
    }

    private fun prepareNavigation() {
        toolbar?.let { prepareToolbar(it) }
        bottomNavigation?.let { prepareBottomNavigation(it) }
    }

    protected fun prepareToolbar(toolbar: Toolbar): Unit = toolbar.run {
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
            navigationBuilder.toolbarNavigationIcon == NO_NAV_ICON -> {
                navigationIcon = null
                setNavigationOnClickListener(null)
            }
            else -> {
                val navIcon = navigationBuilder.navigationDefaults().navigationIcons
                    .fromType(navigationBuilder.toolbarNavigationIcon)
                navigationIcon = navIcon?.iconDrawable(context)
                setNavigationOnClickListener(navigationBuilder.navigationDefaults().navigationIconListener)
            }
        }

        val menu = toolbar.menu
        menu?.clear()
        if (!navigationBuilder.menuRes.isEmpty()) {
            val actions = navigationBuilder.menuActions.build()
            for (menuRes in navigationBuilder.menuRes) {
                toolbar.inflateMenu(menuRes)
            }
            toolbar.setOnMenuItemClickListener { item -> actions.onMenuItemClick(item) }
        }
    }

    protected fun prepareBottomNavigation(bottomNavigation: AHBottomNavigation) {
        val navigationItems = navigationBuilder.navigationDefaults().navigationItems
        bottomNavigation.removeAllItems()
        bottomNavigation.addItems(navigationItems.bottomNavigationItems())
        bottomNavigation.setCurrentItem(
            navigationItems.indexFromType(navigationBuilder.currentBottomBarItem),
            false
        )
        bottomNavigation.setOnTabSelectedListener(this)
        bottomNavigation.titleState = ALWAYS_SHOW
        bottomNavigation.isColored = true
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        val result = object : Animation() {}
        result.duration = 0
        return result
    }

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        val itemType = navigationBuilder.navigationDefaults().navigationItems[position].type
        return onTabTypeSelected(itemType, wasSelected)
    }

    fun onTabTypeSelected(type: Int, wasSelected: Boolean): Boolean {
        return true
    }

    fun showBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation!!.visibility = VISIBLE
        }
    }

    fun hideBottomNavigation() {
        if (bottomNavigation != null) {
            bottomNavigation!!.visibility = GONE
        }
    }

    protected fun buildNavigation(): NavigationBuilder<*> {
        return CustomLayoutNavigationBuilder(DummyLayoutFactory)
    }
}
