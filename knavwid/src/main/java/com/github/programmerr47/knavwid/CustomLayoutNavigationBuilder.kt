package com.github.programmerr47.knavwid

import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory

class CustomLayoutNavigationBuilder(layoutFactory: LayoutFactory) : NavigationBuilder<CustomLayoutNavigationBuilder>(
    layoutFactory,
    NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!
) {

    override val `this`: CustomLayoutNavigationBuilder get() = this

    fun toolbarId(toolbarId: Int) = apply {
        this.toolbarId = toolbarId
    }

    fun bottomBarId(bottomBarId: Int) = apply {
        this.bottomBarId = bottomBarId
    }

    fun auto() = AutoLayoutNavigationBuilder(layoutFactory())
}
