package com.github.programmerr47.knavwid

import android.os.Bundle
import android.view.*
import android.view.View.*
import android.view.animation.Animation
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.github.programmerr47.knavwid.layoutfactory.DummyLayoutFactory

abstract class NavigationFragment : Fragment() {

    private lateinit var navigationBuilder: NavigationBuilderNew
    private lateinit var navigationCustomizer: NavigationCustomizer
    private var navDefaults = NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!

    protected var toolbar: Toolbar? = null
    protected var bottomNavigation: AHBottomNavigation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationBuilder = buildNavigation()
        navigationCustomizer = NavigationCustomizer(navigationBuilder)
        return navigationBuilder.layoutFactory.produceLayout(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.bind(correctId(navigationBuilder.toolbar?.id, navDefaults.toolbarId))
        bottomNavigation = view.bind(correctId(navigationBuilder.bottomBar?.id, navDefaults.bottomBarId))
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    override fun onDestroyView() {
        toolbar = null
        bottomNavigation = null
        super.onDestroyView()
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilderNew) {
        navigationBuilder = newNavigation
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        val result = object : Animation() {}
        result.duration = 0
        return result
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

    protected open fun buildNavigation() = navigation(DummyLayoutFactory).auto()

    private fun correctId(id: Int?, fallback: Int) = if ((id ?: NO_ID) == NO_ID) fallback else id!!
}
