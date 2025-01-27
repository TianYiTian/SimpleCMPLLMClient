package com.tyt.deepseektest

interface Platform {
    val name: String
    fun isAndroid():Boolean
    fun isDesktop():Boolean
}

expect fun getPlatform(): Platform