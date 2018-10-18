package com.github.programmerr47.knavwidsample

import android.app.Application
import com.github.programmerr47.knavwid.NavigationDefaults

/**
 * @author Michael Spitsin
 * @since 2018-10-18
 */
class SandboxApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NavigationDefaults.NavigationDefaultsHolder.initDefaults(NavigationDefaults())
    }
}