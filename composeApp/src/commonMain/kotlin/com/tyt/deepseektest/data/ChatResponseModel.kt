package com.tyt.deepseektest.data

import com.google.gson.annotations.SerializedName

/**
 * @author milos
 * @created on 2023/9/7
 * @desc desc
 *
 */
data class ChatResponseModel(
    val id: String = "",
    @SerializedName("object")
    val obj: String = "chat.completion",
    val created: Long = 0L,
    val model: String,
    val choices: List<ChatChoiceModel> = listOf(),
    val usage: ChatUsageModel
)

data class ChatChoiceModel(
    val index: Int,
    val message: ChatMessageModel,
    @SerializedName("finish_reason")
    val finishReason: String,
)

data class ChatUsageModel(
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("total_tokens")
    val totalTokens: Int
)