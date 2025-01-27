package com.tyt.deepseektest.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.tyt.deepseektest.db.TestDatabase
import java.io.File

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
//        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db")
//        TestDatabase.Schema.create(driver)
//        return driver
        val databasePath = File(System.getProperty("user.home"), "DeepseekTestDB.db")
        return JdbcSqliteDriver("jdbc:sqlite:" + databasePath.absolutePath).also {
            TestDatabase.Schema.create(it)
        }
    }
}