package com.tyt.deepseektest

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.tyt.deepseektest.NetworkService.GSON
import java.io.File

actual object SimplePreferences {
    private val file = File(System.getProperty("user.home"), "DeepseekTestSimplePreferences")

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    actual fun readBoolean(name: String, default: Boolean): Boolean {
        val text = file.readText()
        val jsonObject: JsonObject = GSON.fromJson(text, JsonObject::class.java) ?: return default
        val jsonPrimitive: JsonPrimitive = jsonObject.getAsJsonPrimitive(name) ?: return default
        return jsonPrimitive.asBoolean
    }

    actual fun writeBoolean(name: String, value: Boolean) {
        val text = file.readText()
        var jsonObject: JsonObject? = GSON.fromJson(text, JsonObject::class.java)
        if (jsonObject == null) {
            jsonObject = JsonObject()
        }
        jsonObject.addProperty(name, value)
        file.writeText(GSON.toJson(jsonObject))
    }
}