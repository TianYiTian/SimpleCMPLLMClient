package com.tyt.deepseektest.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.tyt.deepseektest.MyApplication
import com.tyt.deepseektest.db.TestDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TestDatabase.Schema, context = MyApplication.ApplicationContext,"test.db")
    }
}