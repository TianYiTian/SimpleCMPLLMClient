package com.tyt.deepseektest.data.db

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.tyt.deepseektest.data.ChatRequestModel

data class ChatDBModel(
    val requestModel: ChatRequestModel,
    var apiAddress: String,
    var token: String,
    var title: String = "",
    var lastModified: Long = 0,
    var id: Long? = null,
)
