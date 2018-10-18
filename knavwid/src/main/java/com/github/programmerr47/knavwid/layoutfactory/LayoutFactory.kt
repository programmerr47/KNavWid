package com.github.programmerr47.knavwid.layoutfactory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface LayoutFactory {
    fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View?
}

object DummyLayoutFactory : LayoutFactory {
    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View? = null
}

class SimpleLayoutFactory(
    private val view: View
) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View? {
        return view
    }
}
