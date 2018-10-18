package com.github.programmerr47.knavwid.layoutfactory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class IdLayoutFactory(
    private val layoutId: Int
) : LayoutFactory {

    override fun produceLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(layoutId, container, false)
    }
}
