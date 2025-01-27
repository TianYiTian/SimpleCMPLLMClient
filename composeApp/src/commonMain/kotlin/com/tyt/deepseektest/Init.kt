package com.tyt.deepseektest

object Init {

    init {
        PersonalInit
        DataHolder.updateChatList()
        DataHolder.updateModelList()
        DataHolder.updateTokenList()
        DataHolder.updateApiAddressList()
    }
}