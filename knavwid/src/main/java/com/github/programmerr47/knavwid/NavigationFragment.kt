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

abstract class NavigationFragment : Fragment() {

    private lateinit var navigationBuilder: NavigationBuilder<*>
    private lateinit var navigationCustomizer: NavigationCustomizer

    protected var toolbar: Toolbar? = null
    protected var bottomNavigation: AHBottomNavigation? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        navigationBuilder = buildNavigation()
        navigationCustomizer = NavigationCustomizer(navigationBuilder)
        return navigationBuilder.layoutFactory().produceLayout(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.bind(navigationBuilder.toolbarId)
        bottomNavigation = view.bind(navigationBuilder.bottomBarId)
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    override fun onDestroyView() {
        toolbar = null
        bottomNavigation = null
        super.onDestroyView()
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilder<*>) {
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

    protected open fun buildNavigation(): NavigationBuilder<*> {
        return CustomLayoutNavigationBuilder(DummyLayoutFactory)
    }
}
