package com.tyt.deepseektest

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override fun isAndroid(): Boolean {
        return true
    }

    override fun isDesktop(): Boolean {
        return false
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()