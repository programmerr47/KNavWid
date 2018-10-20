package com.github.programmerr47.knavwid

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
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

    private lateinit var navigationBuilder: NavigationBuilder<*>
    private lateinit var navigationCustomizer: NavigationCustomizer

    protected open fun buildNavigation(): NavigationBuilder<*> =
            CustomLayoutNavigationBuilder(DummyLayoutFactory)

    protected var toolbar: Toolbar? = null
    protected var bottomNavigation: AHBottomNavigation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationBuilder = buildNavigation()
        navigationCustomizer = NavigationCustomizer(navigationBuilder)

        val root = navigationBuilder.layoutFactory().produceLayout(this).also {
            setContentView(it)
        }

        toolbar = root?.bind(navigationBuilder.toolbarId)
        bottomNavigation = root?.bind(navigationBuilder.bottomBarId)
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar = null //todo for easy future excluding common parts from NavigationFragment and NavigationActivity
        bottomNavigation = null
    }

    protected fun invalidateNavigation(newNavigation: NavigationBuilder<*>) {
        navigationBuilder = newNavigation
        navigationCustomizer.prepareNavigation(toolbar, bottomNavigation)
    }

    fun showBottomNavigation() {
        bottomNavigation?.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottomNavigation?.visibility = View.GONE
    }
}