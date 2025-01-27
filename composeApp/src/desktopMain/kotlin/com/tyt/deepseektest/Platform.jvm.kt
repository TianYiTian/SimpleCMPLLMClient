package com.tyt.deepseektest

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
    override fun isAndroid(): Boolean {
        return false
    }

    override fun isDesktop(): Boolean {
        return true
    }
}

actual fun getPlatform(): Platform = JVMPlatform()