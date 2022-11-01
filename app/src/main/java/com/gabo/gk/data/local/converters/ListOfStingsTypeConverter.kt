package com.gabo.gk.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class ListOfStingsTypeConverter {
    @TypeConverter
    fun fromSource(list: List<String>): String {
        return Gson().toJson(list).toString()
    }

    @TypeConverter
    fun toSource(list: String?): List<String> {
        return Gson().fromJson(list, Array<String>::class.java).asList()
    }
}