package com.github.programmerr47.knavwid

import android.view.View
import com.github.programmerr47.knavwid.layoutfactory.*

class AutoLayoutNavigationBuilder(layoutFactory: LayoutFactory) : NavigationBuilder<AutoLayoutNavigationBuilder>(
    layoutFactory,
    NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!
) {
    private var includeToolbar: Boolean = false
    private var includeBottomBar: Boolean = false
    private var withoutShadow: Boolean = false

    private var tabTitleReses: IntArray? = null

    override val `this`: AutoLayoutNavigationBuilder get() = this

    override fun layoutFactory() = 
        NavigationLayoutFactory(includeToolbar, includeBottomBar, tabTitleReses, withoutShadow, super.layoutFactory())

    fun includeToolbar() = apply {
        this.includeToolbar = true
    }

    fun excludeToolbar() = apply {
        this.includeToolbar = false
    }

    fun withoutShadow() = apply {
        this.withoutShadow = true
    }

    fun tabs(vararg titleReses: Int) = apply {
        this.tabTitleReses = titleReses
    }

    fun includeBottomNavigation() = apply {
        this.includeBottomBar = true
    }

    fun excludeBottomNavigation() = apply {
        this.includeBottomBar = false
    }

    fun custom() = CustomLayoutNavigationBuilder(super.layoutFactory())

    companion object {

        fun navigation(id: Int) = AutoLayoutNavigationBuilder(IdLayoutFactory(id))
        fun navigation(view: View) = AutoLayoutNavigationBuilder(SimpleLayoutFactory(view))
    }
}
