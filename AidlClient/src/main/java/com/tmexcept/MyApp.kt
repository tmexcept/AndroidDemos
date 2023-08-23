package com.tmexcept

import android.app.Application
import android.content.Context

class MyApp:Application() {
    override fun attachBaseContext(base: Context?) {
        startTime = 0
        super.attachBaseContext(base)
    }

    companion object{
        @JvmField
        var startTime : Long = 0
    }
}