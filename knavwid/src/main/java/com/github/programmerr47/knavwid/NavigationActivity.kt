package com.github.programmerr47.knavwid

import android.os.Bundle
import android.view.View
import android.view.View.NO_ID
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.github.programmerr47.knavwid.layoutfactory.DummyLayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.produceLayout

/**
 * @author Michael Spitsin
 * @since 2018-10-20
 */
abstract class NavigationActivity : AppCompatActivity() {

    private lateinit var navigationBuilder: NavigationBuilderNew
    private lateinit var navigationCustomizer: NavigationCustomizer
    private val navigationDefaults: NavigationDefaults = NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!

    protected open fun buildNavigation(): NavigationBuilderNew = navigation(DummyLayoutFactory).auto()

    protected var toolbar: Toolbar? = null
    protected var bottomNavigation: AHBottomNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationBuilder = buildNavigation()
        navigationCustomizer = NavigationCustomizer(navigationBuilder)

        val root = navigationBuilder.layoutFactory.produceLayout(this).also {
            setContentView(it)
        }

        toolbar = root?.bind(correctId(navigationBuilder.toolbar?.id, navigationDefaults.toolbarId))
        bottomNavigation = root?.bind(correctId(navigationBuilder.bottomBar?.id, navigationDefaults.bottomBarId))
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null //todo for easy future excluding common parts from NavigationFragment and NavigationActivity
        bottomNavigation = null
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilderNew) {
        navigationBuilder = newNavigation
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    fun showBottomNavigation() {
        bottomNavigation?.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottomNavigation?.visibility = View.GONE
    }

    private fun correctId(id: Int?, fallback: Int) = if ((id ?: NO_ID) == NO_ID) fallback else id!!
}