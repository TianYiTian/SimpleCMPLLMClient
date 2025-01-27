package com.tyt.deepseektest

import androidx.compose.runtime.mutableStateListOf
import com.tyt.deepseektest.data.BaseDataItem
import com.tyt.deepseektest.data.Database
import com.tyt.deepseektest.data.db.ChatDBModel

object DataHolder {
    val ChatList = mutableStateListOf<ChatDBModel>()
    val ModelList = mutableStateListOf<BaseDataItem>()
    val ApiAddressList = mutableStateListOf<BaseDataItem>()
    val TokenList = mutableStateListOf<BaseDataItem>()

    fun updateModelList() {
        ModelList.clear()
        ModelList.addAll(Database.getModels().sortedByDescending { it.lastModifiedTime })
    }

    fun updateApiAddressList() {
        ApiAddressList.clear()
        ApiAddressList.addAll(Database.getApiAddresses().sortedByDescending { it.lastModifiedTime })
    }

    fun updateTokenList() {
        TokenList.clear()
        TokenList.addAll(Database.getTokens().sortedByDescending { it.lastModifiedTime })
    }

    fun updateChatList() {
        ChatList.clear()
        ChatList.addAll(Database.getChats().sortedByDescending { it.lastModified })
    }
}