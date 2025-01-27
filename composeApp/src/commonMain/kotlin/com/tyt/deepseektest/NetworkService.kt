package com.tyt.deepseektest

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.tyt.deepseektest.data.ChatMessageModel
import com.tyt.deepseektest.data.ChatResponseModel
import com.tyt.deepseektest.data.ChatRole
import com.tyt.deepseektest.data.SendStatus
import com.tyt.deepseektest.data.db.ChatDBModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

object NetworkService {
    const val SEND_STATUS_SERIAL_NAME = "send_status"

    val CLIENT = HttpClient(OkHttp) {
        engine {
            config {
                followRedirects(true)
            }
        }
        install(ContentNegotiation) {
            gson() {
                setExclusionStrategies(object : ExclusionStrategy {
                    override fun shouldSkipField(f: FieldAttributes): Boolean {
//                        println(f.annotations.toString())
//                        if (f.annotations.any { it.toString().contains("gson") }){
//                            return false
//                        }
                        return f.annotations.any { it.annotationClass.java.name == SerializedName::class.java.name && (it as SerializedName).value == SEND_STATUS_SERIAL_NAME }
                    }

                    override fun shouldSkipClass(clazz: Class<*>): Boolean {
                        return false
                    }

                })
            }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 300000
            connectTimeoutMillis = 300000
            socketTimeoutMillis = 300000
        }
    }

    val GSON = Gson()

//    val TAG = "OPENAI"

    suspend fun sendChatMessage(model: ChatDBModel, newMessage: String, onRefresh: () -> Unit) {
        try {
            model.requestModel.messages.add(ChatMessageModel(ChatRole.USER, newMessage, SendStatus.SENDING))
            onRefresh()
            val response = CLIENT.post(model.apiAddress) {
                contentType(ContentType.Application.Json)
                header(
                    "Authorization", "Bearer " + model.token
                )
                setBody(
                    model.requestModel
                )
            }

            if (response.status.value == 200) {
                val responseModel: ChatResponseModel = response.body()
                model.requestModel.messages.last().sendStatus = SendStatus.COMPLETED
                val responseMessage = responseModel.choices[0].message
                responseMessage.sendStatus = SendStatus.COMPLETED
                model.requestModel.messages.add(responseMessage)
            } else {
                model.requestModel.messages.last().sendStatus = SendStatus.FAILED
            }
        } catch (e: Exception) {
            model.requestModel.messages.lastOrNull { it.role == ChatRole.USER }?.sendStatus = SendStatus.FAILED
        } finally {
            onRefresh()
        }

    }

    suspend fun resendLast(model: ChatDBModel, onRefresh: () -> Unit) {
        try {
            model.requestModel.messages.last().sendStatus = SendStatus.SENDING
            onRefresh()
            val response = CLIENT.post(model.apiAddress) {
                contentType(ContentType.Application.Json)
                header(
                    "Authorization", "Bearer " + model.token
                )
                setBody(
                    model.requestModel
                )
            }

            if (response.status.value == 200) {
                val responseModel: ChatResponseModel = response.body()
                model.requestModel.messages.last().sendStatus = SendStatus.COMPLETED
                val responseMessage = responseModel.choices[0].message
                responseMessage.sendStatus = SendStatus.COMPLETED
                model.requestModel.messages.add(responseMessage)
            } else {
                model.requestModel.messages.last().sendStatus = SendStatus.FAILED
            }
        } catch (e: Exception) {
            model.requestModel.messages.lastOrNull { it.role == ChatRole.USER }?.sendStatus = SendStatus.FAILED
        } finally {
            onRefresh()
        }
    }
}