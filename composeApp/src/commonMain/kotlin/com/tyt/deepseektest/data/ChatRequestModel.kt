package com.tyt.deepseektest.data

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.gson.JsonElement
import com.google.gson.JsonObject


/**
 * @author milos
 * @created on 2023/9/7
 * @desc desc
 *
 */
data class ChatRequestModel(
    val messages: SnapshotStateList<ChatMessageModel>,
    var model: String,
    val tools:List<ChatRequestTool>?=null,
    val temperature:Float =1f
)

data class ChatRequestTool(
    val type:String,
    val function: ChatRequestFunction
)

data class ChatRequestFunction(
    val name:String,
    val description:String,
    val parameters:ChatRequestFunctionParameter
)

data class ChatRequestFunctionParameter(
    val type:String,
    //add几个这样的element
    // 'a': {
    //                    'type': 'int',
    //                    'description': 'A number',
    //                }
    val properties:JsonObject,
    val required:List<String>
)