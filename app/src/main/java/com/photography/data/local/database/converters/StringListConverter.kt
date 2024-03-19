package com.photography.data.local.database.converters

import androidx.room.TypeConverter
import com.photography.utils.listToString
import com.photography.utils.stringToList

class StringListConverter {

    @TypeConverter
    fun listToString(value: List<String>?): String? {
        return value?.listToString()
    }

    @TypeConverter
    fun stringToList(value: String?): List<String> {
        return value?.stringToList() ?: emptyList()
    }
}