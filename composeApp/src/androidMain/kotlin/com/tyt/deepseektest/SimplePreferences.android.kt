package com.tyt.deepseektest

import android.content.Context
import com.tyt.deepseektest.MyApplication.Companion.ApplicationContext

actual object SimplePreferences {
    private val sharedPreferences = ApplicationContext.getSharedPreferences("SimplePreferences", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    actual fun readBoolean(name: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(name,default)
    }

    actual fun writeBoolean(name: String, value: Boolean) {
        editor.putBoolean(name,value)
        editor.apply()
    }
}