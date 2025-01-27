package com.tyt.deepseektest


expect object SimplePreferences {
    fun readBoolean(name: String, default: Boolean = false): Boolean
    fun writeBoolean(name: String, value: Boolean)
}