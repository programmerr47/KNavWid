package com.github.programmerr47.knavwidsample

import com.github.programmerr47.knavwid.AutoLayoutNavigationBuilder.Companion.navigation
import com.github.programmerr47.knavwid.NavigationActivity
import com.github.programmerr47.knavwid.NavigationBuilder

class MainActivity : NavigationActivity() {

    override fun buildNavigation(): NavigationBuilder<*> {
        return navigation(R.layout.activity_main)
            .includeToolbar()
            .toolbarTitle("Test Title")
            .toolbarSubtitle("Subtitle for test")
            .tabs(R.string.app_name, R.string.app_name)
    }
}
