package com.github.programmerr47.knavwid

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import com.github.programmerr47.knavwid.layoutfactory.IdLayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.NavigationLayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.SimpleLayoutFactory

fun navigation(@LayoutRes layoutId: Int, init: NavigationBuilderNew.() -> Unit = {}) =
    navigation(IdLayoutFactory(layoutId), init)

fun navigation(contentView: View, init: NavigationBuilderNew.() -> Unit = {}) =
    navigation(SimpleLayoutFactory(contentView), init)

fun navigation(lf: LayoutFactory, init: NavigationBuilderNew.() -> Unit = {}) =
    NavigationBuilderNew(lf).apply(init)

class NavigationBuilderNew internal constructor(
    private val innerLf: LayoutFactory
) {
    private var isCustomToolbar = false

    val toolbar = ToolbarBuilder()
    val tabs = TabsBuilder()
    val bottomBar = BottomBarBuilder()

    val layoutFactory
        get() = if (isCustomToolbar)
            innerLf
        else
            NavigationLayoutFactory(true, false, null, false, innerLf)

    fun toolbar(isCustom: Boolean = false, init: ToolbarBuilder.() -> Unit = {}): ToolbarBuilder {
        isCustomToolbar = isCustom
        return toolbar.apply(init)
    }

    fun tabs(@StringRes vararg tabReses: Int) {
        tabs.tabs.addAll(tabReses.map { Tab().apply { titleRes = it } })
    }

    fun tabs(vararg tabs: String) {
        this.tabs.tabs.addAll(tabs.map { Tab().apply { title = it } })
    }

    fun tabs(init: TabsBuilder.() -> Unit) = tabs.apply(init)

    fun bottomBar(init: BottomBarBuilder.() -> Unit) = bottomBar.apply(init)
}

class ToolbarBuilder {
    @IdRes var id: Int = -1

    var title: String? = null
    @StringRes var titleRes: Int = -1

    var subtitle: String? = null
    @StringRes var subtitleRes: Int = -1

    var logo: Drawable? = null
    @DrawableRes var logoRes: Int = -1

    val menuReses: MutableList<Int> = mutableListOf()
    val menuActions = MenuActions.Builder()

    fun menu(@MenuRes menuId: Int, vararg items: Pair<Int, MenuAction>) {
        menuReses.add(menuId)
        items.forEach { (id, action) -> menuActions.action(id, action) }
    }

    fun menu(@MenuRes menuId: Int, init: MenuBuilder.() -> Unit = {}) {
        val menu = MenuBuilder(menuId).apply(init)
        menuReses.add(menuId)
        menuActions.append(menu.actions)
    }
}

class MenuBuilder(
    @MenuRes val menuId: Int
) {
    val actions = MenuActions.Builder()

    inline fun item(init: MenuItem.() -> Unit) {
        val item = MenuItem().apply(init)
        item(item.id, item.action)
    }

    fun item(@IdRes id: Int, action: MenuAction) = actions.action(id, action)
}

class MenuItem {
    @IdRes var id: Int = 0
    lateinit var action: MenuAction
}

class TabsBuilder {
    val tabs = mutableListOf<Tab>()

    inline fun res(init: () -> Int) = tab { titleRes = init() }
    inline fun str(init: () -> String) = tab { title = init() }

    inline fun tab(init: Tab.() -> Unit) {
        tabs.add(Tab().apply(init))
    }
}

class Tab {
    @StringRes var titleRes: Int = -1
    var title: String? = null
}

class BottomBarBuilder {
    var current: Int = -1
}