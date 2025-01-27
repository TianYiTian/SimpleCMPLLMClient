package com.tyt.deepseektest.data

import app.cash.sqldelight.db.SqlDriver
import com.tyt.deepseektest.NetworkService
import com.tyt.deepseektest.data.db.ChatDBModel
import com.tyt.deepseektest.db.TestDatabase

expect class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}

internal object Database {
    private val database = TestDatabase(DatabaseDriverFactory().createDriver())
    private val dbQuery = database.testDatabaseQueries

    internal fun insertTest(int: Int, string: String): Long {
        return dbQuery.insertTest(int.toLong(), string).executeAsOne()
    }

    internal fun getTests(): List<Test> {
        return dbQuery.getTests { _, int, string ->
            Test(int.toInt(), string)
        }.executeAsList()
    }

    internal fun insertChat(model: ChatDBModel): Long {
        model.run {
            if ((model.id ?: -1) > 0) {
                //update
                dbQuery.updateChat(
                    NetworkService.GSON.toJson(requestModel), apiAddress, token, title, lastModified, model.id!!
                )
                return model.id!!
            } else {
                return dbQuery.insertChat(NetworkService.GSON.toJson(requestModel), apiAddress, token, title, lastModified)
                    .executeAsOne()
            }
        }
    }

    internal fun deleteChat(model: ChatDBModel) {
        model.id?.let {
            dbQuery.deleteChat(it)
        }
    }

    internal fun getChats(): List<ChatDBModel> {
        return dbQuery.getChats { id, requestModel, apiAddress, token, title, lastModified ->
            ChatDBModel(
                NetworkService.GSON.fromJson(requestModel, ChatRequestModel::class.java),
                apiAddress,
                token,
                title,
                lastModified,
                id
            )
        }.executeAsList()
    }

    internal fun insertModel(model: ModelItem) {
        model.run {
            val time = System.currentTimeMillis()
            if (dbQuery.checkModelExists(model.name).executeAsOne()) {
                dbQuery.updateModel(alias, time, name)
            } else {
                dbQuery.insertModel(name, alias, time)
            }
        }
    }

    internal fun deleteModel(model: String) {
        model.run { dbQuery.deleteModel(model) }
    }

    internal fun getModels(): List<ModelItem> {
        return dbQuery.getModels { name, alias, time ->
            ModelItem(name, alias, time)
        }.executeAsList()
    }

    internal fun checkModelExists(name: String): Boolean {
        return dbQuery.checkModelExists(name).executeAsOne()
    }

    internal fun insertApiAddress(apiAddress: ApiAddressItem) {
        apiAddress.run {
            val time = System.currentTimeMillis()
            if (dbQuery.checkApiAddressExists(apiAddress.apiAddress).executeAsOne()) {
                dbQuery.updateApiAddress(alias, time, apiAddress.apiAddress)
            } else {
                dbQuery.insertApiAddress(apiAddress.apiAddress, alias, time)
            }
        }
    }

    internal fun deleteApiAddress(apiAddress: String) {
        apiAddress.run { dbQuery.deleteApiAddress(apiAddress) }
    }

    internal fun getApiAddresses(): List<ApiAddressItem> {
        return dbQuery.getApiAddresses { apiAddress, alias, lastModified ->
            ApiAddressItem(apiAddress, alias, lastModified)
        }.executeAsList()
    }

    internal fun checkApiAddressExists(name: String): Boolean {
        return dbQuery.checkApiAddressExists(name).executeAsOne()
    }

    internal fun insertToken(token: TokenItem) {
        token.run {
            val time = System.currentTimeMillis()
            if (dbQuery.checkTokenExists(token.token).executeAsOne()) {
                dbQuery.updateToken(alias, time, token.token)
            } else {
                dbQuery.insertToken(token.token, alias, time)
            }
        }
    }

    internal fun deleteToken(token: String) {
        token.run { dbQuery.deleteToken(token) }
    }

    internal fun getTokens(): List<TokenItem> {
        return dbQuery.getTokens { token, alias, lastModified ->
            TokenItem(token, alias, lastModified)
        }.executeAsList()
    }

    internal fun checkTokenExists(name: String): Boolean {
        return dbQuery.checkTokenExists(name).executeAsOne()
    }
}