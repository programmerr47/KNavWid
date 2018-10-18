package com.github.programmerr47.knavwid

import android.util.SparseArray
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu

interface MenuAction {
    fun execute()
}

object DummyMenuAction : MenuAction {
    override fun execute() {}
}

class MenuActions private constructor(builder: Builder) : PopupMenu.OnMenuItemClickListener {
    private val actions: SparseArray<MenuAction>

    init {
        this.actions = builder.actions
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        val itemId = item.itemId
        actions.get(itemId, DummyMenuAction).execute()
        return actions.indexOfKey(itemId) > 0
    }

    class Builder() {
        internal val actions = SparseArray<MenuAction>()

        constructor(vararg items: Item) : this() {
            for (item in items) {
                action(item.id, item.action)
            }
        }

        fun action(itemId: Int, action: MenuAction?) = apply {
            actions.put(itemId, action ?: DummyMenuAction)
        }

        fun append(menuActions: MenuActions) =
            append(menuActions.actions)

        fun append(anotherActionBuilder: Builder) =
            append(anotherActionBuilder.actions)

        private fun append(actions: SparseArray<MenuAction>) = apply {
            for (i in 0 until actions.size()) {
                this.actions.put(actions.keyAt(i), actions.valueAt(i))
            }
        }

        fun build() = MenuActions(this)
    }

    class Item(
        val id: Int,
        val action: MenuAction
    )
}
