package com.robotemi.sdk.map

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class StringOrObjectAdapter : JsonDeserializer<String>, JsonSerializer<String> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): String? {
        if (json == null || json.isJsonNull) {
            return null
        }

        return when {
            json.isJsonPrimitive -> {
                json.asString
            }

            json.isJsonObject || json.isJsonArray -> {
                json.toString()
            }

            else -> {
                json.toString()
            }
        }
    }

    override fun serialize(
        src: String?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        if (src == null) {
            return JsonNull.INSTANCE
        }

        val trimmed = src.trim()
        if (trimmed.isEmpty()) {
            return JsonPrimitive(src)
        }

        // Keep plain strings as-is; only convert object/array JSON string into raw JSON.
        return runCatching { JsonParser.parseString(trimmed) }
            .map { parsed ->
                if (parsed.isJsonObject || parsed.isJsonArray) {
                    parsed
                } else {
                    JsonPrimitive(src)
                }
            }
            .getOrDefault(JsonPrimitive(src))
    }
}