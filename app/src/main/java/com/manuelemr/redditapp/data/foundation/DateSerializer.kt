package com.manuelemr.redditapp.data.foundation

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

class DateSerializer: JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? = json?.let {
        Date(it.asJsonPrimitive.asLong) // Just throw is deserialization fails
    }
}