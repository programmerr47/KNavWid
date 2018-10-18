package com.github.programmerr47.knavwid

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem

import java.util.ArrayList

import java.util.Arrays.asList

class NavigationItems : ArrayList<NavigationItems.NavigationItem> {

    constructor(initialCapacity: Int) : super(initialCapacity) {}
    constructor() : super() {}
    constructor(c: Collection<NavigationItem>) : super(c) {}

    operator fun contains(type: Int) = any { it.type == type }

    fun bottomNavigationItems() = map { it.ahItem }

    fun indexFromType(type: Int) = indexOfFirst { it.type == type }

    fun addAll(vararg navigationItems: NavigationItem) =
            navigationItems.forEach { add(it) }

    class NavigationItem(
        val type: Int,
        val ahItem: AHBottomNavigationItem
    ) {

        companion object {
            fun navigationItem(type: Int, titleRes: Int, iconRes: Int, colorRes: Int): NavigationItem {
                return NavigationItem(type, AHBottomNavigationItem(titleRes, iconRes, colorRes))
            }
        }
    }

    companion object {
        fun of(vararg items: NavigationItem): NavigationItems {
            return NavigationItems(asList(*items))
        }
    }
}
