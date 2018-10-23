package com.github.programmerr47.knavwidsample

import com.github.programmerr47.knavwid.NavigationActivity
import com.github.programmerr47.knavwid.navigation

class MainActivity : NavigationActivity() {
    override fun buildNavigation() = navigation(R.layout.activity_main) {
        toolbar {
            title = "Test Title"
            subtitle = "Subtitle for test"
        }

        tabs {
            tab { title = "test1" }
            tab { titleRes = R.string.app_name }
            tab { title = "testIk" }
        }
    }

//    override fun buildNavigation() = navigation(R.layout.activity_main)
//            .includeToolbar()
//            .toolbarTitle("Test Title")
//            .toolbarSubtitle("Subtitle for test")
//            .tabs(R.string.app_name, R.string.app_name)
}
