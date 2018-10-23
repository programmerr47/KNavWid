package com.github.programmerr47.knavwid

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.NO_ID
import androidx.annotation.*
import com.github.programmerr47.knavwid.layoutfactory.IdLayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.LayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.NavigationLayoutFactory
import com.github.programmerr47.knavwid.layoutfactory.SimpleLayoutFactory

const val NO_NAV_ICON = -1

fun navigation(@LayoutRes layoutId: Int, init: AutoNavigationBuilder.() -> Unit) = navigation(layoutId).auto(init)
fun navigation(contentView: View, init: AutoNavigationBuilder.() -> Unit) = navigation(contentView).auto(init)
fun navigation(lf: LayoutFactory, init: AutoNavigationBuilder.() -> Unit) = navigation(lf).auto(init)

fun navigation(@LayoutRes layoutId: Int): NavigationFactory = NavigationSystem(IdLayoutFactory(layoutId))
fun navigation(contentView: View): NavigationFactory = NavigationSystem(SimpleLayoutFactory(contentView))
fun navigation(lf: LayoutFactory): NavigationFactory = NavigationSystem(lf)

interface NavigationFactory {
    fun auto(): AutoNavigationBuilder
    fun custom(): NavigationBuilderNew
}

inline fun NavigationFactory.auto(init: AutoNavigationBuilder.() -> Unit) = auto().apply(init)
inline fun NavigationFactory.custom(init: NavigationBuilderNew.() -> Unit) = custom().apply(init)

private class NavigationSystem(
    private val innerLf: LayoutFactory
) : NavigationFactory {
    override fun auto() = AutoNavigationBuilder(innerLf)
    override fun custom() = NavigationBuilderNew(innerLf)
}

interface ShortNavigationInfo {
    val toolbar: ToolbarInfo?
    val bottomBar: BottomBarInfo?
}

interface NavigationInfo : ShortNavigationInfo {
    val tabs: TabsInfo
}

interface ToolbarInfo {
    @get:IdRes val id: Int

    val title: String?
    @get:StringRes val titleRes: Int

    val subtitle: String?
    @get:StringRes val subtitleRes: Int

    val logo: Drawable?
    @get:DrawableRes val logoRes: Int

    val navIcon: Int
    val withShadow: Boolean

    val menuReses: List<Int>
    val menuActions: MenuActions.Builder
}

interface TabsInfo : Iterable<Tab>

interface BottomBarInfo {
    @get:IdRes val id: Int
    val current: Int
}

open class NavigationBuilderNew internal constructor(
    private val innerLf: LayoutFactory
) : ShortNavigationInfo {

    final override var toolbar: ToolbarBuilder? = null
        private set
    final override var bottomBar: BottomBarBuilder? = null
        private set

    open val layoutFactory get() = innerLf

    fun toolbar(init: ToolbarBuilder.() -> Unit = {}) = toolbar().apply(init)

    private fun toolbar() = toolbar ?: ToolbarBuilder().also { toolbar = it }

    fun bottomBar(init: BottomBarBuilder.() -> Unit) = bottomBar().apply(init)

    private fun bottomBar() = bottomBar ?: BottomBarBuilder().also { bottomBar = it }
}

class AutoNavigationBuilder internal constructor(
    private val innerLf: LayoutFactory,
    private val defaults: NavigationDefaults = NavigationDefaults.NavigationDefaultsHolder.navigationDefaults()!!
) : NavigationBuilderNew(innerLf), NavigationInfo {

    override var tabs = TabsBuilder()
        private set

    override val layoutFactory get() = NavigationLayoutFactory(this, defaults, innerLf)

    fun tabs(@StringRes vararg tabReses: Int) {
        tabs.tabs.addAll(tabReses.map { Tab().apply { titleRes = it } })
    }

    fun tabs(vararg tabs: String) {
        this.tabs.tabs.addAll(tabs.map { Tab().apply { title = it } })
    }

    inline fun tabs(init: TabsBuilder.() -> Unit) = tabs.apply(init)
}

class ToolbarBuilder : ToolbarInfo {
    @IdRes override var id: Int = NO_ID

    override var title: String? = null
    @StringRes override var titleRes: Int = 0

    override var subtitle: String? = null
    @StringRes override var subtitleRes: Int = 0

    override var logo: Drawable? = null
    @DrawableRes override var logoRes = 0

    override var navIcon: Int = NO_NAV_ICON
    override var withShadow = true

    override val menuReses: MutableList<Int> = mutableListOf()
    override val menuActions = MenuActions.Builder()

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
    @IdRes var id: Int = NO_ID
    lateinit var action: MenuAction
}

class TabsBuilder(
    val tabs: MutableList<Tab> = mutableListOf()
) : TabsInfo, Iterable<Tab> by tabs {

    inline fun res(init: () -> Int) = tab { titleRes = init() }
    inline fun str(init: () -> String) = tab { title = init() }

    inline fun tab(init: Tab.() -> Unit) {
        tabs.add(Tab().apply(init))
    }
}

class Tab {
    @StringRes var titleRes: Int = 0
    var title: String? = null
}

class BottomBarBuilder : BottomBarInfo {
    @IdRes override var id: Int = NO_ID
    override var current: Int = 0
}