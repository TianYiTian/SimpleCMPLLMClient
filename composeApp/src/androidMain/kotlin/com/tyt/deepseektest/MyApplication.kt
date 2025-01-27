package com.tyt.deepseektest

import android.app.Application
import android.content.Context



class MyApplication: Application() {
    companion object{
        lateinit var ApplicationContext:Context
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = applicationContext
    }
}