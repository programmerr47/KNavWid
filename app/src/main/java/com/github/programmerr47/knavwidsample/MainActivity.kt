package com.github.programmerr47.knavwidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.programmerr47.knavwid.AutoLayoutNavigationBuilder.Companion.navigation
import com.github.programmerr47.knavwid.MenuAction
import com.github.programmerr47.knavwid.MenuActions

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = navigation(R.layout.abc_action_bar_title_item)
            .menuRes(0, MenuActions.Builder().action(R.id.design_menu_item_action_area, object :
                MenuAction {
                override fun execute() {

                }
            }))
    }
}
