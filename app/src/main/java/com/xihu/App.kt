package com.xihu

import android.app.Application
import android.content.Context
import com.xihu.utils.initialize

class App :Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        initialize()
    }
}