package com.tyt.deepseektest.data

import com.google.gson.annotations.SerializedName
import com.tyt.deepseektest.NetworkService.SEND_STATUS_SERIAL_NAME

/**
 * @author milos
 * @created on 2023/9/7
 * @desc desc
 *
 */
data class ChatMessageModel(
//    @SerializedName(SEND_STATUS_SERIAL_NAME)
    var role: ChatRole,
    var content: String = "",
    @SerializedName(SEND_STATUS_SERIAL_NAME) var sendStatus: SendStatus = SendStatus.COMPLETED,
    @SerializedName("tool_calls") var toolCalls: List<ChatToolCall>? = null,
    @SerializedName("tool_call_id") var toolCallId: String? = null,
) {
//    override fun toString(): String {
//        return role.toString() + content + toolCalls?.toString() + toolCallId
//    }
}

enum class ChatRole(val value: String) {
    @SerializedName("system")
    SYSTEM("system"),

    @SerializedName("user")
    USER("user"),

    @SerializedName("assistant")
    ASSISTANT("assistant"),

    @SerializedName("tool")
    TOOL("tool")
}

enum class SendStatus(val status: String) {
    @SerializedName("sending")
    SENDING("sending"),

    @SerializedName("failed")
    FAILED("failed"),

    @SerializedName("completed")
    COMPLETED("completed")
}

data class ChatToolCall(
    val id: String,
    val type: String,
    val function: ChatToolCallFunction,
)

data class ChatToolCallFunction(
    val name: String,
    val arguments: String,
)